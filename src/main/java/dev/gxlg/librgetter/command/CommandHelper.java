package dev.gxlg.librgetter.command;

import com.mojang.brigadier.Command;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.commands.Commands;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.NotInGoalsException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.parser.CouldNotParseCustomException;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.EnchantmentRemovedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.CustomTradeAddedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.PriceChangedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.TradeAddedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.TranslatableSuccessMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.warning.AddingCustomEnchantmentMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.warning.CanNotBeTradedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.warning.LevelOverMaxMessage;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
    public static void manageGoals(CommandContext context, boolean remove) throws LibrGetterException {
        List<Enchantment> enchantments = Commands.getImpl().getEnchantmentsFromCommandContext(context);

        int globalLvlCriteria;
        try {
            globalLvlCriteria = context.getArgument("level", R.clz(Integer.class)).unwrap(Integer.class);
        } catch (IllegalArgumentException ignored) {
            globalLvlCriteria = -1;
        }

        int price = 64;
        try {
            price = context.getArgument("maxprice", R.clz(Integer.class)).unwrap(Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        List<EnchantmentTrade> trades = new ArrayList<>();
        List<Integer> maxLevels = new ArrayList<>();
        for (Enchantment enchantment : enchantments) {
            Identifier enchantmentId = Enchantments.getImpl().enchantmentId(enchantment);
            if (enchantmentId == null) {
                throw new InternalErrorException("enchantmentId");
            }
            int max = enchantment.getMaxLevel();
            maxLevels.add(max);
            int level = (globalLvlCriteria == -1 && !remove) ? max : globalLvlCriteria;
            trades.add(new EnchantmentTrade(enchantmentId.toString(), level, price));
        }

        if (remove) {
            boolean anyRemoved = false;
            if (globalLvlCriteria == -1) {
                for (EnchantmentTrade trade : trades) {
                    try {
                        removeGoalAllLevels(trade);
                    } catch (NotInGoalsException e) {
                        continue;
                    }
                    anyRemoved = true;
                }
            } else {
                for (int i = 0; i < enchantments.size(); i++) {
                    EnchantmentTrade trade = trades.get(i);
                    int maxLevel = maxLevels.get(i);
                    if (LibrGetter.config.warning && globalLvlCriteria > maxLevel) {
                        Texts.getImpl().sendTranslatable(new LevelOverMaxMessage(trade, maxLevel));
                    }
                    try {
                        removeGoal(trade);
                    } catch (NotInGoalsException e) {
                        continue;
                    }
                    anyRemoved = true;
                }
            }
            if (!anyRemoved) {
                throw new NotInGoalsException(trades);
            }
        } else {
            for (int i = 0; i < enchantments.size(); i++) {
                Enchantment enchantment = enchantments.get(i);
                EnchantmentTrade trade = trades.get(i);
                int maxLevel = maxLevels.get(i);

                if (LibrGetter.config.warning && !Enchantments.getImpl().canBeTraded(enchantment)) {
                    Texts.getImpl().sendTranslatable(new CanNotBeTradedMessage(trade));
                }

                if (LibrGetter.config.warning && trade.lvl() > maxLevel) {
                    new LevelOverMaxMessage(trade, enchantment.getMaxLevel());
                }

                addGoal(trade, false);
            }
        }
    }

    public static void manageGoalsCustom(CommandContext context, boolean remove) throws LibrGetterException {
        String enchantment = Commands.getImpl().getCustomEnchantmentFromCommandContext(context);
        if (Identifier.tryParse(enchantment) == null) {
            throw new CouldNotParseCustomException();
        }

        int enchantmentLevel = context.getArgument("level", R.clz(Integer.class)).unwrap(Integer.class);

        int price = 64;
        try {
            price = context.getArgument("maxprice", R.clz(Integer.class)).unwrap(Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        EnchantmentTrade trade = new EnchantmentTrade(enchantment, enchantmentLevel, price);

        if (remove) {
            removeGoal(trade);
        } else {
            if (LibrGetter.config.warning) {
                Texts.getImpl().sendTranslatable(new AddingCustomEnchantmentMessage(trade));
            }
            addGoal(trade, true);
        }
    }

    private static void addGoal(EnchantmentTrade newTrade, boolean custom) {
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.same(newTrade)) {
                alreadyPresentTrade = trade;
                break;
            }
        }

        if (alreadyPresentTrade != null) {
            Texts.getImpl().sendTranslatable(new PriceChangedMessage(alreadyPresentTrade, newTrade.price()));
            LibrGetter.config.goals.remove(alreadyPresentTrade);
        } else {
            TranslatableSuccessMessage message = custom ? new CustomTradeAddedMessage(newTrade, newTrade.price()) : new TradeAddedMessage(newTrade, newTrade.price());
            Texts.getImpl().sendTranslatable(message);
        }
        LibrGetter.config.goals.add(newTrade);
        LibrGetter.configManager.save();
    }

    private static void removeGoalAllLevels(EnchantmentTrade tradeToRemove) throws NotInGoalsException {
        List<EnchantmentTrade> alreadyPresentTrades = new ArrayList<>();
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.id().equals(tradeToRemove.id())) {
                alreadyPresentTrades.add(trade);
            }
        }
        if (alreadyPresentTrades.isEmpty()) {
            throw new NotInGoalsException(tradeToRemove);
        }
        for (EnchantmentTrade trade : alreadyPresentTrades) {
            LibrGetter.config.goals.remove(trade);
            Texts.getImpl().sendTranslatable(new EnchantmentRemovedMessage(trade));
        }
        LibrGetter.configManager.save();
    }

    public static void removeGoal(EnchantmentTrade tradeToRemove) throws NotInGoalsException {
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.same(tradeToRemove)) {
                alreadyPresentTrade = trade;
                break;
            }
        }
        if (alreadyPresentTrade == null) {
            throw new NotInGoalsException(tradeToRemove);
        }
        LibrGetter.config.goals.remove(alreadyPresentTrade);
        LibrGetter.configManager.save();
        Texts.getImpl().sendTranslatable(new EnchantmentRemovedMessage(tradeToRemove));
    }

    public static Command<?> commandWrapper(CommandRunnable runnable) {
        return ctx -> {
            try {
                runnable.run(R.wrapperInst(CommandContext.class, ctx));
            } catch (LibrGetterException e) {
                Texts.getImpl().sendTranslatable(e.getTranslatableErrorMessage());
                return 1;
            }
            return 0;
        };
    }

    @FunctionalInterface
    public interface CommandRunnable {
        void run(CommandContext context) throws LibrGetterException;
    }
}

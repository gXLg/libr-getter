package dev.gxlg.librgetter.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.chaining.commands.Commands;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.NotInGoalsException;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.parser.CouldNotParseCustomException;
import dev.gxlg.librgetter.utils.types.translatable_messages.feedback.EnchantmentRemovedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.success.CustomTradeAddedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.success.PriceChangedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.success.TradeAddedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.success.TranslatableSuccessMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.warning.AddingCustomEnchantmentMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.warning.CanNotBeTradedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.warning.LevelOverMaxMessage;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
    public static void manageGoals(CommandContext<?> context, boolean remove) throws LibrGetterException {
        List<Enchantment> list = Commands.getImpl().getEnchantmentsFromCommandContext(context);

        int globalLvlCriteria;
        try {
            globalLvlCriteria = context.getArgument("level", Integer.class);
        } catch (IllegalArgumentException ignored) {
            globalLvlCriteria = -1;
        }

        int price = 64;
        try {
            price = context.getArgument("maxprice", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        for (Enchantment enchantment : list) {
            Identifier enchantmentId = MinecraftHelper.enchantmentId(enchantment);

            if (enchantmentId == null) {
                throw new InternalErrorException("id");
            }

            if (globalLvlCriteria == -1 && remove) {
                // remove all levels of the enchantment
                removeGoals(enchantmentId.toString());
                continue;
            }

            int max = enchantment.getMaxLevel();
            int currentEnchantmentLvl;
            if (globalLvlCriteria == -1) {
                // default to the max level for each enchantment
                currentEnchantmentLvl = max;
            } else {
                currentEnchantmentLvl = globalLvlCriteria;
            }

            if (remove) {
                removeGoal(enchantmentId.toString(), currentEnchantmentLvl);

            } else {
                if (!MinecraftHelper.canBeTraded(enchantment) && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatable(new CanNotBeTradedMessage(enchantmentId));
                }

                if (globalLvlCriteria > max && LibrGetter.config.warning) {
                    new LevelOverMaxMessage(enchantmentId, enchantment.getMaxLevel());
                }

                addGoal(enchantmentId.toString(), currentEnchantmentLvl, price, false);
            }
        }
    }

    public static void manageGoalsCustom(CommandContext<?> context, boolean remove) throws LibrGetterException {
        String enchantment = Commands.getImpl().getCustomEnchantmentFromCommandContext(context);
        if (Identifier.tryParse(enchantment) == null) {
            throw new CouldNotParseCustomException();
        }
        int enchantmentLevel = context.getArgument("level", Integer.class);

        int price = 64;
        try {
            price = context.getArgument("maxprice", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        if (remove) {
            removeGoal(enchantment, enchantmentLevel);
        } else {
            addGoal(enchantment, enchantmentLevel, price, true);
        }
    }


    private static void addGoal(String name, int level, int price, boolean custom) {
        EnchantmentTrade newTrade = new EnchantmentTrade(name, level, price);
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.same(newTrade)) {
                alreadyPresentTrade = trade;
                break;
            }
        }
        if (custom && LibrGetter.config.warning) {
            Texts.getImpl().sendTranslatable(new AddingCustomEnchantmentMessage(name));
        }

        if (alreadyPresentTrade != null) {
            Texts.getImpl().sendTranslatable(new PriceChangedMessage(alreadyPresentTrade, price));
            LibrGetter.config.goals.remove(alreadyPresentTrade);
        } else {
            TranslatableSuccessMessage message = custom ? new CustomTradeAddedMessage(newTrade, newTrade.price()) : new TradeAddedMessage(newTrade, newTrade.price());
            Texts.getImpl().sendTranslatable(message);
        }
        LibrGetter.config.goals.add(newTrade);
        LibrGetter.config.save();
    }

    private static void removeGoals(String name) throws NotInGoalsException {
        List<EnchantmentTrade> alreadyPresentTrades = new ArrayList<>();
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.id().equals(name)) {
                alreadyPresentTrades.add(trade);
            }
        }
        if (alreadyPresentTrades.isEmpty()) {
            throw new NotInGoalsException(name);
        }
        for (EnchantmentTrade trade : alreadyPresentTrades) {
            LibrGetter.config.goals.remove(trade);
            Texts.getImpl().sendTranslatable(new EnchantmentRemovedMessage(trade));
        }
    }

    public static void removeGoal(String name, int level) throws NotInGoalsException {
        EnchantmentTrade newTrade = new EnchantmentTrade(name, level, 64);
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.same(newTrade)) {
                alreadyPresentTrade = trade;
                break;
            }
        }
        if (alreadyPresentTrade == null) {
            throw new NotInGoalsException(newTrade);
        }
        LibrGetter.config.goals.remove(alreadyPresentTrade);
        LibrGetter.config.save();
        Texts.getImpl().sendTranslatable(new EnchantmentRemovedMessage(newTrade));
    }

    public static Command<?> commandWrapper(CommandRunnable runnable) {
        return ctx -> {
            try {
                runnable.run(ctx);
            } catch (LibrGetterException e) {
                Texts.getImpl().sendTranslatable(e.getTranslatableErrorMessage());
                return 1;
            }
            return 0;
        };
    }

    @FunctionalInterface
    public interface CommandRunnable {
        void run(CommandContext<?> context) throws LibrGetterException;
    }
}

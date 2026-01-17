package dev.gxlg.librgetter.command;

import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.chaining.commands.Commands;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.commands.CommandException;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
    public static int manageGoals(CommandContext<?> context, boolean remove) {
        List<Enchantment> list;
        try {
            list = Commands.getImpl().getEnchantmentsFromCommandContext(context);
        } catch (CommandException e) {
            e.sendErrorToPlayer();
            return 1;
        }

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

        boolean anySuccess = false;
        for (Enchantment enchantment : list) {
            Identifier enchantmentId = MinecraftHelper.enchantmentId(enchantment);

            if (enchantmentId == null) {
                Texts.getImpl().sendTranslatableError("librgetter.internal", "id", "LibrGetCommand#enchanter");
                return 1;
            }

            if (globalLvlCriteria == -1 && remove) {
                // remove all levels of the enchantment
                anySuccess = anySuccess || removeGoals(enchantmentId.toString());
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
                anySuccess = anySuccess || removeGoal(enchantmentId.toString(), currentEnchantmentLvl);

            } else {
                if (!MinecraftHelper.canBeTraded(enchantment) && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.notrade", enchantmentId);
                }

                if (globalLvlCriteria > max && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.level", enchantmentId, enchantment.getMaxLevel());
                }

                anySuccess = anySuccess || addGoal(enchantmentId.toString(), currentEnchantmentLvl, price, false);
            }
        }

        return anySuccess ? 0 : 1;
    }

    public static int manageGoalsCustom(CommandContext<?> context, boolean remove) {
        String enchantment = Commands.getImpl().getCustomEnchantmentFromCommandContext(context);
        if (Identifier.tryParse(enchantment) == null) {
            Texts.getImpl().sendTranslatableError("librgetter.parse");
            return 1;
        }
        int enchantmentLevel = context.getArgument("level", Integer.class);

        int price = 64;
        try {
            price = context.getArgument("maxprice", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        if (remove) {
            return removeGoal(enchantment, enchantmentLevel) ? 0 : 1;
        } else {
            return addGoal(enchantment, enchantmentLevel, price, true) ? 0 : 1;
        }
    }


    private static boolean addGoal(String name, int level, int price, boolean custom) {
        EnchantmentTrade newTrade = new EnchantmentTrade(name, level, price);
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.same(newTrade)) {
                alreadyPresentTrade = trade;
                break;
            }
        }
        if (alreadyPresentTrade != null) {
            Texts.getImpl().sendTranslatableSuccess("librgetter.price", alreadyPresentTrade, price);
            LibrGetter.config.goals.remove(alreadyPresentTrade);
        } else {
            Texts.getImpl().sendTranslatableSuccess(custom ? "libgetter.add_custom" : "libgetter.add", newTrade, newTrade.price());
        }
        LibrGetter.config.goals.add(newTrade);
        LibrGetter.config.save();
        return true;
    }

    private static boolean removeGoals(String name) {
        List<EnchantmentTrade> alreadyPresentTrades = new ArrayList<>();
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.id().equals(name)) {
                alreadyPresentTrades.add(trade);
            }
        }
        if (alreadyPresentTrades.isEmpty()) {
            Texts.getImpl().sendTranslatableError("librgetter.not", name);
            return true;
        }
        alreadyPresentTrades.forEach(t -> removeGoal(t.id(), t.lvl()));
        return false;
    }

    public static boolean removeGoal(String name, int level) {
        EnchantmentTrade newTrade = new EnchantmentTrade(name, level, 64);
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : LibrGetter.config.goals) {
            if (trade.same(newTrade)) {
                alreadyPresentTrade = trade;
                break;
            }
        }
        if (alreadyPresentTrade == null) {
            Texts.getImpl().sendTranslatableError("librgetter.not", newTrade);
            return false;
        }
        LibrGetter.config.goals.remove(alreadyPresentTrade);
        LibrGetter.config.save();
        Texts.getImpl().sendTranslatableWarning("librgetter.removed", newTrade);
        return true;
    }
}

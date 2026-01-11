package dev.gxlg.librgetter.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Commands;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
    public static int manageGoals(CommandContext<?> context, boolean remove) {
        List<Either<Enchantment, String>> list = new ArrayList<>();
        if (!Commands.getEnchantments(list, context)) {
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

        for (Either<Enchantment, String> item : list) {
            Identifier enchantmentId;
            int currentEnchantmentLvl;
            boolean isCustomEnchantmentFormat = false;

            if (item.left().isPresent()) {
                Enchantment enchantment = item.left().get();
                enchantmentId = Minecraft.enchantmentId(enchantment);

                if (!Minecraft.canBeTraded(enchantment) && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.notrade", enchantmentId);
                }

                int max = enchantment.getMaxLevel();
                if (globalLvlCriteria > max && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.level", enchantmentId, enchantment.getMaxLevel());
                }

                if (globalLvlCriteria == -1) {
                    // default to the max level for each enchantment
                    currentEnchantmentLvl = max;
                } else {
                    currentEnchantmentLvl = globalLvlCriteria;
                }

                if (enchantmentId == null) {
                    Texts.getImpl().sendTranslatableError("librgetter.internal", "id", "LibrGetCommand#enchanter");
                    return 1;
                }

            } else if (item.right().isPresent()) {
                String customId = item.right().get();
                enchantmentId = Identifier.tryParse(customId);
                currentEnchantmentLvl = globalLvlCriteria;
                isCustomEnchantmentFormat = true;

                if (enchantmentId == null) {
                    Texts.getImpl().sendTranslatableError("librgetter.parse");
                    return 1;
                }

                if (!remove && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.custom", enchantmentId);
                }

            } else {
                continue;
            }

            if (remove) {
                removeGoal(enchantmentId.toString(), currentEnchantmentLvl);
            } else {
                addGoal(enchantmentId.toString(), currentEnchantmentLvl, price, isCustomEnchantmentFormat);
            }
        }

        return 0;
    }

    public static void addGoal(String name, int level, int price, boolean custom) {
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
    }

    public static void removeGoal(String name, int level) {
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
            return;
        }
        LibrGetter.config.goals.remove(alreadyPresentTrade);
        LibrGetter.config.save();
        Texts.getImpl().sendTranslatableWarning("librgetter.removed", newTrade);
    }
}

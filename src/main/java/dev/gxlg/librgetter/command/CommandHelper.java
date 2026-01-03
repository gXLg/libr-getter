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

        int lvl = -1;
        try {
            lvl = context.getArgument("level", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        int price = 64;
        try {
            price = context.getArgument("maxprice", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        for (Either<Enchantment, String> item : list) {
            Identifier enchantmentId;
            int level;
            boolean custom = false;

            if (item.left().isPresent()) {
                Enchantment enchantment = item.left().get();
                enchantmentId = Minecraft.enchantmentId(enchantment);

                if (!Minecraft.canBeTraded(enchantment) && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.notrade", enchantmentId);
                }

                int max = enchantment.getMaxLevel();
                if (lvl > max && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.level", enchantmentId, enchantment.getMaxLevel());
                }

                if (lvl == -1) {
                    // default to the max level for each enchantment
                    level = max;
                } else {
                    level = lvl;
                }

                if (enchantmentId == null) {
                    Texts.getImpl().sendTranslatableError("librgetter.internal", "id", "LibrGetCommand#enchanter");
                    return 1;
                }

            } else if (item.right().isPresent()) {
                String customId = item.right().get();
                enchantmentId = Identifier.tryParse(customId);
                level = lvl;
                custom = true;

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
                removeGoal(enchantmentId.toString(), level);
            } else {
                addGoal(enchantmentId.toString(), level, price, custom);
            }
        }

        return 0;
    }

    public static void addGoal(String name, int level, int price, boolean custom) {
        EnchantmentTrade newLooking = new EnchantmentTrade(name, level, price);
        EnchantmentTrade already = null;
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            if (l.same(newLooking)) {
                already = l;
                break;
            }
        }
        if (already != null) {
            Texts.getImpl().sendTranslatableSuccess("librgetter.price", already, price);
            LibrGetter.config.goals.remove(already);
        } else {
            Texts.getImpl().sendTranslatableSuccess(custom ? "libgetter.add_custom" : "libgetter.add", newLooking, newLooking.price());
        }
        LibrGetter.config.goals.add(newLooking);
        LibrGetter.config.save();
    }

    public static void removeGoal(String name, int level) {
        EnchantmentTrade newLooking = new EnchantmentTrade(name, level, 64);
        EnchantmentTrade already = null;
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            if (l.same(newLooking)) {
                already = l;
                break;
            }
        }
        if (already == null) {
            Texts.getImpl().sendTranslatableError("librgetter.not", newLooking);
            return;
        }
        LibrGetter.config.goals.remove(already);
        LibrGetter.config.save();
        Texts.getImpl().sendTranslatableWarning("librgetter.removed", newLooking);
    }
}

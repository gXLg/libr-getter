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
        if (!Commands.getEnchantments(list, context)) return 1;

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
            if (item.left().isPresent()) {
                Enchantment enchantment = item.left().get();

                Identifier id = Minecraft.enchantmentId(enchantment);

                if (lvl > enchantment.getMaxLevel() && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.level", id, enchantment.getMaxLevel());
                }
                int level = lvl;
                if (lvl == -1) level = enchantment.getMaxLevel();

                if (!Minecraft.canBeTraded(enchantment) && LibrGetter.config.warning) {
                    Texts.getImpl().sendTranslatableWarning("librgetter.notrade", id);
                }

                if (id == null) {
                    Texts.getImpl().sendTranslatableError("librgetter.internal", "id", "LibrGetCommand#enchanter");
                    return 1;
                }

                if (remove) removeGoal(id.toString(), level);
                else addGoal(id.toString(), level, price, false);
            } else if (item.right().isPresent()) {
                String custom = item.right().get();

                Identifier enchantment = Identifier.tryParse(custom);
                if (enchantment == null) {
                    Texts.getImpl().sendTranslatableError("librgetter.parse");
                    return 1;
                }

                if (!remove && LibrGetter.config.warning)
                    Texts.getImpl().sendTranslatableWarning("librgetter.custom", enchantment);

                if (remove) removeGoal(enchantment.toString(), lvl);
                else addGoal(enchantment.toString(), lvl, price, true);
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

package dev.gxlg.librgetter.utils.plugins;

import dev.gxlg.librgetter.utils.reflection.chaining.tags.Tags;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.parser.UnknownPluginDataException;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;

import java.util.Set;

public class Bukkit {
    public static EnchantmentTrade.EnchantmentOnly parse(CompoundTagWrapper tag) throws LibrGetterException {
        CompoundTagWrapper element = Tags.getImpl().getCompound(tag, "PublicBukkitValues");
        Set<String> keys = element.keySet();

        for (String key : keys) {
            if (key.startsWith("enchantmentsolution:")) {
                return enchantmentSolution(element, keys);
            }
        }

        return null;
    }

    /* different Bukkit plugins */

    private static EnchantmentTrade.EnchantmentOnly enchantmentSolution(CompoundTagWrapper element, Set<String> keys) throws LibrGetterException {
        String id = null;
        int lvl = -1;

        for (String key : keys) {
            if (key.startsWith("enchantmentsolution:") && keys.contains(key + "_level")) {
                id = key;
                String lvls = Tags.getImpl().getString(element, key + "_level");
                lvl = Integer.parseInt(lvls);
                break;
            }
        }
        if (id == null) {
            throw new UnknownPluginDataException("Enchantment Solution");
        }

        return new EnchantmentTrade.EnchantmentOnly(id, lvl);
    }
}

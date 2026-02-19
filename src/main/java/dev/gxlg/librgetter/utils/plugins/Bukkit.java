package dev.gxlg.librgetter.utils.plugins;

import dev.gxlg.librgetter.utils.chaining.tags.Tags;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.parser.UnknownPluginDataException;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;

import java.util.Set;

public class Bukkit {
    public static EnchantmentTrade.EnchantmentOnly parse(CompoundTag tag) throws LibrGetterException {
        CompoundTag element = Tags.getImpl().getCompound(tag, "PublicBukkitValues");
        Set<String> keys = element.keySet();

        for (String key : keys) {
            if (key.startsWith("enchantmentsolution:")) {
                return enchantmentSolution(element, keys);
            }
        }

        return null;
    }

    /* different Bukkit plugins */

    private static EnchantmentTrade.EnchantmentOnly enchantmentSolution(CompoundTag element, Set<String> keys) throws LibrGetterException {
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

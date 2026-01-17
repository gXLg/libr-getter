package dev.gxlg.librgetter.utils.plugins;

import dev.gxlg.librgetter.utils.reflection.chaining.tags.Tags;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Set;

public class Bukkit {
    public static Triple<String, Integer, String[]> parse(CompoundTagWrapper tag) {
        CompoundTagWrapper element = Tags.getImpl().getCompound(tag, "PublicBukkitValues");
        Set<String> keys = element.keySet();

        for (String key : keys) {
            if (key.startsWith("enchantmentsolution:")) {
                return enchantmentSolution(element, keys);
            }
        }

        return null;
    }

    /* different bukkit plugins */

    private static Triple<String, Integer, String[]> enchantmentSolution(CompoundTagWrapper element, Set<String> keys) {
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
            return Triple.of(null, null, new String[]{ "librgetter.unknown", "Enchantment Solution" });
        }

        return Triple.of(id, lvl, null);
    }
}

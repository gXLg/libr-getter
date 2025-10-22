package dev.gxlg.librgetter.utils.plugins;

import dev.gxlg.librgetter.utils.reflection.Nbt;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashSet;
import java.util.Set;

public class Bukkit {
    public static Triple<String, Integer, String[]> parse(Object tag) {
        Object element = Nbt.getCompound(tag, "PublicBukkitValues");
        Set<?> ukeys = Nbt.getKeys(element);

        Set<String> keys = new HashSet<>();
        for (Object key : ukeys) {
            keys.add((String) key);
        }

        for (String key : keys) {
            if (key.startsWith("enchantmentsolution:")) {
                return enchantmentSolution(element, keys);
            }
        }

        return null;
    }

    /* different bukkit plugins */

    private static Triple<String, Integer, String[]> enchantmentSolution(Object element, Set<String> keys) {
        String id = null;
        int lvl = -1;

        for (String key : keys) {
            if (key.startsWith("enchantmentsolution:") && keys.contains(key + "_level")) {
                id = key;
                String lvls = Nbt.getString(element, key + "_level");
                lvl = Integer.parseInt(lvls);
                break;
            }
        }
        if (id == null) {
            return Triple.of(null, null, new String[]{"librgetter.unknown", "Enchantment Solution"});
        }

        return Triple.of(id, lvl, null);
    }
}

package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.plugins.Bukkit;
import dev.gxlg.librgetter.utils.reflection.Nbt;
import org.apache.commons.lang3.tuple.Triple;

public class Plugins {
    public static Triple<String, Integer, String[]> parse(Object tag) {
        if (tag == null) return null;
        if (Nbt.contains(tag, "PublicBukkitValues")) {
            return Bukkit.parse(tag);
        }
        return null;
    }
}

package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.utils.plugins.Bukkit;
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

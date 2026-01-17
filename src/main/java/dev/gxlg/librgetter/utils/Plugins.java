package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.plugins.Bukkit;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import org.apache.commons.lang3.tuple.Triple;

public class Plugins {
    public static Triple<String, Integer, String[]> parse(CompoundTagWrapper tag) {
        if (tag == null) {
            return null;
        }
        if (tag.contains("PublicBukkitValues")) {
            return Bukkit.parse(tag);
        }
        return null;
    }
}

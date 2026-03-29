package dev.gxlg.librgetter.utils.plugins;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;

public class Plugins {
    public static EnchantmentTrade.EnchantmentOnly parse(CompoundTag tag) throws LibrGetterException {
        if (tag == null) {
            return null;
        }
        if (tag.contains("PublicBukkitValues")) {
            return Bukkit.parse(tag);
        }
        return null;
    }
}

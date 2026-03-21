package dev.gxlg.librgetter.utils.chaining.tags;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.nbt.Tag;

import java.util.List;

public class Tags {
    private static Base implementation = null;

    public static String getString(CompoundTag element, String name) {
        return getImpl().getString(element, name);
    }

    public static CompoundTag getCompound(CompoundTag element, String name) {
        return getImpl().getCompound(element, name);
    }

    public static List<Tag> getList(CompoundTag element, String name, int type) {
        return getImpl().getList(element, name, type);
    }

    public static short getShort(CompoundTag element, String name) {
        return getImpl().getShort(element, name);
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.21.5")) {
            implementation = new Tags_1_17_0();
        } else {
            implementation = new Tags_1_21_5();
        }
        return implementation;
    }

    public abstract static class Base {
        public abstract String getString(CompoundTag element, String name);

        public abstract CompoundTag getCompound(CompoundTag element, String name);

        public abstract List<Tag> getList(CompoundTag element, String name, int type);

        public abstract short getShort(CompoundTag element, String name);
    }
}

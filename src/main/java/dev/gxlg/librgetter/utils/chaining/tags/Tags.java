package dev.gxlg.librgetter.utils.chaining.tags;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.nbt.Tag;

import java.util.List;

public class Tags {
    private static final Base implementation;

    static {
        if (V.lower("1.21.5")) {
            implementation = new Tags_1_17_0();
        } else {
            implementation = new Tags_1_21_5();
        }
    }

    public static String getString(CompoundTag element, String name) {
        return implementation.getString(element, name);
    }

    public static CompoundTag getCompound(CompoundTag element, String name) {
        return implementation.getCompound(element, name);
    }

    public static List<Tag> getList(CompoundTag element, String name, int type) {
        return implementation.getList(element, name, type);
    }

    public static short getShort(CompoundTag element, String name) {
        return implementation.getShort(element, name);
    }

    public abstract static class Base {
        public abstract String getString(CompoundTag element, String name);

        public abstract CompoundTag getCompound(CompoundTag element, String name);

        public abstract List<Tag> getList(CompoundTag element, String name, int type);

        public abstract short getShort(CompoundTag element, String name);
    }
}

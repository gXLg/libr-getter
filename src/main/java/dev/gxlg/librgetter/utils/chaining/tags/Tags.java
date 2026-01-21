package dev.gxlg.librgetter.utils.chaining.tags;

import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper;

import java.util.List;

public abstract class Tags {

    public abstract String getString(CompoundTagWrapper element, String name);

    public abstract CompoundTagWrapper getCompound(CompoundTagWrapper element, String name);

    public abstract List<TagWrapper> getList(CompoundTagWrapper element, String name, int type);

    public abstract short getShort(CompoundTagWrapper element, String name);

    private static Tags implementation = null;

    public static Tags getImpl() {
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
}

package dev.gxlg.librgetter.utils.chaining.tags;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.nbt.Tag;

import java.util.List;

public abstract class Tags {

    public abstract String getString(CompoundTag element, String name);

    public abstract CompoundTag getCompound(CompoundTag element, String name);

    public abstract List<Tag> getList(CompoundTag element, String name, int type);

    public abstract short getShort(CompoundTag element, String name);

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

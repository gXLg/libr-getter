package dev.gxlg.librgetter.utils.chaining.tags;

import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.nbt.Tag;

import java.util.List;

public class Tags_1_17_0 extends Tags {
    @Override
    public String getString(CompoundTag element, String name) {
        return element.getString(name);
    }

    @Override
    public CompoundTag getCompound(CompoundTag element, String name) {
        return element.getCompound(name);
    }

    @Override
    public List<Tag> getList(CompoundTag element, String name, int type) {
        return element.getList(name, type);
    }

    @Override
    public short getShort(CompoundTag element, String name) {
        return element.getShort(name);
    }
}

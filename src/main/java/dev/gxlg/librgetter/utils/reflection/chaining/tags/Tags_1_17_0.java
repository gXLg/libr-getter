package dev.gxlg.librgetter.utils.reflection.chaining.tags;

import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper;

import java.util.List;

public class Tags_1_17_0 extends Tags {
    @Override
    public String getString(CompoundTagWrapper element, String name) {
        return element.getString(name);
    }

    @Override
    public CompoundTagWrapper getCompound(CompoundTagWrapper element, String name) {
        return element.getCompound(name);
    }

    @Override
    public List<TagWrapper> getList(CompoundTagWrapper element, String name, int type) {
        return element.getList(name, type);
    }

    @Override
    public short getShort(CompoundTagWrapper element, String name) {
        return element.getShort(name);
    }
}

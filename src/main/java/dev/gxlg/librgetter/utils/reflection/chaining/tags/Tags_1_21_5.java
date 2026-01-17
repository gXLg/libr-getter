package dev.gxlg.librgetter.utils.reflection.chaining.tags;

import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper;

import java.util.List;

public class Tags_1_21_5 extends Tags_1_17_0 {
    @Override
    public String getString(CompoundTagWrapper element, String name) {
        return element.getStringOr(name, null);
    }

    @Override
    public CompoundTagWrapper getCompound(CompoundTagWrapper element, String name) {
        CompoundTagWrapper tag = element.getCompoundOrEmpty(name);
        if (tag.isEmpty()) {
            return null;
        }
        return tag;
    }

    @Override
    public List<TagWrapper> getList(CompoundTagWrapper element, String name, int type) {
        List<TagWrapper> list = element.getListOrEmpty(name);
        if (list.isEmpty()) {
            return null;
        }
        TagWrapper t = list.get(0);
        if (t.getId() != type) {
            return null;
        }
        return list;
    }

    @Override
    public short getShort(CompoundTagWrapper element, String name) {
        return element.getShortOr(name, (short) 0);
    }
}

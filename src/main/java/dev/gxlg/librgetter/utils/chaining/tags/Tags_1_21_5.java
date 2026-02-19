package dev.gxlg.librgetter.utils.chaining.tags;

import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.nbt.Tag;

import java.util.List;

public class Tags_1_21_5 extends Tags_1_17_0 {
    @Override
    public String getString(CompoundTag element, String name) {
        return element.getStringOr(name, null);
    }

    @Override
    public CompoundTag getCompound(CompoundTag element, String name) {
        CompoundTag tag = element.getCompoundOrEmpty(name);
        if (tag.isEmpty()) {
            return null;
        }
        return tag;
    }

    @Override
    public List<Tag> getList(CompoundTag element, String name, int type) {
        List<Tag> list = element.getListOrEmpty(name);
        if (list.isEmpty()) {
            return null;
        }
        Tag t = list.get(0);
        if (t.getId() != type) {
            return null;
        }
        return list;
    }

    @Override
    public short getShort(CompoundTag element, String name) {
        return element.getShortOr(name, (short) 0);
    }
}

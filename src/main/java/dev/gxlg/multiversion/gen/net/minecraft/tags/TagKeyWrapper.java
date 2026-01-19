package dev.gxlg.multiversion.gen.net.minecraft.tags;

import dev.gxlg.multiversion.R;

public class TagKeyWrapper extends R.RWrapper<TagKeyWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_6862/net.minecraft.tags.TagKey");

    protected TagKeyWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static TagKeyWrapper inst(Object instance) {
        return new TagKeyWrapper(instance);
    }
}
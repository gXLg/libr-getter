package dev.gxlg.multiversion.gen.com.mojang.brigadier.builder;

import dev.gxlg.multiversion.R;

public class LiteralArgumentBuilderWrapper extends dev.gxlg.multiversion.gen.com.mojang.brigadier.builder.ArgumentBuilderWrapper {
    public static final R.RClass clazz = R.clz("com.mojang.brigadier.builder.LiteralArgumentBuilder");

    protected LiteralArgumentBuilderWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static LiteralArgumentBuilderWrapper inst(Object instance) {
        return new LiteralArgumentBuilderWrapper(instance);
    }
}
package dev.gxlg.multiversion.gen.net.minecraft.world;

import dev.gxlg.multiversion.R;

public class InteractionResultWrapper extends R.RWrapper<InteractionResultWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1269/net.minecraft.world.InteractionResult");

    protected InteractionResultWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static InteractionResultWrapper inst(Object instance) {
        return new InteractionResultWrapper(instance);
    }

    public static net.minecraft.world.InteractionResult FAIL() {
        return (net.minecraft.world.InteractionResult) clazz.fld("field_5814/FAIL").get();
    }
}
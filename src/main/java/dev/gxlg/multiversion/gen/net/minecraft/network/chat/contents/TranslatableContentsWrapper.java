package dev.gxlg.multiversion.gen.net.minecraft.network.chat.contents;

import dev.gxlg.multiversion.R;

public class TranslatableContentsWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2588/net.minecraft.network.chat.contents.TranslatableContents");

    public TranslatableContentsWrapper(String key, Object[] args){
        this(clazz.constr(String.class, Object.class.arrayType()).newInst(key, args).self());
    }

    protected TranslatableContentsWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static TranslatableContentsWrapper inst(Object instance) {
        return new TranslatableContentsWrapper(instance);
    }
}
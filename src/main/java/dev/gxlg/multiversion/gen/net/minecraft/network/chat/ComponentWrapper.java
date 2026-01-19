package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class ComponentWrapper extends R.RWrapper<ComponentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2561/net.minecraft.network.chat.Component");

    protected ComponentWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper plainCopy(){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.inst(this.instance).mthd("method_27662/plainCopy").invk());
    }

    public static ComponentWrapper inst(Object instance) {
        return new ComponentWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper translatable(String key, Object[] args){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.mthd("method_43469/translatable", String.class, Object.class.arrayType()).invk(key, args));
    }
}
package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;

public class MutableComponentWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_5250/net.minecraft.network.chat.MutableComponent");

    protected MutableComponentWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper withStyle(net.minecraft.ChatFormatting formatting){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(this.instance.mthd("method_27692/withStyle", net.minecraft.ChatFormatting.class).invk(formatting));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper append(String string){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(this.instance.mthd("method_27693/append", String.class).invk(string));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper append(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper component){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(this.instance.mthd("method_10852/append", dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz).invk(component.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper setStyle(net.minecraft.network.chat.Style style){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(this.instance.mthd("method_10862/setStyle", net.minecraft.network.chat.Style.class).invk(style));
    }

    public static MutableComponentWrapper inst(Object instance) {
        return new MutableComponentWrapper(instance);
    }
}
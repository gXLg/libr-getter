package dev.gxlg.multiversion.gen.net.minecraft.locale;

import dev.gxlg.multiversion.R;

public class LanguageWrapper extends R.RWrapper<LanguageWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2477/net.minecraft.locale.Language");

    protected LanguageWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public String get(String translationKey){
        return (String) clazz.inst(this.instance).mthd("method_4679/get", String.class).invk(translationKey);
    }

    public String get2(String translationKey){
        return (String) clazz.inst(this.instance).mthd("method_48307/get", String.class).invk(translationKey);
    }

    public static LanguageWrapper inst(Object instance) {
        return new LanguageWrapper(instance);
    }
}
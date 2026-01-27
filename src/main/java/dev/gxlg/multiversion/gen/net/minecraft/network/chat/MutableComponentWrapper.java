package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MutableComponentWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_5250/net.minecraft.network.chat.MutableComponent");

    private int superCall = 0;

    protected MutableComponentWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper withStyle(dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper formatting){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.inst(this.instance).mthd("method_27692/withStyle", dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz).invk(formatting.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper withStyle(dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper style){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.inst(this.instance).mthd("method_27696/withStyle", dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.clazz).invk(style.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper append(String string){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.inst(this.instance).mthd("method_27693/append", dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.clazz, String.class).invk(string));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper append(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper component){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.inst(clazz.inst(this.instance).mthd("method_10852/append", dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz).invk(component.unwrap()));
    }

    public static MutableComponentWrapper inst(Object instance) {
        return instance == null ? null : new MutableComponentWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") MutableComponentWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
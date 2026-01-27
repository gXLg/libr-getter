package dev.gxlg.multiversion.gen.net.minecraft.network.chat;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class StyleWrapper extends R.RWrapper<StyleWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2583/net.minecraft.network.chat.Style");

    private int superCall = 0;

    protected StyleWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper withClickEvent(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper event){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.inst(clazz.inst(this.instance).mthd("method_10958/withClickEvent", dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper.clazz).invk(event.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper withHoverEvent(dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper event){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.inst(clazz.inst(this.instance).mthd("method_10949/withHoverEvent", dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper.clazz).invk(event.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper withColor(dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper formatting){
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.inst(clazz.inst(this.instance).mthd("method_10977/withColor", dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz).invk(formatting.unwrap()));
    }

    public static StyleWrapper inst(Object instance) {
        return instance == null ? null : new StyleWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper EMPTY() {
        return dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.inst(clazz.fld("field_24360/EMPTY", dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") StyleWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
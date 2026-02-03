package dev.gxlg.multiversion.gen.net.minecraft;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ChatFormattingWrapper extends R.RWrapper<ChatFormattingWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_124/net.minecraft.ChatFormatting");

    private int superCall = 0;

    protected ChatFormattingWrapper(Object instance) {
        super(instance);
    }

    public static ChatFormattingWrapper inst(Object instance) {
        return instance == null ? null : new ChatFormattingWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper OBFUSCATED() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1051/OBFUSCATED", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper YELLOW() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1054/YELLOW", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper STRIKETHROUGH() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1055/STRIKETHROUGH", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper ITALIC() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1056/ITALIC", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper DARK_BLUE() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1058/DARK_BLUE", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper GREEN() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1060/GREEN", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper RED() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1061/RED", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper DARK_AQUA() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1062/DARK_AQUA", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper DARK_GRAY() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1063/DARK_GRAY", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper DARK_PURPLE() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1064/DARK_PURPLE", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper GOLD() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1065/GOLD", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper BOLD() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1067/BOLD", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper WHITE() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1068/WHITE", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper RESET() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1070/RESET", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper UNDERLINE() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1073/UNDERLINE", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper BLACK() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1074/BLACK", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper AQUA() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1075/AQUA", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper LIGHT_PURPLE() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1076/LIGHT_PURPLE", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper DARK_GREEN() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1077/DARK_GREEN", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper BLUE() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1078/BLUE", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper DARK_RED() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1079/DARK_RED", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper GRAY() {
        return dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.inst(clazz.fld("field_1080/GRAY", dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper.clazz.self()).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ChatFormattingWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
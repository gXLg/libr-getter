package dev.gxlg.multiversion.gen.net.minecraft.core;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class DirectionWrapper extends R.RWrapper<DirectionWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2350/net.minecraft.core.Direction");

    private int superCall = 0;

    protected DirectionWrapper(Object instance) {
        super(instance);
    }

    public static DirectionWrapper inst(Object instance) {
        return instance == null ? null : new DirectionWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper UP() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.fld("field_11036/UP", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper DOWN() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.fld("field_11033/DOWN", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper EAST() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.fld("field_11034/EAST", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper SOUTH() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.fld("field_11035/SOUTH", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper WEST() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.fld("field_11039/WEST", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper NORTH() {
        return dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.inst(clazz.fld("field_11043/NORTH", dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") DirectionWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
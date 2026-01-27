package dev.gxlg.multiversion.gen.net.minecraft.world.item;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ItemsWrapper extends R.RWrapper<ItemsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1802/net.minecraft.world.item.Items");

    private int superCall = 0;

    protected ItemsWrapper(Object instance) {
        super(instance);
    }

    public static ItemsWrapper inst(Object instance) {
        return instance == null ? null : new ItemsWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper BOOK() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.inst(clazz.fld("field_8529/BOOK", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper ENCHANTED_BOOK() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.inst(clazz.fld("field_8598/ENCHANTED_BOOK", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper EMERALD() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.inst(clazz.fld("field_8687/EMERALD", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper LECTERN() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.inst(clazz.fld("field_16312/LECTERN", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ItemsWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
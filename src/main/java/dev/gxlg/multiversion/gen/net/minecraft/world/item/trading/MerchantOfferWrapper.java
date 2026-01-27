package dev.gxlg.multiversion.gen.net.minecraft.world.item.trading;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MerchantOfferWrapper extends R.RWrapper<MerchantOfferWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1914/net.minecraft.world.item.trading.MerchantOffer");

    private int superCall = 0;

    protected MerchantOfferWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper getResult(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.inst(clazz.inst(this.instance).mthd("method_8250/getResult", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper getCostA(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.inst(clazz.inst(this.instance).mthd("method_19272/getCostA", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper getCostB(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.inst(clazz.inst(this.instance).mthd("method_8247/getCostB", dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper.clazz).invk());
    }

    public static MerchantOfferWrapper inst(Object instance) {
        return instance == null ? null : new MerchantOfferWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") MerchantOfferWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
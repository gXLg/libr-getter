package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientboundMerchantOffersPacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3943/net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket");

    private int superCall = 0;

    protected ClientboundMerchantOffersPacketWrapper(Object instance) {
        super(instance);
    }

    public boolean canRestock(){
        return (boolean) clazz.inst(this.instance).mthd("method_20722/canRestock", boolean.class).invk();
    }

    public java.util.List<dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper> getOffers(){
        return dev.gxlg.multiversion.adapters.java.util.ListAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper::inst).apply(clazz.inst(this.instance).mthd("method_17590/getOffers", java.util.List.class).invk());
    }

    public static ClientboundMerchantOffersPacketWrapper inst(Object instance) {
        return instance == null ? null : new ClientboundMerchantOffersPacketWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientboundMerchantOffersPacketWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
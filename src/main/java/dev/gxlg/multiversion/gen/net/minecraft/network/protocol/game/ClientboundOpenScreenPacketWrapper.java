package dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClientboundOpenScreenPacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3944/net.minecraft.network.protocol.game.ClientboundOpenScreenPacket");

    private int superCall = 0;

    protected ClientboundOpenScreenPacketWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper getType(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper.inst(clazz.inst(this.instance).mthd("method_17593/getType", dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper.clazz).invk());
    }

    public int getContainerId(){
        return (int) clazz.inst(this.instance).mthd("method_17592/getContainerId", int.class).invk();
    }

    public static ClientboundOpenScreenPacketWrapper inst(Object instance) {
        return instance == null ? null : new ClientboundOpenScreenPacketWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") ClientboundOpenScreenPacketWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.network.protocol.PacketWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
package dev.gxlg.multiversion.gen.net.minecraft.client.player;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LocalPlayerWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.PlayerWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_746/net.minecraft.client.player.LocalPlayer");

    private int superCall = 0;

    private final R.RField clientLevelField;

    protected LocalPlayerWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.clientLevelField = rInstance.fld("field_17892/clientLevel", dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.clazz);
    }

    public void displayClientMessage(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper message, boolean actionBar){
        clazz.inst(this.instance).mthd("method_7353/displayClientMessage", void.class, dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper.clazz, boolean.class).invk(message.unwrap(), actionBar);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper level(){
        return dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.inst(clazz.inst(this.instance).mthd("method_73183/level", dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.clazz).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper getLevel(){
        return dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.inst(clazz.inst(this.instance).mthd("method_37908/getLevel", dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.clazz).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper getClientLevelField() {
        return dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.inst(this.clientLevelField.get());
    }

    public void setClientLevelField(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper value) {
        this.clientLevelField.set(value.unwrap());
    }

    public static LocalPlayerWrapper inst(Object instance) {
        return instance == null ? null : new LocalPlayerWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") LocalPlayerWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.PlayerWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
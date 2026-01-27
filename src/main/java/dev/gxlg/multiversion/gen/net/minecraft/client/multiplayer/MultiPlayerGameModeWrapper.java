package dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MultiPlayerGameModeWrapper extends R.RWrapper<MultiPlayerGameModeWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_636/net.minecraft.client.multiplayer.MultiPlayerGameMode");

    private int superCall = 0;

    protected MultiPlayerGameModeWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper useItemOn(dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper player, dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper hand, dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper hit){
        return dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.inst(clazz.inst(this.instance).mthd("method_2896/useItemOn", dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper.clazz).invk(player.unwrap(), hand.unwrap(), hit.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper useItemOn(dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper player, dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper clientWorld, dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper hand, dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper hit){
        return dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.inst(clazz.inst(this.instance).mthd("method_2896/useItemOn", dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper.clazz).invk(player.unwrap(), clientWorld.unwrap(), hand.unwrap(), hit.unwrap()));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper interact(dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.PlayerWrapper player, dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper target, dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper hand){
        return dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.inst(clazz.inst(this.instance).mthd("method_2905/interact", dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.PlayerWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper.clazz).invk(player.unwrap(), target.unwrap(), hand.unwrap()));
    }

    public void handleInventoryMouseClick(int containerId, int slot, int otherSlot, dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper clickType, dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.PlayerWrapper player){
        clazz.inst(this.instance).mthd("method_2906/handleInventoryMouseClick", void.class, int.class, int.class, int.class, dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.PlayerWrapper.clazz).invk(containerId, slot, otherSlot, clickType.unwrap(), player.unwrap());
    }

    public boolean continueDestroyBlock(dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper pos, dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper side){
        return (boolean) clazz.inst(this.instance).mthd("method_2902/continueDestroyBlock", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper.clazz).invk(pos.unwrap(), side.unwrap());
    }

    public static MultiPlayerGameModeWrapper inst(Object instance) {
        return instance == null ? null : new MultiPlayerGameModeWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") MultiPlayerGameModeWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
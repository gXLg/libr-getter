package dev.gxlg.multiversion.gen.net.minecraft.client;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MinecraftWrapper extends R.RWrapper<MinecraftWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_310/net.minecraft.client.Minecraft");

    private int superCall = 0;

    private final R.RField screenField;

    private final R.RField playerField;

    private final R.RField levelField;

    private final R.RField hitResultField;

    private final R.RField gameModeField;

    protected MinecraftWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.screenField = rInstance.fld("field_1755/screen", dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper.clazz);
        this.playerField = rInstance.fld("field_1724/player", dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper.clazz);
        this.levelField = rInstance.fld("field_1687/level", dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.clazz);
        this.hitResultField = rInstance.fld("field_1765/hitResult", dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper.clazz);
        this.gameModeField = rInstance.fld("field_1761/gameMode", dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper.clazz);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper getScreenField() {
        Object __return = this.screenField.get();
        return __return == null ? null : dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper.inst(__return);
    }

    public void setScreenField(dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper value) {
        this.screenField.set(value == null ? null : value.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper getPlayerField() {
        Object __return = this.playerField.get();
        return __return == null ? null : dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper.inst(__return);
    }

    public void setPlayerField(dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper value) {
        this.playerField.set(value == null ? null : value.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper getLevelField() {
        Object __return = this.levelField.get();
        return __return == null ? null : dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper.inst(__return);
    }

    public void setLevelField(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper value) {
        this.levelField.set(value == null ? null : value.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper getHitResultField() {
        Object __return = this.hitResultField.get();
        return __return == null ? null : dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper.inst(__return);
    }

    public void setHitResultField(dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper value) {
        this.hitResultField.set(value == null ? null : value.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper getGameModeField() {
        Object __return = this.gameModeField.get();
        return __return == null ? null : dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper.inst(__return);
    }

    public void setGameModeField(dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper value) {
        this.gameModeField.set(value == null ? null : value.unwrap());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper getConnection(){
        Object __return = clazz.inst(this.instance).mthd("method_1562/getConnection", dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper.clazz).invk();
        return __return == null ? null : dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper.inst(__return);
    }

    public void setScreen(dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper screen){
        clazz.inst(this.instance).mthd("method_1507/setScreen", void.class, dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper.clazz).invk(screen.unwrap());
    }

    public static MinecraftWrapper inst(Object instance) {
        return instance == null ? null : new MinecraftWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper getInstance(){
        return dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.inst(clazz.mthd("method_1551/getInstance", dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper.clazz).invk());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") MinecraftWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
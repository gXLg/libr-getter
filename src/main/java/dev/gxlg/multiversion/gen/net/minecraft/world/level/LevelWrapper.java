package dev.gxlg.multiversion.gen.net.minecraft.world.level;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LevelWrapper extends R.RWrapper<LevelWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1937/net.minecraft.world.level.Level");

    private int superCall = 0;

    protected LevelWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper getBlockState(dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper pos){
        return dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper.inst(clazz.inst(this.instance).mthd("method_8320/getBlockState", dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz).invk(pos.unwrap()));
    }

    public boolean setBlockAndUpdate(dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper pos, dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper state){
        return (boolean) clazz.inst(this.instance).mthd("method_8501/setBlockAndUpdate", boolean.class, dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper.clazz).invk(pos.unwrap(), state.unwrap());
    }

    public void playLocalSound(double x, double y, double z, dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundEventWrapper sound, dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundSourceWrapper source, float volume, float pitch, boolean distanceDelay){
        clazz.inst(this.instance).mthd("method_8486/playLocalSound", void.class, double.class, double.class, double.class, dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundEventWrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.sounds.SoundSourceWrapper.clazz, float.class, float.class, boolean.class).invk(x, y, z, sound.unwrap(), source.unwrap(), volume, pitch, distanceDelay);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.RegistryAccessWrapper registryAccess(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.RegistryAccessWrapper.inst(clazz.inst(this.instance).mthd("method_30349/registryAccess", dev.gxlg.multiversion.gen.net.minecraft.core.RegistryAccessWrapper.clazz).invk());
    }

    public static LevelWrapper inst(Object instance) {
        return instance == null ? null : new LevelWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") LevelWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
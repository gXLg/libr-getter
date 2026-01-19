package dev.gxlg.multiversion.gen.net.minecraft.world.level;

import dev.gxlg.multiversion.R;

public class LevelWrapper extends R.RWrapper<LevelWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1937/net.minecraft.world.level.Level");

    protected LevelWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public void playLocalSound(double x, double y, double z, net.minecraft.sounds.SoundEvent sound, net.minecraft.sounds.SoundSource source, float volume, float pitch, boolean distanceDelay){
        clazz.inst(this.instance).mthd("method_8486/playLocalSound", double.class, double.class, double.class, net.minecraft.sounds.SoundEvent.class, net.minecraft.sounds.SoundSource.class, float.class, float.class, boolean.class).invk(x, y, z, sound, source, volume, pitch, distanceDelay);
    }

    public static LevelWrapper inst(Object instance) {
        return new LevelWrapper(instance);
    }
}
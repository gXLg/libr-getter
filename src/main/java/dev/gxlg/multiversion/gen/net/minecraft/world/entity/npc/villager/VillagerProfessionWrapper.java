package dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager;

import dev.gxlg.multiversion.R;

public class VillagerProfessionWrapper extends R.RWrapper<VillagerProfessionWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3852/net.minecraft.world.entity.npc.villager.VillagerProfession");

    protected VillagerProfessionWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static VillagerProfessionWrapper inst(Object instance) {
        return new VillagerProfessionWrapper(instance);
    }
}
package dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager;

import dev.gxlg.multiversion.R;

public class VillagerDataWrapper extends R.RWrapper<VillagerDataWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3850/net.minecraft.world.entity.npc.villager.VillagerData");

    protected VillagerDataWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper profession(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper.inst(clazz.inst(this.instance).mthd("comp_3521/profession").invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper getProfession(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper.inst(clazz.inst(this.instance).mthd("method_16924/getProfession").invk());
    }

    public static VillagerDataWrapper inst(Object instance) {
        return new VillagerDataWrapper(instance);
    }
}
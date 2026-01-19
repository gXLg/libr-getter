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

    public static dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper LIBRARIAN() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper.inst(clazz.fld("field_17060/LIBRARIAN").get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper LIBRARIAN2() {
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.fld("field_17060/LIBRARIAN").get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper NONE() {
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper.inst(clazz.fld("field_17051/NONE").get());
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper NONE2() {
        return dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper.inst(clazz.fld("field_17051/NONE").get());
    }
}
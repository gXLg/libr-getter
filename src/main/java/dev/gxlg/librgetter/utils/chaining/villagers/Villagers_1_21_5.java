package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerWrapper;

public class Villagers_1_21_5 extends Villagers_1_17_0 {
    @Override
    public boolean isVillagerLibrarian(VillagerWrapper villager) {
        VillagerDataWrapper villagerData = villager.getVillagerData();
        ResourceKeyWrapper librarianProfession = VillagerProfessionWrapper.LIBRARIAN2();
        return villagerData.profession().is(librarianProfession);
    }

    @Override
    public boolean isVillagerUnemployed(VillagerWrapper villager) {
        VillagerDataWrapper villagerData = villager.getVillagerData();
        ResourceKeyWrapper noneProfession = VillagerProfessionWrapper.NONE2();
        return villagerData.profession().is(noneProfession);
    }
}

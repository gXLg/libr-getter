package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerWrapper;

public class Villagers_1_17_0 extends Villagers {
    @Override
    public boolean isVillagerLibrarian(VillagerWrapper villager) {
        VillagerDataWrapper villagerData = villager.getVillagerData();
        VillagerProfessionWrapper librarianProfession = VillagerProfessionWrapper.LIBRARIAN();
        return villagerData.getProfession().equals(librarianProfession);
    }

    @Override
    public boolean isVillagerUnemployed(VillagerWrapper villager) {
        VillagerDataWrapper villagerData = villager.getVillagerData();
        VillagerProfessionWrapper noneProfession = VillagerProfessionWrapper.NONE();
        return villagerData.getProfession().equals(noneProfession);
    }
}

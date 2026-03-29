package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.VillagerData;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.VillagerProfession;

public class Villagers_1_17_0 extends Villagers.Base {
    @Override
    public boolean isVillagerLibrarian(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        VillagerProfession librarianProfession = VillagerProfession.LIBRARIAN();
        return villagerData.getProfession().equals(librarianProfession);
    }

    @Override
    public boolean isVillagerUnemployed(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        VillagerProfession noneProfession = VillagerProfession.NONE();
        return villagerData.getProfession().equals(noneProfession);
    }
}

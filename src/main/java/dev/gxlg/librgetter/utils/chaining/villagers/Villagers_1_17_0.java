package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;

public class Villagers_1_17_0 extends Villagers {
    @Override
    public boolean isVillagerLibrarian(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        VillagerProfessionWrapper librarianProfession = VillagerProfessionWrapper.LIBRARIAN();
        return VillagerDataWrapper.inst(villagerData).getProfession().equals(librarianProfession);
    }

    @Override
    public boolean isVillagerUnemployed(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        VillagerProfessionWrapper noneProfession = VillagerProfessionWrapper.NONE();
        return VillagerDataWrapper.inst(villagerData).getProfession().equals(noneProfession);
    }
}

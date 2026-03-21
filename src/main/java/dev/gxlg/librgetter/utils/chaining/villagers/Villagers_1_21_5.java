package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.VillagerData;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.VillagerProfession;

public class Villagers_1_21_5 extends Villagers_1_17_0 {
    @Override
    public boolean isVillagerLibrarian(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        ResourceKey librarianProfession = VillagerProfession.LIBRARIAN2();
        return villagerData.profession().is(librarianProfession);
    }

    @Override
    public boolean isVillagerUnemployed(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        ResourceKey noneProfession = VillagerProfession.NONE2();
        return villagerData.profession().is(noneProfession);
    }
}

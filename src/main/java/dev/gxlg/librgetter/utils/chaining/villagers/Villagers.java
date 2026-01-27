package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerWrapper;

public abstract class Villagers {
    public abstract boolean isVillagerLibrarian(VillagerWrapper villager);

    public abstract boolean isVillagerUnemployed(VillagerWrapper villager);

    private static Villagers implementation = null;

    public static Villagers getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.21.5")) {
            implementation = new Villagers_1_17_0();
        } else {
            implementation = new Villagers_1_21_5();
        }
        return implementation;
    }
}

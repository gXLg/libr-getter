package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;

public abstract class Villagers {
    public abstract boolean isVillagerLibrarian(Villager villager);

    public abstract boolean isVillagerUnemployed(Villager villager);

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

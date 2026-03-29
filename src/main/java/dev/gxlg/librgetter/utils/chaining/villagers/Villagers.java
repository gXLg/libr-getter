package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;

public class Villagers {
    private static final Base implementation;

    static {
        if (V.lower("1.21.5")) {
            implementation = new Villagers_1_17_0();
        } else {
            implementation = new Villagers_1_21_5();
        }
    }

    public static boolean isVillagerLibrarian(Villager villager) {
        return implementation.isVillagerLibrarian(villager);
    }

    public static boolean isVillagerUnemployed(Villager villager) {
        return implementation.isVillagerUnemployed(villager);
    }

    public abstract static class Base {
        public abstract boolean isVillagerLibrarian(Villager villager);

        public abstract boolean isVillagerUnemployed(Villager villager);
    }
}

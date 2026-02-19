package dev.gxlg.librgetter.utils.chaining.villagers;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;

public class Villagers {
    private static Base implementation = null;

    public static boolean isVillagerLibrarian(Villager villager) {
        return getImpl().isVillagerLibrarian(villager);
    }

    public static boolean isVillagerUnemployed(Villager villager) {
        return getImpl().isVillagerUnemployed(villager);
    }

    private static Base getImpl() {
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

    public abstract static class Base {
        public abstract boolean isVillagerLibrarian(Villager villager);

        public abstract boolean isVillagerUnemployed(Villager villager);
    }
}

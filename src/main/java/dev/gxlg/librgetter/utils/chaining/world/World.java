package dev.gxlg.librgetter.utils.chaining.world;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;

public class World {
    private static final Base implementation;

    static {
        if (!V.lower("1.19.3")) {
            implementation = new World_1_19_3();
        } else {
            implementation = new World_1_17_0();
        }
    }

    public static ResourceKey getDimensionRegistryKey() {
        return implementation.getDimensionRegistryKey();
    }

    public abstract static class Base {
        public abstract ResourceKey getDimensionRegistryKey();
    }
}

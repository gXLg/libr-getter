package dev.gxlg.librgetter.utils.chaining.world;

import dev.gxlg.versiont.gen.net.minecraft.core.Registry;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;

public class World_1_17_0 extends World.Base {
    @Override
    public ResourceKey getDimensionRegistryKey() {
        return Registry.DIMENSION_REGISTRY();
    }
}

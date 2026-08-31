package dev.gxlg.librgetter.utils.chaining.world;

import dev.gxlg.versiont.gen.net.minecraft.core.registries.Registries;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;

public class World_1_19_3 extends World_1_17_0 {
    @Override
    public ResourceKey getDimensionRegistryKey() {
        return Registries.DIMENSION();
    }
}

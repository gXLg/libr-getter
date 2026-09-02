package dev.gxlg.librgetter.utils.chaining.world;

import dev.gxlg.versiont.gen.net.minecraft.core.Registry;
import dev.gxlg.versiont.gen.net.minecraft.core.particles.DustParticleOptions;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;
import dev.gxlg.versiont.gen.org.joml.Vector3f;

public class World_1_17_0 extends World.Base {
    @Override
    public ResourceKey getDimensionRegistryKey() {
        return Registry.DIMENSION_REGISTRY();
    }

    @Override
    public DustParticleOptions createDustParticle(int r, int g, int b, float scale) {
        Vector3f color = new Vector3f(r / 255.0F, g / 255.0F, b / 255.0F);
        return new DustParticleOptions(color, scale);
    }
}

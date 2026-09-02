package dev.gxlg.librgetter.utils.chaining.world;

import dev.gxlg.versiont.gen.net.minecraft.core.particles.DustParticleOptions;

public class World_1_21_2 extends World_1_19_3 {
    @Override
    public DustParticleOptions createDustParticle(int r, int g, int b, float scale) {
        int color = (r << 16) | (g << 8) | b;
        return new DustParticleOptions(color, scale);
    }
}

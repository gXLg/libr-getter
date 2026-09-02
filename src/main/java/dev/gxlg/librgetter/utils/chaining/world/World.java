package dev.gxlg.librgetter.utils.chaining.world;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.core.particles.DustParticleOptions;

public class World {
    private static final Base implementation;

    static {
        if (!V.lower("1.21.2")) {
            implementation = new World_1_21_2();
        } else {
            implementation = new World_1_17_0();
        }
    }

    public static DustParticleOptions createDustParticle(int r, int g, int b, float scale) {
        return implementation.createDustParticle(r, g, b, scale);
    }

    public abstract static class Base {
        public abstract DustParticleOptions createDustParticle(int r, int g, int b, float scale);
    }
}

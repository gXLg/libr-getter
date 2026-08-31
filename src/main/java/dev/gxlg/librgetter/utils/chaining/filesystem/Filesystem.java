package dev.gxlg.librgetter.utils.chaining.filesystem;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;

import java.nio.file.Path;

public class Filesystem {
    private static final Base implementation;

    static {
        if (!V.lower("1.18")) {
            implementation = new Filesystem_1_18_0();
        } else {
            implementation = new Filesystem_1_17_0();
        }
    }

    public static Path getDimensionPath(ResourceKey dimension, Path worldRoot) {
        return implementation.getDimensionPath(dimension, worldRoot);
    }

    public abstract static class Base {
        public abstract Path getDimensionPath(ResourceKey dimension, Path worldRoot);
    }
}

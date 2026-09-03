package dev.gxlg.librgetter.utils.chaining.filesystem;

import dev.gxlg.versiont.gen.net.minecraft.resources.ResourceKey;
import dev.gxlg.versiont.gen.net.minecraft.world.level.dimension.DimensionType;

import java.nio.file.Path;

public class Filesystem_1_17_0 extends Filesystem.Base {
    @Override
    public Path getDimensionPath(ResourceKey dimension, Path worldRoot) {
        return DimensionType.getStorageFolder(dimension, worldRoot.toFile()).toPath();
    }
}

package dev.gxlg.multiversion;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class V {
    private static MinecraftVersion version = null;

    public static MinecraftVersion getVersion() {
        if (version != null) {
            return version;
        }
        R.RClass constants = R.clz("net.minecraft.class_155/net.minecraft.SharedConstants");
        try {
            R.RClass gameVersionClz = R.clz("net.minecraft.class_6489/com.mojang.bridge.game.GameVersion");
            Object gameVersion = constants.mthd("method_16673/getCurrentVersion", gameVersionClz).invk();
            version = new MinecraftVersion((String) gameVersionClz.inst(gameVersion).mthd("method_48019/getName", String.class).invk());
        } catch (Exception ignored) {
            R.RClass gameVersionClz = R.clz("net.minecraft.class_6489/net.minecraft.WorldVersion");
            Object gameVersion = constants.mthd("method_16673/getCurrentVersion", gameVersionClz).invk();
            version = new MinecraftVersion((String) gameVersionClz.inst(gameVersion).mthd("comp_4025/name", String.class).invk());
        }
        return version;
    }

    public static boolean higher(String other) {
        return getVersion().higher(other);
    }

    public static boolean equal(String other) {
        return getVersion().equal(other);
    }

    public static boolean lower(String other) {
        return getVersion().lower(other);
    }

    public static class MinecraftVersion {
        private final int major;

        private final int minor;

        private final int patch;

        private final Map<String, Integer> cache = new HashMap<>();

        public MinecraftVersion(String version) {
            String[] mainParts = version.split("-", 2);
            String[] nums = mainParts[0].split("\\.");
            this.major = nums.length > 0 ? Integer.parseInt(nums[0]) : 0;
            this.minor = nums.length > 1 ? Integer.parseInt(nums[1]) : 0;
            this.patch = nums.length > 2 ? Integer.parseInt(nums[2]) : 0;
        }

        public int compare(String other) {
            return cache.computeIfAbsent(
                other, i -> {
                    MinecraftVersion v = new MinecraftVersion(other);
                    if (this.major != v.major) {
                        return Integer.compare(this.major, v.major);
                    }
                    if (this.minor != v.minor) {
                        return Integer.compare(this.minor, v.minor);
                    }
                    return Integer.compare(this.patch, v.patch);
                }
            );
        }

        public boolean higher(String other) {
            return this.compare(other) > 0;
        }

        public boolean lower(String other) {
            return this.compare(other) < 0;
        }

        public boolean equal(String other) {
            return this.compare(other) == 0;
        }
    }
}

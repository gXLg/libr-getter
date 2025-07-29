package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.utils.reflection.Minecraft;

public class MultiVersion {
    private static final String version;

    static {
        version = Minecraft.getVersion();
    }

    public static String getVersion() {
        return version;
    }

    public static boolean isApiLevel(ApiLevel level) {
        if (version.equals("1.21.5") || version.equals("1.21.6") || version.equals("1.21.7") || version.equals("1.21.8")) return true;
        if (level == ApiLevel.MORE_ABSTRACTION) return false;

        if (version.equals("1.21.4") || version.equals("1.21.3") || version.equals("1.21.2") || version.equals("1.21.1") || version.equals("1.21"))
            return true;
        if (level == ApiLevel.EFFECTS) return false;

        if (version.equals("1.20.6") || version.equals("1.20.5")) return true;
        if (level == ApiLevel.COMPONENTS) return false;

        if (version.equals("1.20.4") || version.equals("1.20.3") || version.equals("1.20.2") || version.equals("1.20.1") || version.equals("1.20") || version.equals("1.19.4") || version.equals("1.19.3"))
            return true;
        if (level == ApiLevel.TAGS) return false;

        if (version.equals("1.19.2") || version.equals("1.19.1") || version.equals("1.19")) return true;
        if (level == ApiLevel.API_COMMAND_V2) return false;

        if (version.equals("1.18.2") || version.equals("1.18.1") || version.equals("1.18") || version.equals("1.17.1"))
            return true;
        if (level == ApiLevel.VILLAGER_PACKET) return false;

        if (version.equals("1.17")) return true;
        return level != ApiLevel.BASE;
    }

    public enum ApiLevel {
        BASE, VILLAGER_PACKET, API_COMMAND_V2, TAGS, COMPONENTS, EFFECTS, MORE_ABSTRACTION
    }
}

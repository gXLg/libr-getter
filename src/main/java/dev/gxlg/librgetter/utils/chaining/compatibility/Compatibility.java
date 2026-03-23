package dev.gxlg.librgetter.utils.chaining.compatibility;

import dev.gxlg.versiont.api.V;

public class Compatibility {
    private static final Base implementation;

    static {
        if (V.lower("1.21.2")) {
            implementation = new Compatibility_1_17_0();
        } else {
            implementation = new Compatibility_1_20_2();
        }
    }

    public static void sendCycleTradesPacket() {
        implementation.sendCycleTradesPacket();
    }

    public static boolean isModPresent(String modID) {
        return implementation.isModPresent(modID);
    }

    public abstract static class Base {
        public abstract void sendCycleTradesPacket();

        public abstract boolean isModPresent(String modID);
    }
}

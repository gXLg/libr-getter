package dev.gxlg.librgetter.utils.chaining.support;

import dev.gxlg.versiont.api.V;

public class Support {
    private static Base implementation;

    public static void sendCycleTradesPacket() {
        getImpl().sendCycleTradesPacket();
    }

    public static boolean isUsingTradeCycling() {
        return getImpl().isUsingTradeCycling();
    }

    public static boolean isModPresent(String modID) {
        return getImpl().isModPresent(modID);
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.21.2")) {
            implementation = new Support_1_17_0();
        } else {
            implementation = new Support_1_20_2();
        }
        return implementation;
    }

    public abstract static class Base {
        public abstract void sendCycleTradesPacket();

        public abstract boolean isUsingTradeCycling();

        public abstract boolean isModPresent(String modID);
    }
}

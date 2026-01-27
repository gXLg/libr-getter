package dev.gxlg.librgetter.utils.chaining.support;

import dev.gxlg.multiversion.V;

public abstract class Support {
    public abstract void sendCycleTradesPacket();

    public abstract boolean isUsingTradeCycling();

    public abstract boolean isModPresent(String modID);

    private static Support implementation;

    public static Support getImpl() {
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
}

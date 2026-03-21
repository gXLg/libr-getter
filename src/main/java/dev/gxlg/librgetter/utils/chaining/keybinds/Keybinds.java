package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;

public class Keybinds {
    private static Base implementation = null;

    public static void registerKeybinds() {
        getImpl().registerKeybinds();
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.21.9")) {
            implementation = new Keybinds_1_17_0();
        } else if (V.lower("26.1")) {
            implementation = new Keybinds_1_21_9();
        } else {
            implementation = new Keybinds_26_1_0();
        }
        return implementation;
    }

    public abstract static class Base {
        public abstract void registerKeybinds();

        protected record KeybindData(String id, InputConstants$Type type, int key) { }
    }
}

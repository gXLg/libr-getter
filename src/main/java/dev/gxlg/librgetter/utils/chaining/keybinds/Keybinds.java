package dev.gxlg.librgetter.utils.chaining.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import dev.gxlg.multiversion.V;

public abstract class Keybinds {
    public abstract void registerKeybinds();

    private static Keybinds implementation = null;

    public static Keybinds getImpl() {
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

    protected record KeybindData(String id, InputConstants.Type type, int key) { }
}

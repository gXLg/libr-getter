package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.keybinds.Keybind;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;

public class Keybinds {
    private static final Base implementation;

    static {
        if (V.lower("1.21.9")) {
            implementation = new Keybinds_1_17_0();
        } else if (V.lower("26.1")) {
            implementation = new Keybinds_1_21_9();
        } else {
            implementation = new Keybinds_26_1_0();
        }
    }

    public static KeyMapping createKeyMapping(Keybind keybindData, String modId) {
        return implementation.createKeyMapping(keybindData, modId);
    }

    public static void register(KeyMapping keyMapping) {
        implementation.register(keyMapping);
    }

    public abstract static class Base {
        public abstract void register(KeyMapping keyMapping);

        public abstract KeyMapping createKeyMapping(Keybind keybindData, String modId);
    }
}

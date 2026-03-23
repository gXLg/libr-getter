package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.keybinds.KeybindManager;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;

public class Keybinds_1_17_0 extends Keybinds.Base {
    @Override
    public void register(KeyMapping keyMapping) {
        KeyBindingHelper.registerKeyBinding(keyMapping);
    }

    @Override
    public KeyMapping createKeyMapping(KeybindManager.KeybindData keybindData, String modId) {
        String modCategory = "key.category." + modId + ".category";
        return new KeyMapping(keybindData.getId(), keybindData.getType(), keybindData.getKey(), modCategory);
    }
}

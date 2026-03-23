package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.keybinds.KeybindManager;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping$Category;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class Keybinds_1_21_9 extends Keybinds_1_17_0 {
    @Override
    public KeyMapping createKeyMapping(KeybindManager.KeybindData keybindData, String modId) {
        KeyMapping$Category modCategory = KeyMapping$Category.register(Identifier.tryBuild(modId, "category"));
        return new KeyMapping(keybindData.getId(), keybindData.getType(), keybindData.getKey(), modCategory);
    }
}

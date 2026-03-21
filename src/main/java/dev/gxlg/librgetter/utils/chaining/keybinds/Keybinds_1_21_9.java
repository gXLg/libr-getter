package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping$Category;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class Keybinds_1_21_9 extends Keybinds_1_17_0 {
    @Override
    protected KeyMapping createKeyMapping(KeybindData keybindData) {
        return new KeyMapping(keybindData.id(), keybindData.type(), keybindData.key(), modCategory);
    }

    private final static KeyMapping$Category modCategory = KeyMapping$Category.register(Identifier.tryBuild(LibrGetter.MOD_ID, "category"));
}

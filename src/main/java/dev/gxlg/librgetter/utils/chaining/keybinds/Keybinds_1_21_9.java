package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.multiversion.gen.net.minecraft.client.KeyMapping$CategoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper;

public class Keybinds_1_21_9 extends Keybinds_1_17_0 {
    @Override
    protected KeyMappingWrapper createKeyMapping(KeybindData keybindData) {
        return new KeyMappingWrapper(keybindData.id(), keybindData.type(), keybindData.key(), modCategory);
    }

    private final static KeyMapping$CategoryWrapper modCategory = KeyMapping$CategoryWrapper.register(IdentifierWrapper.tryBuild(LibrGetter.MOD_ID, "category"));
}

package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelperWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper;

public class Keybinds_26_1_0 extends Keybinds_1_21_9 {
    @Override
    protected void register(KeyMappingWrapper keyMapping) {
        KeyMappingHelperWrapper.registerKeyMapping(keyMapping);
    }
}

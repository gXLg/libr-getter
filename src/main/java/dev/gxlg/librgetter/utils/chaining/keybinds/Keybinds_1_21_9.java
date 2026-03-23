package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.keybinds.Keybind;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping$Category;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class Keybinds_1_21_9 extends Keybinds_1_17_0 {
    @Override
    public KeyMapping createKeyMapping(Keybind keybindData, String modId) {
        KeyMapping$Category modCategory = modCategories.computeIfAbsent(modId, k -> KeyMapping$Category.register(Identifier.tryBuild(modId, "category")));
        return new KeyMapping(keybindData.getId(), keybindData.getType(), keybindData.getKey(), modCategory);
    }

    private static final Map<String, KeyMapping$Category> modCategories = new HashMap<>();
}

package dev.gxlg.librgetter.utils.reflection.chaining.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class Keybinds_1_17_0 extends Keybinds {
    @Override
    public void registerKeybinds() {
        KeyMapping configMenuKeyMapping = createKeyMapping(new KeybindData("librgetter.keys.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K));
        KeyBindingHelper.registerKeyBinding(configMenuKeyMapping);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configMenuKeyMapping.consumeClick()) {
                client.setScreen(new ConfigScreen());
            }
        });

    }

    protected KeyMapping createKeyMapping(KeybindData keybindData) {
        return new KeyMappingWrapper(keybindData.id(), keybindData.type(), keybindData.key(), modCategory).unwrap(KeyMapping.class);
    }

    private final static String modCategory = "key.category." + LibrGetter.MOD_ID + ".category";
}

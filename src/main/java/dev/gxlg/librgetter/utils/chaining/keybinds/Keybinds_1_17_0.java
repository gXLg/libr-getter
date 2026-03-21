package dev.gxlg.librgetter.utils.chaining.keybinds;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;

public class Keybinds_1_17_0 extends Keybinds.Base {
    private final KeybindData configMenuData = new KeybindData("librgetter.keys.open", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_K);

    @Override
    public void registerKeybinds() {
        KeyMapping configMenuKeyMapping = createKeyMapping(configMenuData);
        register(configMenuKeyMapping);
        ClientTickEvents$EndTickI endTick = client -> {
            while (configMenuKeyMapping.consumeClick()) {
                client.setScreen(new ConfigScreen());
            }
        };
        ClientTickEvents.END_CLIENT_TICK.register(endTick.unwrap(ClientTickEvents.EndTick.class));
    }

    protected void register(KeyMapping keyMapping) {
        KeyBindingHelper.registerKeyBinding(keyMapping);
    }

    protected KeyMapping createKeyMapping(KeybindData keybindData) {
        return new KeyMapping(keybindData.id(), keybindData.type(), keybindData.key(), modCategory);
    }

    private final static String modCategory = "key.category." + LibrGetter.MOD_ID + ".category";
}

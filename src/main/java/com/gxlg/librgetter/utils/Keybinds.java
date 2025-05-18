package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.gui.ConfigScreen;
import com.gxlg.librgetter.utils.reflection.Minecraft;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static void registerKeybinds() {
        KeyBinding kOpen = KeyBindingHelper.registerKeyBinding(new KeyBinding("librgetter.keys.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "librgetter.keys.category"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (kOpen.wasPressed()) {
                Minecraft.setScreen(client, new ConfigScreen());
            }
        });
    }
}

package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.gui.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static void registerKeybinds() {
        KeyBinding kOpen = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("librgetter.keys.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "librgetter.keys.category")
        );
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (kOpen.wasPressed()) {
                Reflection.invokeMethod(MinecraftClient.class, client, new Object[]{new ConfigScreen()}, new Class[]{Screen.class}, "method_1507", "setScreen", "openScreen");
            }
        });
    }
}

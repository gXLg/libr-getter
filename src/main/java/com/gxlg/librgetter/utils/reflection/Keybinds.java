package com.gxlg.librgetter.utils.reflection;

import com.gxlg.librgetter.gui.ConfigScreen;
import com.gxlg.librgetter.utils.MultiVersion;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static void registerKeybinds() {
        KeyBinding kOpen;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.CLIENT_WORLD)) {
            Class<?> catc = Reflection.clazz("net.minecraft.class_304$class_11900", "net.minecraft.client.option.KeyBinding$Category");
            Object cat = Reflection.invokeMethod(catc, null, new Object[]{Identifier.of("librgetter", "category")}, "method_74698", "create");
            kOpen = (KeyBinding) Reflection.construct(KeyBinding.class, new Object[]{"librgetter.keys.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, cat}, new Class[]{String.class, InputUtil.Type.class, int.class, catc});
        } else {
            kOpen = (KeyBinding) Reflection.construct(KeyBinding.class, new Object[]{"librgetter.keys.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "key.category.librgetter.category"}, new Class[]{String.class, InputUtil.Type.class, int.class, String.class});
        }
        KeyBindingHelper.registerKeyBinding(kOpen);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (kOpen.wasPressed()) {
                Minecraft.setScreen(client, new ConfigScreen());
            }
        });
    }
}

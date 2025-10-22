package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Reflection;
import dev.gxlg.librgetter.gui.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static void registerKeybinds() {
        KeyBinding kOpen;
        if (Reflection.version(">= 1.21.9")) {
            Identifier id = Identifier.of("librgetter", "category");
            Class<?> cat = (Class<?>) Reflection.wrap(".class_304$class_11900/.client.option.KeyBinding$Category");
            kOpen = (KeyBinding) Reflection.wrapn("KeyBinding @\"librgetter.keys.open\" @InputUtil.Type.KEYSYM int:GLFW.GLFW_KEY_K cat:[cat method_74698/create id]", InputUtil.class, GLFW.class, cat, id);
        } else {
            kOpen = (KeyBinding) Reflection.wrapn("KeyBinding @\"librgetter.keys.open\" @InputUtil.Type.KEYSYM int:GLFW.GLFW_KEY_K @\"key.category.librgetter.category\"", InputUtil.class, GLFW.class);
        }
        KeyBindingHelper.registerKeyBinding(kOpen);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (kOpen.wasPressed()) {
                Minecraft.setScreen(client, new ConfigScreen());
            }
        });
    }
}

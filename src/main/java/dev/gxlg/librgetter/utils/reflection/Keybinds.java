package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.multiversion.R;
import dev.gxlg.librgetter.multiversion.V;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static void registerKeybinds() {
        KeyBinding kOpen;
        if (!V.lower("1.21.9")) {
            Identifier id = (Identifier) R.clz(Identifier.class).mthd("method_43902/of", String.class, String.class).invk("librgetter", "category");

            R.RClass cat = R.clz("net.minecraft.class_304$class_11900/net.minecraft.client.option.KeyBinding$Category");
            kOpen = (KeyBinding) R.clz(KeyBinding.class).constr(String.class, InputUtil.Type.class, int.class, cat).newInst("librgetter.keys.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, cat.mthd("method_74698/create", Identifier.class).invk(id)).self();
        } else {
            kOpen = (KeyBinding) R.clz(KeyBinding.class).constr(String.class, InputUtil.Type.class, int.class, String.class).newInst("librgetter.keys.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "key.category.librgetter.category").self();
        }
        KeyBindingHelper.registerKeyBinding(kOpen);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (kOpen.wasPressed()) {
                Minecraft.setScreen(client, new ConfigScreen());
            }
        });
    }
}

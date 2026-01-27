package dev.gxlg.librgetter.utils.chaining.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickWrapperInterface;
import dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelperWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.KeyMappingWrapper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;

public class Keybinds_1_17_0 extends Keybinds {
    @Override
    public void registerKeybinds() {
        KeyMappingWrapper configMenuKeyMapping = createKeyMapping(new KeybindData("librgetter.keys.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K));
        KeyBindingHelperWrapper.registerKeyBinding(configMenuKeyMapping);

        ClientTickEvents.END_CLIENT_TICK.register((
                                                      (ClientTickEvents$EndTickWrapperInterface) client -> {
                                                          while (configMenuKeyMapping.consumeClick()) {
                                                              client.setScreen(new ConfigScreen());
                                                          }
                                                      }
                                                  ).wrapper().unwrap(ClientTickEvents.EndTick.class));
    }

    protected KeyMappingWrapper createKeyMapping(KeybindData keybindData) {
        return new KeyMappingWrapper(keybindData.id(), keybindData.type(), keybindData.key(), modCategory);
    }

    private final static String modCategory = "key.category." + LibrGetter.MOD_ID + ".category";
}

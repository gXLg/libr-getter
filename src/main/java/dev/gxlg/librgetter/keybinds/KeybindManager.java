package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.utils.chaining.keybinds.Keybinds;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickI;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.List;

public class KeybindManager {
    private final List<KeybindData> keybinds;

    private final String modId;

    public KeybindManager(String modId, ConfigManager configManager, String modVersion, SharedController sharedController) {
        this.keybinds = List.of(new ConfigMenuKeybind(modVersion, configManager), new ToggleWorkKeybind(sharedController), new SelectKeybind(sharedController));
        this.modId = modId;
    }

    public void register() {
        keybinds.forEach(keybind -> {
            KeyMapping mapping = Keybinds.createKeyMapping(keybind, modId);
            Keybinds.register(mapping);
            ClientTickEvents$EndTickI endTick = client -> {
                while (mapping.consumeClick()) {
                    try {
                        keybind.execute(client);
                    } catch (LibrGetterException e) {
                        Texts.sendMessage(e.getTranslatableErrorMessage());
                    }
                }
            };
            ClientTickEvents.END_CLIENT_TICK.register(endTick.unwrap(ClientTickEvents.EndTick.class));
        });
    }

    public static abstract class KeybindData {
        private final String id;

        private final InputConstants$Type type;

        private final int key;

        public KeybindData(String id, InputConstants$Type type, int key) {
            this.id = id;
            this.type = type;
            this.key = key;
        }

        public String getId() {
            return id;
        }

        public InputConstants$Type getType() {
            return type;
        }

        public int getKey() {
            return key;
        }

        public abstract void execute(Minecraft client) throws LibrGetterException;
    }
}

package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.utils.chaining.keybinds.Keybinds;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickI;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.List;

public class KeybindManager {
    private final List<Keybind> keybinds;

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

}

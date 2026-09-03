package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.utils.TickUtil;
import dev.gxlg.librgetter.utils.chaining.keybinds.Keybinds;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.versiont.gen.net.minecraft.client.KeyMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeybindManager {
    private final List<Keybind> keybinds;

    private final String modId;

    public KeybindManager(String modId, ConfigManager configManager, GoalListAccessor goalListAccessor, String modVersion, SharedController sharedController, TradehallAccessor tradehallAccessor) {
        this.keybinds = List.of(
            new ConfigMenuKeybind(modVersion, configManager),
            new ToggleWorkKeybind(sharedController),
            new SelectKeybind(sharedController),
            new GoalScreenKeybind(goalListAccessor),
            new TradehallScreenKeybind(tradehallAccessor)
        );
        this.modId = modId;
    }

    public void register() {
        Map<Keybind, KeyMapping> mappings = keybinds.stream().collect(Collectors.toMap(
            keybind -> keybind, keybind -> {
                KeyMapping mapping = Keybinds.createKeyMapping(keybind, modId);
                Keybinds.register(mapping);
                return mapping;
            }
        ));

        TickUtil.registerClientTicker(client -> {
            for (Map.Entry<Keybind, KeyMapping> entry : mappings.entrySet()) {
                Keybind keybind = entry.getKey();
                KeyMapping mapping = entry.getValue();
                while (mapping.consumeClick()) {
                    try {
                        keybind.execute(client);
                    } catch (LibrGetterException e) {
                        Texts.sendMessage(e.getTranslatableErrorMessage());
                    }
                }
            }
        });
    }
}

package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.gui.impl.config.ConfigMenu;
import dev.gxlg.librgetter.gui.impl.config.ConfigScreen;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;

public class ConfigMenuKeybind extends Keybind {
    private final ConfigMenu configMenu;

    public ConfigMenuKeybind(String modVersion, ConfigManager configManager) {
        super("librgetter.keys.open", InputConstants$Type.KEYBOARD(), InputConstants.KEY_K());
        this.configMenu = new ConfigMenu(modVersion, configManager);
    }

    @Override
    public void execute(Minecraft client) {
        Gui.setScreen(client, new ConfigScreen(configMenu));
    }
}

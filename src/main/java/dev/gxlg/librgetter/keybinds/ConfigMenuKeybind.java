package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.gui.ConfigMenu;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class ConfigMenuKeybind extends KeybindManager.KeybindData {
    private final ConfigMenu configMenu;

    public ConfigMenuKeybind(String modVersion, ConfigManager configManager) {
        super("librgetter.keys.open", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_K);
        this.configMenu = new ConfigMenu(modVersion, configManager);
    }

    @Override
    public void execute(Minecraft client) {
        client.setScreen(new ConfigScreen(configMenu));
    }
}

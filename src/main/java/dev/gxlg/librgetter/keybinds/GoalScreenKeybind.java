package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.gui.goals.GoalScreen;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class GoalScreenKeybind extends Keybind {
    public GoalScreenKeybind() {
        super("librgetter.keys.goals", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_COMMA);
    }

    @Override
    public void execute(Minecraft client) {
        client.setScreen(new GoalScreen());
    }
}

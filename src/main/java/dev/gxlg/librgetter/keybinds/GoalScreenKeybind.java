package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.gui.goals.GoalListScreen;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class GoalScreenKeybind extends Keybind {
    private final GoalListManager goalListManager;

    public GoalScreenKeybind(GoalListManager goalListManager) {
        super("librgetter.keys.goals", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_COMMA);
        this.goalListManager = goalListManager;
    }

    @Override
    public void execute(Minecraft client) {
        Screen lastScreen = client.getScreenField();
        GoalListScreen newScreen = new GoalListScreen(lastScreen, goalListManager);
        client.setScreen(newScreen);
    }
}

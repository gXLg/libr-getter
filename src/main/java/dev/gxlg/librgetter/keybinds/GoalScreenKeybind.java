package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.gui.impl.goals.list.GoalListScreen;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class GoalScreenKeybind extends Keybind {
    private final GoalListAccessor goalListAccessor;

    public GoalScreenKeybind(GoalListAccessor goalListAccessor) {
        super("librgetter.keys.goals", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_COMMA);
        this.goalListAccessor = goalListAccessor;
    }

    @Override
    public void execute(Minecraft client) {
        Screen lastScreen = Gui.getScreen(client);
        GoalListScreen newScreen = new GoalListScreen(lastScreen, goalListAccessor);
        Gui.setScreen(client, newScreen);
    }
}

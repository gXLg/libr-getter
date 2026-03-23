package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class ToggleWorkKeybind extends Keybind {
    private final SharedController sharedController;

    private BlockPos lastLecternPos;

    private Villager lastLibrarian;

    public ToggleWorkKeybind(SharedController sharedController) {
        super("librgetter.keys.toggle", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_J);
        this.sharedController = sharedController;

        lastLecternPos = sharedController.getStateView().getTaskContext().selectedLecternPos();
        lastLibrarian = sharedController.getStateView().getTaskContext().selectedVillager();
    }

    @Override
    public void execute(Minecraft client) throws LibrGetterException {
        BlockPos newLecternPos = sharedController.getStateView().getTaskContext().selectedLecternPos();
        Villager newLibrarian = sharedController.getStateView().getTaskContext().selectedVillager();
        try {
            work(client, newLecternPos, newLibrarian);
        } finally {
            lastLecternPos = newLecternPos;
            lastLibrarian = newLibrarian;
        }
    }

    private void work(Minecraft client, BlockPos newLecternPos, Villager newLibrarian) throws LibrGetterException {
        if (sharedController.getStateView().isWorking()) {
            sharedController.stopWorking();
            return;
        }

        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }

        if ((newLecternPos == null || newLibrarian == null) || (
            !newLecternPos.closerThan(player.blockPosition(), Task.MAX_INTERACTION_DISTANCE) || newLibrarian.distanceTo(player) > Task.MAX_INTERACTION_DISTANCE
        )) {
            sharedController.autostart();
            return;
        }

        if (Objects.equals(newLecternPos, lastLecternPos) && Objects.equals(newLibrarian, lastLibrarian)) {
            sharedController.continueWorking();
        } else {
            sharedController.startWorking();
        }
    }
}

package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.VillagerTooFarException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionHandWrapper;

public class RequestTradesTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx));
        }

        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        MultiPlayerGameModeWrapper game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }

        if (taskContext.selectedVillager().distanceTo(player) > 3.4f) {
            throw new VillagerTooFarException();
        }

        game.interact(player, taskContext.selectedVillager(), InteractionHandWrapper.MAIN_HAND());
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx));
    }
}

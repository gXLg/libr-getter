package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.core.Direction;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.state.BlockState;

public class BreakLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }

        BlockState targetBlock = world.getBlockState(taskContext.selectedLecternPos());
        if (targetBlock.isAir()) {
            // lectern is broken now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitVillagerLoseProfessionTask(), ctx.withIncreasedAttemptsCounter()));
        }

        if (LibrGetter.config.manual) {
            return;
        }

        MultiPlayerGameMode game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }

        game.continueDestroyBlock(taskContext.selectedLecternPos(), Direction.UP());
    }

    @Override
    public boolean allowsBreaking() {
        return true;
    }
}

package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper;

public class BreakLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        ClientLevelWrapper world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }

        BlockStateWrapper targetBlock = world.getBlockState(taskContext.selectedLecternPos());
        if (targetBlock.isAir()) {
            // lectern is broken now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitVillagerLoseProfessionTask(), ctx.withIncreasedAttemptsCounter()));
        }

        if (LibrGetter.config.manual) {
            return;
        }

        MultiPlayerGameModeWrapper game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }

        game.continueDestroyBlock(taskContext.selectedLecternPos(), DirectionWrapper.UP());
    }

    @Override
    public boolean allowsBreaking() {
        return true;
    }
}

package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class BreakLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
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

        MultiPlayerGameMode manager = client.gameMode;
        if (manager == null) {
            throw new InternalErrorException("manager");
        }
        manager.continueDestroyBlock(taskContext.selectedLecternPos(), Direction.UP);
    }

    @Override
    public boolean allowsBreaking() {
        return true;
    }
}

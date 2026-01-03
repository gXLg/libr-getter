package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Direction;

public class BreakLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) {
            throw new InternalTaskException("world", this);
        }

        BlockState targetBlock = world.getBlockState(taskContext.selectedLectern());
        if (targetBlock.isAir()) {
            // lectern is broken now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitVillagerLoseProfessionTask(), ctx.withIncreasedAttemptsCounter()));
        }

        if (LibrGetter.config.manual) {
            return;
        }

        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) {
            throw new InternalTaskException("manager", this);
        }
        manager.updateBlockBreakingProgress(taskContext.selectedLectern(), Direction.UP);
    }

    @Override
    public boolean allowsBreaking() {
        return true;
    }
}

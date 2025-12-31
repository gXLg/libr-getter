package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Direction;

public class BreakLecternTask extends Worker.Task {
    public BreakLecternTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) return internalError("world");

        BlockState targetBlock = world.getBlockState(taskContext.selectedLectern());
        if (targetBlock.isAir()) {
            // lectern is broken now
            return switchSameTick(new WaitVillagerLoseProfessionTask(taskContext.withIncreasedAttemptsCounter()));
        }

        if (LibrGetter.config.manual) return noSwitch();

        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) return internalError("manager");
        manager.updateBlockBreakingProgress(taskContext.selectedLectern(), Direction.UP);
        return noSwitch();
    }

    @Override
    public boolean allowsBreaking() {
        return true;
    }
}

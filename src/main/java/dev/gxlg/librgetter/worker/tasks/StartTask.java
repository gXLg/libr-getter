package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class StartTask extends Worker.Task {
    private final boolean resetCounter;

    public StartTask(Worker.TaskContext taskContext, boolean resetCounter) {
        super(taskContext);
        this.resetCounter = resetCounter;
    }

    @Override
    public Worker.TaskSwitch work() {
        if (taskContext.selectedLectern() == null) return error("librgetter.no_lectern");
        if (taskContext.selectedVillager() == null) return error("librgetter.no_librarian");
        if (LibrGetter.config.goals.isEmpty()) return error("librgetter.goals");

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");

        if (LibrGetter.config.safeChecker) {
            ClientWorld world = client.world;
            if (world == null) return internalError("world");
            // If the villager is sitting, assume it cannot move
            if (!taskContext.selectedVillager().hasVehicle()) {
                List<BlockPos> path = PathFinding.findPath(taskContext.selectedVillager().getBlockPos(), taskContext.selectedLectern(), world, 2);
                if (path != null) return error("librgetter.unsafe");
            }
        }

        Worker.TaskContext newContext = taskContext;
        if (!LibrGetter.config.autoTool) newContext = newContext.withDefaultItem(player.getMainHandStack());

        Texts.getImpl().sendTranslatableSuccess("librgetter.start");
        if (resetCounter) newContext = newContext.withResetAttemptsCounter();

        return switchNextTick(new SelectAndPlaceLecternTask(newContext));
    }
}

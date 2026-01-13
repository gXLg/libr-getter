package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.TaskException;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;

import java.util.List;

public class StartTask extends TaskManager.Task {
    private final boolean resetCounter;

    public StartTask(boolean resetCounter) {
        this.resetCounter = resetCounter;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (taskContext.selectedLecternPos() == null) {
            throw new TaskException("librgetter.no_lectern");
        }
        if (taskContext.selectedVillager() == null) {
            throw new TaskException("librgetter.no_librarian");
        }
        if (LibrGetter.config.goals.isEmpty()) {
            throw new TaskException("librgetter.goals");
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalTaskException("player", this);
        }

        if (LibrGetter.config.safeChecker) {
            ClientLevel world = client.level;
            if (world == null) {
                throw new InternalTaskException("world", this);
            }
            // If the villager is sitting, assume it cannot move
            if (!taskContext.selectedVillager().isPassenger()) {
                List<BlockPos> path = PathFinding.findPath(taskContext.selectedVillager().blockPosition(), taskContext.selectedLecternPos(), world, 2);
                if (path != null) {
                    throw new TaskException("librgetter.unsafe");
                }
            }
        }

        Texts.getImpl().sendTranslatableSuccess("librgetter.start");

        throw new StopTaskSignal(ctx -> {
            if (!LibrGetter.config.autoTool) {
                ctx = ctx.withDefaultItem(player.getMainHandItem());
            }
            if (resetCounter) {
                ctx = ctx.withResetAttemptsCounter();
            }

            return TaskManager.TaskSwitch.nextTick(
                new RotationTask(player, EntityAnchorArgument.Anchor.EYES.apply(ctx.selectedVillager()), new WaitVillagerAcceptProfessionTask()),
                ctx
            );
        });
    }
}

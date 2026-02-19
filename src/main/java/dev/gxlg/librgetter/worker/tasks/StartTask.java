package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.EmptyGoalsListException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.NoLecternSetException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.NoLibrarianSetException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.UnsafeSetupException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStartedMessage;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.List;

public class StartTask extends TaskManager.Task {
    private final boolean resetCounter;

    public StartTask(boolean resetCounter) {
        this.resetCounter = resetCounter;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (taskContext.selectedLecternPos() == null) {
            throw new NoLecternSetException();
        }
        if (taskContext.selectedVillager() == null) {
            throw new NoLibrarianSetException();
        }
        if (LibrGetter.config.goals.isEmpty()) {
            throw new EmptyGoalsListException();
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }

        if (LibrGetter.config.safeChecker) {
            ClientLevel world = client.getLevelField();
            if (world == null) {
                throw new InternalErrorException("world");
            }
            // If the villager is sitting, assume it cannot move
            if (!taskContext.selectedVillager().isPassenger()) {
                List<BlockPos> path = PathFinding.findPath(taskContext.selectedVillager().blockPosition(), taskContext.selectedLecternPos(), world, 2);
                if (path != null) {
                    throw new UnsafeSetupException();
                }
            }
        }

        Texts.getImpl().sendTranslatable(new ProcessStartedMessage());

        throw new StopTaskSignal(ctx -> {
            if (!LibrGetter.config.autoTool) {
                ctx = ctx.withDefaultItem(player.getMainHandItem());
            }
            if (resetCounter) {
                ctx = ctx.withResetAttemptsCounter();
            }

            return TaskManager.TaskSwitch.nextTick(new RotationTask(player, EntityAnchorArgument$Anchor.EYES().apply(ctx.selectedVillager()), new WaitVillagerAcceptProfessionTask()), ctx);
        });
    }
}

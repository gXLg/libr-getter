package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.EmptyGoalsListException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.NoLecternSetException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.NoLibrarianSetException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.UnsafeSetupException;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStartedMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.List;

public class StartTask extends Task {
    private final boolean resetCounter;

    public StartTask(boolean resetCounter) {
        this.resetCounter = resetCounter;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (taskContext.selectedLecternPos() == null && !compatibilityManager.isUsingTradeCycling()) {
            throw new NoLecternSetException();
        }
        if (taskContext.selectedVillager() == null) {
            throw new NoLibrarianSetException();
        }
        if (configManager.getData().getGoals().isEmpty()) {
            throw new EmptyGoalsListException();
        }

        MinecraftData minecraftData = new MinecraftData();
        if (configManager.getBoolean(Config.SAFE_CHECKER)) {
            // If the villager is sitting, assume it cannot move
            if (!taskContext.selectedVillager().isPassenger()) {
                List<BlockPos> path = PathFinding.findPath(taskContext.selectedVillager().blockPosition(), taskContext.selectedLecternPos(), minecraftData.clientLevel, 2);
                if (path != null) {
                    throw new UnsafeSetupException();
                }
            }
        }

        controller.scheduleContextUpdate(ctx -> {
            if (!configManager.getBoolean(Config.AUTO_TOOL)) {
                ctx.setDefaultItem(minecraftData.localPlayer.getMainHandItem());
            }
            if (resetCounter) {
                ctx.resetAttemptsCounter();
            }
            ctx.setTradeOfferData(null).setMinecraftData(minecraftData);
        });

        Task rotationTask = new RotationTask(minecraftData.localPlayer, EntityAnchorArgument$Anchor.EYES().apply(taskContext.selectedVillager()), new WaitVillagerAcceptProfessionTask());
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> {
            Texts.sendMessage(new ProcessStartedMessage());
            return rotationTask;
        }));
    }
}

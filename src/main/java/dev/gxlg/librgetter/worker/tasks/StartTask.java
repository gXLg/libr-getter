package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.exceptions.commands.VillagerNotLibrarianException;
import dev.gxlg.librgetter.utils.exceptions.tasks.EmptyGoalsListException;
import dev.gxlg.librgetter.utils.exceptions.tasks.NoLecternSetException;
import dev.gxlg.librgetter.utils.exceptions.tasks.NoLibrarianSetException;
import dev.gxlg.librgetter.utils.exceptions.tasks.UnsafeSetupException;
import dev.gxlg.librgetter.utils.exceptions.tasks.VillagerNotExistException;
import dev.gxlg.librgetter.utils.messages.translatable.feedback.ProcessStartedMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.List;

public class StartTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (taskContext.selectedLecternPos() == null && !compatibilityManager.isUsingTradeCycling()) {
            throw new NoLecternSetException();
        }

        if (taskContext.selectedVillager() == null) {
            throw new NoLibrarianSetException();
        }
        if (!Villagers.isVillagerLibrarian(taskContext.selectedVillager())) {
            throw new VillagerNotLibrarianException();
        }
        if (!taskContext.selectedVillager().isAlive()) {
            throw new VillagerNotExistException();
        }
        if (goalListAccessor.createAccessForCurrentManager().getGoals().isEmpty()) {
            throw new EmptyGoalsListException();
        }

        MinecraftData minecraftData = new MinecraftData();
        if (configManager.getBoolean(Config.SAFE_CHECKER) && configManager.getConfigurable(Config.SAFE_CHECKER).hasEffect()) {
            // If the villager is a passenger (in boat, minecart), assume it cannot move
            if (!taskContext.selectedVillager().isPassenger()) {
                List<BlockPos> path = PathFinding.findPathInsideBlock(taskContext.selectedVillager().blockPosition(), taskContext.selectedLecternPos(), minecraftData.clientLevel, 2);
                if (path != null) {
                    throw new UnsafeSetupException();
                }
            }
        }

        controller.scheduleContextUpdate(ctx -> {
            if (!configManager.getBoolean(Config.AUTO_TOOL)) {
                ctx.setDefaultItem(minecraftData.localPlayer.getMainHandItem());
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

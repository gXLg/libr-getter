package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.core.Direction;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.state.BlockState;

public class BreakLecternTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListManager goalListManager, CompatibilityManager compatibilityManager) {
        MinecraftData minecraftData = taskContext.minecraftData();

        BlockState targetBlock = minecraftData.clientLevel.getBlockState(taskContext.selectedLecternPos());
        if (targetBlock.isAir()) {
            // lectern is broken now
            controller.scheduleContextUpdate(TaskContextBuilder::increaseAttemptsCounter);
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(WaitVillagerLoseProfessionTask::new));
            return;
        }

        if (configManager.getBoolean(Config.MANUAL)) {
            return;
        }

        minecraftData.gameMode.continueDestroyBlock(taskContext.selectedLecternPos(), Direction.UP());
    }

    @Override
    protected boolean allowsBreakingLecterns() {
        return true;
    }
}

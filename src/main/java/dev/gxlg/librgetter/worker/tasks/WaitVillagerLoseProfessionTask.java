package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class WaitVillagerLoseProfessionTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListManager goalListManager, CompatibilityManager compatibilityManager) {
        if (!configManager.getBoolean(Config.WAIT_LOSE) || Villagers.isVillagerUnemployed(taskContext.selectedVillager())) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(SelectAndPlaceLecternTask::new));
        }
    }
}

package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallManager;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.tasks.tradehall.SearchNextWorkstationTask;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class FinishTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListManager goalListManager, TradehallManager tradehallManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (!configManager.getBoolean(Config.TRADEHALL_MODE) || !configManager.getConfigurable(Config.TRADEHALL_MODE).hasEffect()) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(StandbyTask::new));
            return;
        }
        if (goalListManager.getGoals().isEmpty()) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(StandbyTask::new));
            return;
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(SearchNextWorkstationTask::new));
    }
}

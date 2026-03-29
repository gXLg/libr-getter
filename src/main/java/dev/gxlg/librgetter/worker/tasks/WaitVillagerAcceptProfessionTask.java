package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.PickedAnotherProfessionException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class WaitVillagerAcceptProfessionTask extends Task {
    private int timeout = 0;

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (!Villagers.isVillagerUnemployed(taskContext.selectedVillager())) {
            if (!Villagers.isVillagerLibrarian(taskContext.selectedVillager())) {
                throw new PickedAnotherProfessionException();
            }
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(RequestTradesTask::new));
            return;
        }

        // the timeout can only happen on a second and later cycles (because we check the profession in StartTask),
        // and since TradeCycling integration would not revisit this task after the first cycle,
        // we can just switch to SelectAxeTask after a timeout without checking for TradeCycling again
        if (configManager.getInteger(Config.TIMEOUT) != 0) {
            timeout++;
            // break and place the lectern again after a timeout
            if (timeout >= configManager.getInteger(Config.TIMEOUT) * 20) {
                controller.scheduleTaskSwitch(TaskSwitch.sameTick(SelectAxeTask::new));
            }
        }
    }
}

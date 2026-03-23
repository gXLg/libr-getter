package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.chaining.compatibility.Compatibility;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStoppedMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class TradeCyclingClickTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) {
        MinecraftData minecraftData = taskContext.minecraftData();
        if (minecraftData.client.getScreenField() == null) {
            // when using TradeCycling, merchant screen stays open during the process
            // if user closes the screen, stop the process
            controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> {
                Texts.sendMessage(new ProcessStoppedMessage());
                return new StandbyTask();
            }));
            return;
        }
        Compatibility.sendCycleTradesPacket();
        controller.scheduleContextUpdate(TaskContextBuilder::increaseAttemptsCounter);
        controller.scheduleTaskSwitch(TaskSwitch.sameTick(WaitTradesTask::new));
    }
}

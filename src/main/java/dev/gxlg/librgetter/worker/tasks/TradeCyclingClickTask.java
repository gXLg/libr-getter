package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStoppedMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class TradeCyclingClickTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) {
        MinecraftData minecraftData = taskContext.minecraftData();
        if (minecraftData.client.getScreenField() == null) {
            // when using TradeCycling, merchant screen stays open during the process
            // if user closes the screen, stop the process
            controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> {
                Texts.sendTranslatable(new ProcessStoppedMessage());
                return new StandbyTask();
            }));
            return;
        }
        Support.sendCycleTradesPacket();
        controller.scheduleContextUpdate(TaskContextBuilder::increaseAttemptsCounter);
        controller.scheduleTaskSwitch(TaskSwitch.sameTick(WaitTradesTask::new));
    }
}

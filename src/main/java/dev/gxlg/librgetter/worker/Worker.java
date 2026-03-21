package dev.gxlg.librgetter.worker;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.SchedulingHandler;
import dev.gxlg.librgetter.worker.scheduling.base.TaskContextUpdateScheduler;
import dev.gxlg.librgetter.worker.scheduling.base.TaskSwitchScheduler;
import dev.gxlg.librgetter.worker.scheduling.controllers.SystemSchedulerController;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateController;
import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.librgetter.worker.state.base.TaskContextState;
import dev.gxlg.librgetter.worker.state.base.TaskState;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$DisconnectI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$JoinI;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class Worker {
    private final StateView stateView;

    private final SystemSchedulerController systemSchedulerController;

    private final UserSchedulerController userSchedulerController;

    private final TaskSchedulerController taskSchedulerController;

    private final SchedulingHandler schedulingHandler;

    private final StateController stateController;


    public Worker() {
        TaskState taskState = new TaskState();
        TaskContextState taskContextState = new TaskContextState();
        TaskContextUpdateScheduler taskContextUpdateScheduler = new TaskContextUpdateScheduler();
        TaskSwitchScheduler taskSwitchScheduler = new TaskSwitchScheduler(new StandbyTask());

        stateController = new StateController(taskState, taskContextState);
        stateView = new StateView(taskState, taskContextState);
        systemSchedulerController = new SystemSchedulerController(taskContextUpdateScheduler, taskSwitchScheduler);
        userSchedulerController = new UserSchedulerController(taskContextUpdateScheduler, taskSwitchScheduler);
        taskSchedulerController = new TaskSchedulerController(taskContextUpdateScheduler, taskSwitchScheduler);
        schedulingHandler = new SchedulingHandler(taskContextUpdateScheduler, taskSwitchScheduler);

        ClientPlayConnectionEvents.JOIN.register(((ClientPlayConnectionEvents$JoinI) (h, s, c) -> reset()).unwrap(ClientPlayConnectionEvents.Join.class));
        ClientPlayConnectionEvents.DISCONNECT.register(((ClientPlayConnectionEvents$DisconnectI) (h, c) -> reset()).unwrap(ClientPlayConnectionEvents.Disconnect.class));
    }

    public void work() {
        do {
            Task currentTask = schedulingHandler.takeScheduledTask();
            TaskContext currentContext = schedulingHandler.buildTaskContext();

            stateController.setTask(currentTask);
            stateController.setTaskContext(currentContext);

            try {
                currentTask.work(currentContext, taskSchedulerController);
            } catch (LibrGetterException exception) {
                Texts.sendTranslatable(exception.getTranslatableErrorMessage());
                systemSchedulerController.scheduleTaskSwitch(TaskSwitch.nextTick(StandbyTask::new));
            }

            schedulingHandler.handleScheduling();

        } while (schedulingHandler.isTaskScheduledForSameTick());
    }

    public StateView getStateView() {
        return stateView;
    }

    public UserSchedulerController getUserSchedulerController() {
        return userSchedulerController;
    }

    public SystemSchedulerController getSystemSchedulerController() {
        return systemSchedulerController;
    }

    public void reset() {
        systemSchedulerController.scheduleContextUpdate(TaskContextBuilder::reset);
        systemSchedulerController.scheduleTaskSwitch(TaskSwitch.nextTick(StandbyTask::new));
    }
}

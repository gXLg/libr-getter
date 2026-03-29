package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.VillagerTooFarException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class RequestTradesTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        if (configManager.getBoolean(Config.MANUAL)) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(WaitTradesTask::new));
            return;
        }

        MinecraftData minecraftData = taskContext.minecraftData();
        if (taskContext.selectedVillager().distanceTo(minecraftData.localPlayer) > MAX_INTERACTION_DISTANCE) {
            throw new VillagerTooFarException();
        }

        Players.interactEntity(minecraftData.gameMode, minecraftData.localPlayer, taskContext.selectedVillager(), true);

        controller.scheduleTaskSwitch(TaskSwitch.sameTick(WaitTradesTask::new));
    }

    @Override
    protected boolean allowsSettingTradeOffers() {
        return true;
    }
}

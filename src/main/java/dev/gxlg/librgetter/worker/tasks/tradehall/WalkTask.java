package dev.gxlg.librgetter.worker.tasks.tradehall;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallManager;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class WalkTask extends Task {
    private final List<BlockPos> path;

    public WalkTask(List<BlockPos> path) {
        this.path = new ArrayList<>(path);
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListManager goalListManager, TradehallManager tradehallManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
    }
}

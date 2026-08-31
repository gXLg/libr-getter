package dev.gxlg.librgetter.worker.tasks.tradehall;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.WorkstationList;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;

import java.util.List;

public class SearchNextWorkstationTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor, CompatibilityManager compatibilityManager) throws LibrGetterException {
        LocalPlayer player = taskContext.minecraftData().localPlayer;
        ClientLevel world = taskContext.minecraftData().clientLevel;
        WorkstationList blacklist = tradehallAccessor.createAccessForCurrentManager().getWorkstations();
        PathFinding.Jobsite workstation;
        List<BlockPos> path;
        while (true) {
            workstation = PathFinding.findJobsite(world, player.blockPosition(), p -> !blacklist.workstationExists(p));
            path = PathFinding.findPathToBlock(player.blockPosition(), workstation.lectern(), world, 2);
            if (path == null) {
                blacklist.addDummyWorkstation(workstation.lectern());
                continue;
            }
            break;
        }
        BlockPos lecternPos = workstation.lectern();
        Villager villager = workstation.librarian();
        List<BlockPos> humanizedPath = PathFinding.humanize(path, world, 2);

        controller.scheduleContextUpdate(ctx -> ctx.setLecternPos(lecternPos).setVillager(villager).resetAttemptsCounter());
        controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> new WalkTask(humanizedPath)));
    }
}

package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopCyclingSignal;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;

public class TradeCyclingClickTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        Minecraft client = Minecraft.getInstance();
        Screen currentScreen = client.getScreenField();
        if (currentScreen == null) {
            throw new StopCyclingSignal();
        }
        Support.getImpl().sendCycleTradesPacket();
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx.withIncreasedAttemptsCounter()));
    }
}

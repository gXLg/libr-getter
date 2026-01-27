package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopCyclingSignal;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper;

public class TradeCyclingClickTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        ScreenWrapper currentScreen = client.getScreenField();
        if (currentScreen == null) {
            throw new StopCyclingSignal();
        }
        Support.getImpl().sendCycleTradesPacket();
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx.withIncreasedAttemptsCounter()));
    }
}

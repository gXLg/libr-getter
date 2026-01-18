package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.signals.StopCyclingSignal;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class TradeCyclingClickTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        Minecraft client = Minecraft.getInstance();
        Screen currentScreen = client.screen;
        if (currentScreen == null) {
            throw new StopCyclingSignal();
        }
        Support.sendCycleTradesPacket();
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx.withIncreasedAttemptsCounter()));
    }
}

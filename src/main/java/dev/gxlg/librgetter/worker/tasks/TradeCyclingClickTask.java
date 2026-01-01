package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopCyclingSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class TradeCyclingClickTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        MinecraftClient client = MinecraftClient.getInstance();
        Screen s = client.currentScreen;
        if (s == null) throw new StopCyclingSignal();
        Support.sendCycleTradesPacket();
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx.withIncreasedAttemptsCounter()));
    }
}

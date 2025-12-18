package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class TradeCyclingClickTask extends Worker.Task {
    public TradeCyclingClickTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        MinecraftClient client = MinecraftClient.getInstance();
        Screen s = client.currentScreen;
        if (s == null) return finish();
        Support.sendCycleTradesPacket();
        return switchNextTick(new GetTradesTask(taskContext.withIncreasedAttemptsCounter()));
    }
}

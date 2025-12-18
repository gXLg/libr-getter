package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;

public class RequestTradesTask extends Worker.Task {
    public RequestTradesTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (LibrGetter.config.manual) return switchSameTick(new WaitTradesTask(taskContext));

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");
        ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) return internalError("handler");
        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) return internalError("manager");

        if (taskContext.selectedVillager().distanceTo(player) > 3.4f) {
            return error("librgetter.far");
        }

        manager.interactEntity(player, taskContext.selectedVillager(), Hand.MAIN_HAND);
        return switchSameTick(new WaitTradesTask(taskContext));
    }
}

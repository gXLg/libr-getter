package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.TaskException;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;

public class RequestTradesTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (LibrGetter.config.manual) throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx));

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) throw new InternalTaskException("player", this);
        ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) throw new InternalTaskException("handler", this);
        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) throw new InternalTaskException("manager", this);

        if (taskContext.selectedVillager().distanceTo(player) > 3.4f) throw new TaskException("librgetter.far");

        manager.interactEntity(player, taskContext.selectedVillager(), Hand.MAIN_HAND);
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx));
    }
}

package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.TaskException;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;

public class RequestTradesTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx));
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalTaskException("player", this);
        }
        ClientPacketListener handler = client.getConnection();
        if (handler == null) {
            throw new InternalTaskException("handler", this);
        }
        MultiPlayerGameMode manager = client.gameMode;
        if (manager == null) {
            throw new InternalTaskException("manager", this);
        }

        if (taskContext.selectedVillager().distanceTo(player) > 3.4f) {
            throw new TaskException("librgetter.far");
        }

        manager.interact(player, taskContext.selectedVillager(), InteractionHand.MAIN_HAND);
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new WaitTradesTask(), ctx));
    }
}

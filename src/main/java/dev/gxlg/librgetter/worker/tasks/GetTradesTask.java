package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Nullable;

public class GetTradesTask extends Worker.Task {
    @Nullable
    private TradeOfferList offers = null;
    private boolean canRefresh = true;

    public GetTradesTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (!canRefresh) return error("librgetter.update");
        if (offers != null) return switchSameTick(new ParseAndMatchTradesTask(taskContext, offers));

        if (LibrGetter.config.manual) return noSwitch();

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");
        ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) return internalError("handler");
        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) return internalError("manager");

        if (!taskContext.selectedLectern().isWithinDistance(player.getBlockPos(), 3.4f) || taskContext.selectedVillager().distanceTo(player) > 3.4f) {
            return error("librgetter.far");
        }

        manager.interactEntity(player, taskContext.selectedVillager(), Hand.MAIN_HAND);
        return noSwitch();
    }

    @Override
    public boolean allowsBreaking() {
        return false;
    }

    public void setOffers(@Nullable TradeOfferList offers) {
        this.offers = offers;
    }

    public void setNoRefresh() { canRefresh = false; }
}

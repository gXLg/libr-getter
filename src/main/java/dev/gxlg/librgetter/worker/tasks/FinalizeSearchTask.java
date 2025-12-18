package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class FinalizeSearchTask extends Worker.Task {
    private final TradeOfferList offers;

    public FinalizeSearchTask(Worker.TaskContext taskContext, TradeOfferList offers) {
        super(taskContext);
        this.offers = offers;
    }

    @Override
    public Worker.TaskSwitch work() {
        if (LibrGetter.config.manual) {
            return switchSameTick(new ManualModeWaitTask(taskContext));
        }

        if (!LibrGetter.config.lock) return finish();

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");

        if (!Support.useTradeCycling()) {
            // TradeCycling process keeps the screen open, else we have to open it again
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) return internalError("manager");
            manager.interactEntity(player, taskContext.selectedVillager(), Hand.MAIN_HAND);
        }

        int buy = canBuy(player, offers.get(0)) ? 0 : (
                canBuy(player, offers.get(1)) ? 1 : -1
        );
        if (buy == -1) return error("librgetter.lock");

        return switchNextTick(new LockTradesTask(taskContext, buy));
    }

    private static boolean canBuy(ClientPlayerEntity player, TradeOffer offer) {
        ItemStack first = Minecraft.getFirstBuyItem(offer);
        ItemStack second = Minecraft.getSecondBuyItem(offer);
        int firstCount = player.getInventory().count(first.getItem());
        int secondCount = second.isEmpty() ? 0 : player.getInventory().count(second.getItem());
        return first.getCount() <= firstCount && second.getCount() <= secondCount;
    }
}

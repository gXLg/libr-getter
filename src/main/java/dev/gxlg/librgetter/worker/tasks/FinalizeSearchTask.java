package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.*;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class FinalizeSearchTask extends TaskManager.Task {
    private final TradeOfferList offers;

    public FinalizeSearchTask(TradeOfferList offers) {
        this.offers = offers;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (LibrGetter.config.manual) throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new ManualModeWaitTask(), ctx));

        if (!LibrGetter.config.lock) throw new FinishSignal();

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) throw new InternalTaskException("player", this);

        if (!Support.isUsingTradeCycling()) {
            // TradeCycling process keeps the screen open, else we have to open it again
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) throw new InternalTaskException("manager", this);

            manager.interactEntity(player, taskContext.selectedVillager(), Hand.MAIN_HAND);
        }

        int buy = canBuy(player, offers.get(0)) ? 0 : (
                canBuy(player, offers.get(1)) ? 1 : -1
        );
        if (buy == -1) throw new TaskException("librgetter.lock");

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.nextTick(new LockTradesTask(buy), ctx));
    }

    private static boolean canBuy(ClientPlayerEntity player, TradeOffer offer) {
        ItemStack first = Minecraft.getFirstBuyItem(offer);
        ItemStack second = Minecraft.getSecondBuyItem(offer);
        int firstCount = player.getInventory().count(first.getItem());
        int secondCount = second.isEmpty() ? 0 : player.getInventory().count(second.getItem());
        return first.getCount() <= firstCount && second.getCount() <= secondCount;
    }
}

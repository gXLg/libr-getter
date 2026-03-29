package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.ClientNetwork;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.worker.scheduling.controllers.SystemSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.MenuType;

import java.util.Optional;

public class ClientPacketListenerMixinImpl {
    private final StateView stateView;

    private final SystemSchedulerController systemSchedulerController;

    private final CompatibilityManager compatibilityManager;

    public ClientPacketListenerMixinImpl(StateView stateView, SystemSchedulerController systemSchedulerController, CompatibilityManager compatibilityManager) {
        this.stateView = stateView;
        this.systemSchedulerController = systemSchedulerController;
        this.compatibilityManager = compatibilityManager;
    }

    public void handleMerchantOffers(ClientboundMerchantOffersPacket packet) {
        if (!stateView.getPermissionManager().allowsSettingTradeOffers()) {
            return;
        }
        TradeOfferData tradeOfferData = packet.getVillagerXp() > 0 ? TradeOfferData.noRefresh() : TradeOfferData.offers(packet.getOffers());
        systemSchedulerController.scheduleContextUpdate(ctx -> ctx.setTradeOfferData(tradeOfferData));
    }

    public Optional<Object> handleOpenScreen(ClientboundOpenScreenPacket packet) {
        if (!packet.getType().equals(MenuType.MERCHANT())) {
            return Optional.empty();
        }
        if (!stateView.isWorking() || compatibilityManager.isUsingTradeCycling()) {
            return Optional.empty();
        }
        if (stateView.getPermissionManager().allowsOpeningScreen()) {
            return Optional.empty();
        }

        Minecraft client = Minecraft.getInstance();
        try {
            ClientNetwork clientNetwork = new ClientNetwork(client);
            clientNetwork.send(new ServerboundContainerClosePacket(packet.getContainerId()));
        } catch (InternalErrorException ignored) {
        }

        return Optional.of(new Object());
    }
}

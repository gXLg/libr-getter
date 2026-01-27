package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game.ClientboundMerchantOffersPacketWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game.ClientboundOpenScreenPacketWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.protocol.game.ServerboundContainerClosePacketWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.inventory.MenuTypeWrapper;

import java.util.Optional;

public class ClientPacketListenerMixinImpl {
    public static void handleMerchantOffers(ClientboundMerchantOffersPacketWrapper packet) {
        if (!TaskManager.isWorking()) {
            return;
        }
        if (packet.canRestock()) {
            TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.offers(packet.getOffers())));
        } else {
            TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.noRefresh()));
        }
    }

    public static Optional<Object> handleOpenScreen(ClientboundOpenScreenPacketWrapper packet) {
        if (!packet.getType().equals(MenuTypeWrapper.MERCHANT()) || !TaskManager.isWorking() || Support.getImpl().isUsingTradeCycling()) {
            return Optional.empty();
        }
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        ClientPacketListenerWrapper clientNetwork = client.getConnection();
        if (clientNetwork != null) {
            clientNetwork.send(new ServerboundContainerClosePacketWrapper(packet.getContainerId()));
        }
        return Optional.of(new Object());
    }
}

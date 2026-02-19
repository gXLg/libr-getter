package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.MenuType;

import java.util.Optional;

public class ClientPacketListenerMixinImpl {
    public static void handleMerchantOffers(ClientboundMerchantOffersPacket packet) {
        if (!TaskManager.isWorking()) {
            return;
        }
        if (packet.canRestock()) {
            TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.offers(packet.getOffers())));
        } else {
            TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.noRefresh()));
        }
    }

    public static Optional<Object> handleOpenScreen(ClientboundOpenScreenPacket packet) {
        if (!packet.getType().equals(MenuType.MERCHANT()) || !TaskManager.isWorking() || Support.getImpl().isUsingTradeCycling()) {
            return Optional.empty();
        }
        Minecraft client = Minecraft.getInstance();
        ClientPacketListener clientNetwork = client.getConnection();
        if (clientNetwork != null) {
            clientNetwork.send(new ServerboundContainerClosePacket(packet.getContainerId()));
        }
        return Optional.of(new Object());
    }
}

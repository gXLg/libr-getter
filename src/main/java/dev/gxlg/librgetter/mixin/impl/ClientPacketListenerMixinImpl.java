package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.MenuType;

import java.util.Optional;

public class ClientPacketListenerMixinImpl {
    public static void handleMerchantOffers(ClientboundMerchantOffersPacket packet) {
        if (!LibrGetter.worker.getStateView().getPermissionManager().allowsSettingTradeOffers()) {
            return;
        }

        TradeOfferData tradeOfferData = packet.getVillagerXp() > 0 ? TradeOfferData.noRefresh() : TradeOfferData.offers(packet.getOffers());
        LibrGetter.worker.getSystemSchedulerController().scheduleContextUpdate(ctx -> ctx.setTradeOfferData(tradeOfferData));
    }

    public static Optional<Object> handleOpenScreen(ClientboundOpenScreenPacket packet) {
        if (!packet.getType().equals(MenuType.MERCHANT())) {
            return Optional.empty();
        }
        if (!LibrGetter.worker.getStateView().isWorking() || Support.isUsingTradeCycling()) {
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

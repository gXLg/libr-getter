package dev.gxlg.librgetter.mixin;

import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(at = @At("HEAD"), method = "handleMerchantOffers", order = 900)
    public void onSetTradeOffers(ClientboundMerchantOffersPacket packet, CallbackInfo callback) {
        if (TaskManager.isWorking()) {
            if (packet.canRestock()) {
                TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.offers(packet.getOffers())));
            } else {
                TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.noRefresh()));
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "handleOpenScreen", cancellable = true, order = 900)
    public void onOpenScreen(ClientboundOpenScreenPacket packet, CallbackInfo callback) {
        if (packet.getType() == MenuType.MERCHANT && TaskManager.isWorking()) {
            if (Support.isUsingTradeCycling()) {
                return;
            }

            callback.cancel();
            Minecraft client = Minecraft.getInstance();
            ClientPacketListener handler = client.getConnection();
            if (handler == null) {
                return;
            }
            ServerboundContainerClosePacket packetClose = new ServerboundContainerClosePacket(packet.getContainerId());
            Helper.getImpl().getConnection(handler).send(packetClose);
        }
    }
}
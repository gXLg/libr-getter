package dev.gxlg.librgetter.mixin;

import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class Trader {
    @Inject(at = @At("HEAD"), method = "onSetTradeOffers")
    public void onSetTradeOffers(SetTradeOffersS2CPacket packet, CallbackInfo callback) {
        if (TaskManager.isWorking()) {
            if (!packet.isRefreshable()) TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.noRefresh()));
            else TaskManager.updateContext(ctx -> ctx.withTradeOfferData(TradeOfferData.offers(packet.getOffers())));
        }
    }

    @Inject(at = @At("HEAD"), method = "onOpenScreen", cancellable = true)
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo callback) {
        if (packet.getScreenHandlerType() == ScreenHandlerType.MERCHANT && TaskManager.isWorking()) {
            if (Support.useTradeCycling()) return;

            callback.cancel();
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) return;
            CloseHandledScreenC2SPacket packetClose = new CloseHandledScreenC2SPacket(packet.getSyncId());
            Minecraft.getConnection(handler).send(packetClose);
        }
    }
}
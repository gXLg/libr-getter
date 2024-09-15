package com.gxlg.librgetter.mixin;

import com.gxlg.librgetter.Worker;
import com.gxlg.librgetter.utils.Minecraft;
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

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(ClientPlayNetworkHandler.class)
public class Trader {
    @Inject(at = @At("HEAD"), method = "onSetTradeOffers")
    public void onSetTradeOffers(SetTradeOffersS2CPacket packet, CallbackInfo callback) {
        if (Worker.getState() == Worker.State.GET || Worker.getState() == Worker.State.LOCK) {
            if (packet.getExperience() > 0) {
                Worker.noRefresh();
                return;
            }
            Worker.setTrades(packet.getOffers());
        }
    }

    @Inject(at = @At("HEAD"), method = "onOpenScreen", cancellable = true)
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo callback) {
        if (Worker.getState() == Worker.State.GET && packet.getScreenHandlerType() == ScreenHandlerType.MERCHANT) {
            callback.cancel();
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) return;
            CloseHandledScreenC2SPacket packetClose = new CloseHandledScreenC2SPacket(packet.getSyncId());
            Minecraft.getConnection(handler).send(packetClose);
        }
    }
}
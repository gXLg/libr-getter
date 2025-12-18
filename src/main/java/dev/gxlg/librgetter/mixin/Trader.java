package dev.gxlg.librgetter.mixin;

import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.worker.Worker;
import dev.gxlg.librgetter.worker.tasks.WaitTradesTask;
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
        if (Worker.getCurrentTask() instanceof WaitTradesTask waitTradesTask) {
            if (packet.getExperience() > 0) {
                waitTradesTask.setNoRefresh();
                return;
            }
            waitTradesTask.setOffers(packet.getOffers());
        }
    }

    @Inject(at = @At("HEAD"), method = "onOpenScreen", cancellable = true)
    public void onOpenScreen(OpenScreenS2CPacket packet, CallbackInfo callback) {
        if (packet.getScreenHandlerType() == ScreenHandlerType.MERCHANT && Worker.getCurrentTask().shouldCloseScreen()) {
            callback.cancel();
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) return;
            CloseHandledScreenC2SPacket packetClose = new CloseHandledScreenC2SPacket(packet.getSyncId());
            Minecraft.getConnection(handler).send(packetClose);
        }
    }
}
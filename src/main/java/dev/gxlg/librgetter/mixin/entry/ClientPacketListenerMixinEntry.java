package dev.gxlg.librgetter.mixin.entry;

import dev.gxlg.librgetter.mixin.impl.ClientPacketListenerMixinImpl;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({ "MixinAnnotationTarget", "UnresolvedMixinReference" })
@Mixin(targets = { "net.minecraft.client.multiplayer.ClientPacketListener", "net.minecraft.class_634" }, remap = false)
public class ClientPacketListenerMixinEntry {
    @Inject(at = @At("HEAD"), method = "handleMerchantOffers(Lnet/minecraft/network/protocol/game/ClientboundMerchantOffersPacket;)V", order = 900, remap = false, require = 0)
    private void handleMerchantOffers1(@Coerce Object packet, CallbackInfo info) {
        ClientPacketListenerMixinImpl.handleMerchantOffers(R.wrapperInst(ClientboundMerchantOffersPacket.class, packet));
    }

    @Inject(at = @At("HEAD"), method = "method_17586(Lnet/minecraft/class_3943;)V", order = 900, remap = false, require = 0)
    private void handleMerchantOffers2(@Coerce Object packet, CallbackInfo info) {
        ClientPacketListenerMixinImpl.handleMerchantOffers(R.wrapperInst(ClientboundMerchantOffersPacket.class, packet));
    }

    // --- //

    @Inject(at = @At("HEAD"), method = "handleOpenScreen(Lnet/minecraft/network/protocol/game/ClientboundOpenScreenPacket;)V", cancellable = true, order = 900, remap = false, require = 0)
    public void handleOpenScreen1(@Coerce Object packet, CallbackInfo callback) {
        ClientPacketListenerMixinImpl.handleOpenScreen(R.wrapperInst(ClientboundOpenScreenPacket.class, packet)).ifPresent(r -> callback.cancel());
    }

    @Inject(at = @At("HEAD"), method = "method_17587(Lnet/minecraft/class_3944;)V", cancellable = true, order = 900, remap = false, require = 0)
    public void handleOpenScreen2(@Coerce Object packet, CallbackInfo callback) {
        ClientPacketListenerMixinImpl.handleOpenScreen(R.wrapperInst(ClientboundOpenScreenPacket.class, packet)).ifPresent(r -> callback.cancel());
    }
}
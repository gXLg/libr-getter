package dev.gxlg.librgetter.mixin.entry.obf;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.ClientPacketListenerMixinImpl;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import dev.gxlg.versiont.mixins.Compare;
import dev.gxlg.versiont.mixins.Comparison;
import dev.gxlg.versiont.mixins.VersiontMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@VersiontMixin(compare = { @Compare(version = "1.17", comparison = Comparison.NOT_LOWER) }, obfuscated = true)
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "net.minecraft.class_634", remap = false)
public class ClientPacketListenerMixinEntry {
    @Inject(at = @At("HEAD"), method = "method_17586(Lnet/minecraft/class_3943;)V", order = 900)
    private void handleMerchantOffers2(@Coerce Object packet, CallbackInfo info) {
        MixinImpl.mixin(ClientPacketListenerMixinImpl.class, i -> i.handleMerchantOffers(R.wrapperInst(ClientboundMerchantOffersPacket.class, packet)));
    }

    @Inject(at = @At("HEAD"), method = "method_17587(Lnet/minecraft/class_3944;)V", cancellable = true, order = 900)
    public void handleOpenScreen2(@Coerce Object packet, CallbackInfo info) {
        MixinImpl.mixinVoid(ClientPacketListenerMixinImpl.class, info, i -> i.handleOpenScreen(R.wrapperInst(ClientboundOpenScreenPacket.class, packet)));
    }
}
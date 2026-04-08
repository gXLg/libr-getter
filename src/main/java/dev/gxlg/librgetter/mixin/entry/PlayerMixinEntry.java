package dev.gxlg.librgetter.mixin.entry;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.PlayerMixinImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({ "MixinAnnotationTarget", "UnresolvedMixinReference" })
@Mixin(targets = { "net.minecraft.world.entity.player.Player", "net.minecraft.class_1657" }, remap = false)
public class PlayerMixinEntry {
    @Inject(at = @At("HEAD"), method = "isSecondaryUseActive()Z", cancellable = true, remap = false, require = 0)
    private void isSecondaryUseActive1(CallbackInfoReturnable<Boolean> info) {
        MixinImpl.mixinReturn(PlayerMixinImpl.class, info, PlayerMixinImpl::isSecondaryUseActive);
    }

    @Inject(at = @At("HEAD"), method = "method_21823()Z", cancellable = true, remap = false, require = 0)
    private void isSecondaryUseActive2(CallbackInfoReturnable<Boolean> info) {
        MixinImpl.mixinReturn(PlayerMixinImpl.class, info, PlayerMixinImpl::isSecondaryUseActive);
    }
}

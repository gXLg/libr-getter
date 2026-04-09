package dev.gxlg.librgetter.mixin.entry.deobf;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.PlayerMixinImpl;
import dev.gxlg.versiont.mixins.Compare;
import dev.gxlg.versiont.mixins.Comparison;
import dev.gxlg.versiont.mixins.VersiontMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@VersiontMixin(compare = { @Compare(version = "1.17", comparison = Comparison.NOT_LOWER) })
@Mixin(targets = "net.minecraft.world.entity.player.Player", remap = false)
public class PlayerMixinEntry {
    @Inject(at = @At("HEAD"), method = "isSecondaryUseActive()Z", cancellable = true)
    private void isSecondaryUseActive(CallbackInfoReturnable<Boolean> info) {
        MixinImpl.mixinReturn(PlayerMixinImpl.class, info, PlayerMixinImpl::isSecondaryUseActive);
    }
}

package dev.gxlg.librgetter.mixin.entry.obf;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.mixins.Compare;
import dev.gxlg.versiont.mixins.Comparison;
import dev.gxlg.versiont.mixins.VersiontMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@VersiontMixin(compare = { @Compare(version = "1.17", comparison = Comparison.NOT_LOWER) }, obfuscated = true)
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "net.minecraft.class_636", remap = false)
public abstract class MultiPlayerGameModeMixinEntry_Common {
    @Inject(at = @At("HEAD"), method = "method_2910(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Z", cancellable = true)
    private void startDestroyBlock(@Coerce Object pos, @Coerce Object direction, CallbackInfoReturnable<Boolean> info) {
        MixinImpl.mixinReturn(MultiPlayerGameModeMixinImpl.class, info, i -> i.startDestroyBlock(R.wrapperInst(BlockPos.class, pos)));
    }
}

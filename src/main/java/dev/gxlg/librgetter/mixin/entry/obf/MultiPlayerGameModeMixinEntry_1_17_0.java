package dev.gxlg.librgetter.mixin.entry.obf;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;
import dev.gxlg.versiont.mixins.Compare;
import dev.gxlg.versiont.mixins.Comparison;
import dev.gxlg.versiont.mixins.VersiontMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@VersiontMixin(compare = { @Compare(version = "1.17", comparison = Comparison.NOT_LOWER), @Compare(version = "1.19", comparison = Comparison.LOWER) }, obfuscated = true)
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "net.minecraft.class_636", remap = false)
public abstract class MultiPlayerGameModeMixinEntry_1_17_0 {
    @Inject(
        at = @At("HEAD"), method = "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_638;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;", cancellable = true
    )
    private void useItemOn(@Coerce Object player, @Coerce Object world, @Coerce Object hand, @Coerce Object blockHit, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.useItemOn(R.wrapperInst(BlockHitResult.class, blockHit)));
    }
}

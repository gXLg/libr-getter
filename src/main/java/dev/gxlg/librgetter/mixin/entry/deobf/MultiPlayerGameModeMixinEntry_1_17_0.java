package dev.gxlg.librgetter.mixin.entry.deobf;

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

@VersiontMixin(compare = { @Compare(version = "1.17", comparison = Comparison.NOT_LOWER), @Compare(version = "1.19", comparison = Comparison.LOWER) })
@SuppressWarnings({ "UnresolvedMixinReference", "MixinAnnotationTarget" })
@Mixin(targets = "net.minecraft.client.multiplayer.MultiPlayerGameMode", remap = false)
public abstract class MultiPlayerGameModeMixinEntry_1_17_0 {
    @Inject(
        at = @At("HEAD"),
        method = "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
        cancellable = true
    )
    private void useItemOn(@Coerce Object player, @Coerce Object world, @Coerce Object hand, @Coerce Object blockHit, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.useItemOn(R.wrapperInst(BlockHitResult.class, blockHit)));
    }
}

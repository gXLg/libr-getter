package dev.gxlg.librgetter.mixin.entry;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({ "UnresolvedMixinReference", "MixinAnnotationTarget" })
@Mixin(targets = { "net.minecraft.client.multiplayer.MultiPlayerGameMode", "net.minecraft.class_636" }, remap = false)
public abstract class MultiPlayerGameModeMixinEntry {

    @Inject(at = @At("HEAD"), method = "startDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", cancellable = true, remap = false, require = 0)
    private void startDestroyBlock1(@Coerce Object pos, @Coerce Object direction, CallbackInfoReturnable<Boolean> info) {
        MixinImpl.mixinReturn(MultiPlayerGameModeMixinImpl.class, info, i -> i.startDestroyBlock(R.wrapperInst(BlockPos.class, pos)));
    }

    @Inject(at = @At("HEAD"), method = "method_2910(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Z", cancellable = true, remap = false, require = 0)
    private void startDestroyBlock2(@Coerce Object pos, @Coerce Object direction, CallbackInfoReturnable<Boolean> info) {
        MixinImpl.mixinReturn(MultiPlayerGameModeMixinImpl.class, info, i -> i.startDestroyBlock(R.wrapperInst(BlockPos.class, pos)));
    }

    // ---- //

    @Inject(
        at = @At("HEAD"),
        method = "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
        cancellable = true, remap = false, require = 0
    )
    private void useItemOn1(@Coerce Object player, @Coerce Object hand, @Coerce Object blockHit, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.useItemOn(R.wrapperInst(BlockHitResult.class, blockHit)));
    }

    @Inject(
        at = @At("HEAD"), method = "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;", cancellable = true, remap = false,
        require = 0
    )
    private void useItemOn2(@Coerce Object player, @Coerce Object hand, @Coerce Object blockHit, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.useItemOn(R.wrapperInst(BlockHitResult.class, blockHit)));
    }

    @Inject(
        at = @At("HEAD"),
        method = "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
        cancellable = true, remap = false, require = 0
    )
    private void useItemOn3(@Coerce Object player, @Coerce Object world, @Coerce Object hand, @Coerce Object blockHit, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.useItemOn(R.wrapperInst(BlockHitResult.class, blockHit)));
    }

    @Inject(
        at = @At("HEAD"), method = "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_638;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;", cancellable = true,
        remap = false, require = 0
    )
    private void useItemOn4(@Coerce Object player, @Coerce Object world, @Coerce Object hand, @Coerce Object blockHit, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.useItemOn(R.wrapperInst(BlockHitResult.class, blockHit)));
    }
}

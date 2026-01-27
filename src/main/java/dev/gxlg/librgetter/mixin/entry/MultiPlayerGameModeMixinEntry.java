package dev.gxlg.librgetter.mixin.entry;

import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({ "UnresolvedMixinReference", "MixinAnnotationTarget" })
@Mixin(targets = { "net.minecraft.client.multiplayer.MultiPlayerGameMode", "net.minecraft.class_636" }, remap = false)
public abstract class MultiPlayerGameModeMixinEntry {

    @Inject(at = @At("HEAD"), method = "tick()V", remap = false, require = 0)
    private void tick1(CallbackInfo info) {
        MultiPlayerGameModeMixinImpl.tick();
    }

    @Inject(at = @At("HEAD"), method = "method_2927()V", remap = false, require = 0)
    private void tick2(CallbackInfo info) {
        MultiPlayerGameModeMixinImpl.tick();
    }

    // ---- //

    @Inject(at = @At("HEAD"), method = "startDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", cancellable = true, remap = false, require = 0)
    private void startDestroyBlock1(@Coerce Object blockPos, @Coerce Object direction, CallbackInfoReturnable<Boolean> info) {
        MultiPlayerGameModeMixinImpl.startDestroyBlock(BlockPosWrapper.inst(blockPos)).ifPresent(info::setReturnValue);
    }

    @Inject(at = @At("HEAD"), method = "method_2910(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Z", cancellable = true, remap = false, require = 0)
    private void startDestroyBlock2(@Coerce Object blockPos, @Coerce Object direction, CallbackInfoReturnable<Boolean> info) {
        MultiPlayerGameModeMixinImpl.startDestroyBlock(BlockPosWrapper.inst(blockPos)).ifPresent(info::setReturnValue);
    }

    // ---- //

    @Inject(
        at = @At("HEAD"),
        method = "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
        cancellable = true, remap = false, require = 0
    )
    private void useItemOn1(@Coerce Object player, @Coerce Object hand, @Coerce Object hitResult, CallbackInfoReturnable<Object> info) {
        MultiPlayerGameModeMixinImpl.useItemOn(BlockHitResultWrapper.inst(hitResult)).ifPresent(r -> info.setReturnValue(r.unwrap()));
    }

    @Inject(
        at = @At("HEAD"), method = "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;", cancellable = true, remap = false,
        require = 0
    )
    private void useItemOn2(@Coerce Object player, @Coerce Object hand, @Coerce Object hitResult, CallbackInfoReturnable<Object> info) {
        MultiPlayerGameModeMixinImpl.useItemOn(BlockHitResultWrapper.inst(hitResult)).ifPresent(r -> info.setReturnValue(r.unwrap()));
    }

    @Inject(
        at = @At("HEAD"),
        method = "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
        cancellable = true, remap = false, require = 0
    )
    private void useItemOn3(@Coerce Object player, @Coerce Object world, @Coerce Object hand, @Coerce Object hitResult, CallbackInfoReturnable<Object> info) {
        MultiPlayerGameModeMixinImpl.useItemOn(BlockHitResultWrapper.inst(hitResult)).ifPresent(r -> info.setReturnValue(r.unwrap()));
    }

    @Inject(
        at = @At("HEAD"), method = "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_638;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;", cancellable = true,
        remap = false, require = 0
    )
    private void useItemOn4(@Coerce Object player, @Coerce Object world, @Coerce Object hand, @Coerce Object hitResult, CallbackInfoReturnable<Object> info) {
        MultiPlayerGameModeMixinImpl.useItemOn(BlockHitResultWrapper.inst(hitResult)).ifPresent(r -> info.setReturnValue(r.unwrap()));
    }
}
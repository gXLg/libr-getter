package dev.gxlg.librgetter.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings({ "UnresolvedMixinReference", "MixinAnnotationTarget" })
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        TaskManager.work();
    }

    @Inject(at = @At("HEAD"), method = "startDestroyBlock", cancellable = true)
    private void attackBlock(CallbackInfoReturnable<Boolean> info, @Local(argsOnly = true) BlockPos pos) {
        if (minecraft.player == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "player", "ClientPlayerInteractionManagerMixin#attackBlock");
            return;
        }
        ClientLevel world = MinecraftHelper.getWorld(minecraft.player);
        if (!world.getBlockState(pos).is(Blocks.LECTERN)) {
            return;
        }
        if (!TaskManager.getCurrentTask().allowsBreaking()) {
            info.setReturnValue(false);
        }
    }

    /// <%
    @Inject(at = @At("HEAD"), method = "$1", cancellable = true, require = 0, remap = false)
    private void interactBlock$2(CallbackInfoReturnable<InteractionResult> info, @Local(argsOnly = true) BlockHitResult hitResult) {
        interactBlock(info, hitResult);
    }
    /// [
    ///   "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;",
    ///   "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
    ///   "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_638;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;",
    ///   "useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"
    /// ].map((s, i) => code.replace("$1", s).replace("$2", i))
    /// %>

    @Unique
    private void interactBlock(CallbackInfoReturnable<InteractionResult> info, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());

        if (minecraft.player == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "player", "ClientPlayerInteractionManagerMixin#interactBlock");
            return;
        }

        if (!pos.equals(TaskManager.getTaskContext().selectedLecternPos())) {
            return;
        }
        if (!TaskManager.getCurrentTask().allowsPlacing()) {
            MinecraftHelper.setActionResultFail(info);
        }
    }
}
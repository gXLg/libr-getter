package com.gxlg.librgetter.mixin;

import com.gxlg.librgetter.Worker;
import com.gxlg.librgetter.utils.reflection.Minecraft;
import com.gxlg.librgetter.utils.reflection.Texts;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(ClientPlayerInteractionManager.class)
public abstract class Manager {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        Worker.tick();
    }

    @Inject(at = @At("HEAD"), method = "attackBlock", cancellable = true)
    private void attackBlock(CallbackInfoReturnable<Boolean> info, @Local(argsOnly = true) BlockPos pos) {
        if (client.player == null) {
            Texts.sendError(Worker.getSource(), "librgetter.internal", "player");
            return;
        }
        ClientWorld world = this.client.player.clientWorld;
        if (!world.getBlockState(pos).isOf(Blocks.LECTERN)) return;
        for (Worker.State state : new Worker.State[]{Worker.State.MANUAL_WAIT_FINISH, Worker.State.GET_TRADES, Worker.State.PARSE_TRADES, Worker.State.WAIT_VILLAGER_ACCEPT_PROFESSION}) {
            if (Worker.getState() == state) {
                info.setReturnValue(false);
                return;
            }
        }
    }

    /// <%
    @Inject(at = @At("HEAD"), method = "$1", cancellable = true, require = 0, remap = false)
    private void interactBlock$2(CallbackInfoReturnable<ActionResult> info, @Local(argsOnly = true) BlockHitResult hitResult) {
        interactBlock(info, hitResult);
    }
    /// [
    ///   "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;",
    ///   "interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;",
    ///   "method_2896(Lnet/minecraft/class_746;Lnet/minecraft/class_638;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;",
    ///   "interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"
    /// ].map((s, i) => code.replace("$1", s).replace("$2", i))
    /// %>

    @Unique
    private void interactBlock(CallbackInfoReturnable<ActionResult> info, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos().offset(hitResult.getSide());

        if (client.player == null) {
            Texts.sendError(Worker.getSource(), "librgetter.internal", "player");
            return;
        }

        if (!pos.equals(Worker.getBlock())) return;
        if (Worker.getState() == Worker.State.WAIT_VILLAGER_LOSE_PROFESSION) Minecraft.setActionResultFail(info);
    }
}
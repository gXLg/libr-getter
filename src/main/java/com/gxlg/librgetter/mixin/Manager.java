package com.gxlg.librgetter.mixin;

import com.gxlg.librgetter.Worker;
import com.gxlg.librgetter.utils.Messages;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
            Messages.sendError(Worker.getSource(), "librgetter.internal", "player");
            return;
        }
        ClientWorld world = this.client.player.clientWorld;
        if (!world.getBlockState(pos).isOf(Blocks.LECTERN)) return;
        for (Worker.State state : new Worker.State[]{Worker.State.MANUAL_WAIT, Worker.State.GET, Worker.State.PARSING, Worker.State.RESET}) {
            if (Worker.getState() == state) {
                info.setReturnValue(false);
                return;
            }
        }
    }
}
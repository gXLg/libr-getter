package com.gxlg.librgetter.mixin;

import com.gxlg.librgetter.Worker;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class Ticker {
    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        Worker.tick();
    }
}

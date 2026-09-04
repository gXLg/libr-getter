package dev.gxlg.librgetter.mixin.entry.deobf;

import dev.gxlg.librgetter.mixin.MixinImpl;
import dev.gxlg.librgetter.mixin.impl.MultiPlayerGameModeMixinImpl;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.Entity;
import dev.gxlg.versiont.mixins.Compare;
import dev.gxlg.versiont.mixins.Comparison;
import dev.gxlg.versiont.mixins.VersiontMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@VersiontMixin(compare = { @Compare(version = "26.1", comparison = Comparison.NOT_LOWER) })
@Mixin(targets = "net.minecraft.client.multiplayer.MultiPlayerGameMode", remap = false)
public class MultiPlayerGameModeMixinEntry_26_1_0 {
    @Inject(
        at = @At("HEAD"),
        method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/EntityHitResult;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
        cancellable = true
    )
    private void interact(@Coerce Object player, @Coerce Object entity, @Coerce Object hitResult, @Coerce Object hand, CallbackInfoReturnable<Object> info) {
        MixinImpl.mixinReturnWrapped(MultiPlayerGameModeMixinImpl.class, info, i -> i.interact(R.wrapperInst(Entity.class, entity)));
    }
}

package com.gxlg.librgetter.mixin;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface AbstractBlockAccessor {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Accessor("collidable")
    boolean getCollidable();
}

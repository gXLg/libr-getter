package dev.gxlg.librgetter.mixin;

import net.minecraft.client.gui.screen.ingame.BookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BookScreen.class)
public interface BookScreenAccessor {
    @Accessor("cachedPageIndex")
    void setCachedPageIndex(int index);
}

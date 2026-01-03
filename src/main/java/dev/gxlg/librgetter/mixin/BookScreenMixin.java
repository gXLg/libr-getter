package dev.gxlg.librgetter.mixin;

import dev.gxlg.librgetter.gui.ConfigScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({ "UnresolvedMixinReference", "MixinAnnotationTarget" })
@Mixin(BookScreen.class)
public class BookScreenMixin {

    // used only with RUN_COMMAND in 1.21.5 and before

    /// <%
    @Inject(at = @At("HEAD"), method = "$1", cancellable = true, require = 0, remap = false)
    private void close$2(CallbackInfo info) {
        BookScreen current = (BookScreen) (Object) this;
        if (current instanceof ConfigScreen configScreen) {
            configScreen.updateScreen();
            info.cancel();
            // by not executing super, we avoid the book actually closing
        }
    }
    /// [
    ///   "method_34494()V",
    ///   "closeScreen()V"
    /// ].map((s, i) => code.replace("$1", s).replace("$2", i))
    /// %>
}

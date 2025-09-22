package com.gxlg.librgetter.mixin;

import com.gxlg.librgetter.gui.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(BookScreen.class)
public class BookScreenMixin extends Screen {
    protected BookScreenMixin(Text title) {
        super(title);
    }

    // used only with RUN_COMMAND in 1.21.5 and before
    /// <%
    @SuppressWarnings("DataFlowIssue")
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

    // 1.21.6 and after
    /// <%
    @SuppressWarnings("DataFlowIssue")
    @Inject(at = @At("RETURN"), method = "$1", require = 0, remap = false)
    private void handle$2(CallbackInfo info) {
        BookScreen current = (BookScreen) (Object) this;
        if (current instanceof ConfigScreen configScreen) {
            configScreen.updateScreen();
        }
    }
    /// [
    ///   "method_71846(Lnet/minecraft/class_310;Lnet/minecraft/class_2558;)V",
    ///   "handleClickEvent(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/text/ClickEvent;)V"
    /// ].map((s, i) => code.replace("$1", s).replace("$2", i))
    /// %>

}

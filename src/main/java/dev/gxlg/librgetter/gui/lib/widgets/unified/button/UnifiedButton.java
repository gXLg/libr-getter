package dev.gxlg.librgetter.gui.lib.widgets.unified.button;

import dev.gxlg.librgetter.gui.lib.widgets.unified.UnifiedWidget;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public interface UnifiedButton extends UnifiedWidget {
    void setMessage(Component message);
}

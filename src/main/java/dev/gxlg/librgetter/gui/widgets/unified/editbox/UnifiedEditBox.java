package dev.gxlg.librgetter.gui.widgets.unified.editbox;

import dev.gxlg.librgetter.gui.widgets.unified.UnifiedWidget;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public interface UnifiedEditBox extends UnifiedWidget {
    void setResponder(Consumer<String> responder);

    void setHint(Component component);

    String getValue();
}

package dev.gxlg.librgetter.utils.types.messages.translatable.config;

import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;

public class TranslatableConfigDescription extends TranslatableMessage {
    public TranslatableConfigDescription(Configurable<?> configurable) {
        super("librgetter.config." + configurable.name());
    }
}

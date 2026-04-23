package dev.gxlg.librgetter.utils.messages.translatable.config;

import dev.gxlg.librgetter.config.types.helpers.Configurable;
import dev.gxlg.librgetter.utils.messages.translatable.TranslatableMessage;

public class TranslatableConfigDescription extends TranslatableMessage {
    public TranslatableConfigDescription(Configurable<?> configurable) {
        super("librgetter.config." + configurable.config().getId());
    }
}

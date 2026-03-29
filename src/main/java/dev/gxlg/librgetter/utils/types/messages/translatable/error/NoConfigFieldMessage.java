package dev.gxlg.librgetter.utils.types.messages.translatable.error;

import dev.gxlg.librgetter.utils.config.Config;

public class NoConfigFieldMessage extends TranslatableErrorMessage {
    public NoConfigFieldMessage(Config config) {
        super("librgetter.error.config.nofield", config.getId());
    }
}

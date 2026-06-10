package dev.gxlg.librgetter.utils.messages.translatable.error;

import dev.gxlg.librgetter.savefiles.config.Config;

public class NoConfigFieldMessage extends TranslatableErrorMessage {
    public NoConfigFieldMessage(Config config) {
        super("librgetter.error.config.nofield", config.getId());
    }
}

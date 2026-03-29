package dev.gxlg.librgetter.utils.types.messages.translatable.error;

import dev.gxlg.librgetter.utils.config.Config;

public class UncategorizedConfigMessage extends TranslatableErrorMessage {
    public UncategorizedConfigMessage(Config config) {
        super("librgetter.error.config.uncategorized", config.getId());
    }
}

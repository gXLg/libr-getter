package dev.gxlg.librgetter.utils.messages.translatable.error;

import dev.gxlg.librgetter.config.Config;

public class UncategorizedConfigMessage extends TranslatableErrorMessage {
    public UncategorizedConfigMessage(Config config) {
        super("librgetter.error.config.uncategorized", config.getId());
    }
}

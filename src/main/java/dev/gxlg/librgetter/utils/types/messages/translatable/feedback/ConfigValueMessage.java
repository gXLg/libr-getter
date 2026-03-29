package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.config.Config;

public class ConfigValueMessage extends TranslatableFeedbackMessage {
    public ConfigValueMessage(Config config, Object value) {
        super("librgetter.feedback.config", config.getId(), value);
    }
}

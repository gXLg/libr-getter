package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

public class ConfigValueMessage extends TranslatableFeedbackMessage {
    public ConfigValueMessage(String configName, Object value) {
        super("librgetter.feedback.config", configName, value);
    }
}

package dev.gxlg.librgetter.utils.types.messages.feedback;

public class ConfigValueMessage extends TranslatableFeedbackMessage {
    public ConfigValueMessage(String configName, Object value) {
        super("librgetter.feedback.config", configName, value);
    }
}

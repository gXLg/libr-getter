package dev.gxlg.librgetter.utils.messages.translatable.error;

public class UnknownPluginDataMessage extends TranslatableErrorMessage {
    public UnknownPluginDataMessage(String pluginName) {
        super("librgetter.error.unknown", pluginName);
    }
}

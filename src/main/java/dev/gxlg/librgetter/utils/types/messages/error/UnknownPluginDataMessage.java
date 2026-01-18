package dev.gxlg.librgetter.utils.types.messages.error;

public class UnknownPluginDataMessage extends TranslatableErrorMessage {
    public UnknownPluginDataMessage(String pluginName) {
        super("librgetter.error.unknown", pluginName);
    }
}

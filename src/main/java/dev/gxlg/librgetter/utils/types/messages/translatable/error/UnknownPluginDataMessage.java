package dev.gxlg.librgetter.utils.types.messages.translatable.error;

public class UnknownPluginDataMessage extends TranslatableErrorMessage {
    public UnknownPluginDataMessage(String pluginName) {
        super("librgetter.error.unknown", pluginName);
    }
}

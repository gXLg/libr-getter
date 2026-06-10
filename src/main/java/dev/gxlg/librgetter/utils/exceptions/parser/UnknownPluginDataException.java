package dev.gxlg.librgetter.utils.exceptions.parser;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.UnknownPluginDataMessage;

public class UnknownPluginDataException extends LibrGetterException {
    public UnknownPluginDataException(String pluginName) {
        super(new UnknownPluginDataMessage(pluginName));
    }
}

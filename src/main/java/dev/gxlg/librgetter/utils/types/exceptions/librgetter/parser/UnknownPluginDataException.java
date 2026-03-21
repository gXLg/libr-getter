package dev.gxlg.librgetter.utils.types.exceptions.librgetter.parser;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.UnknownPluginDataMessage;

public class UnknownPluginDataException extends LibrGetterException {
    public UnknownPluginDataException(String pluginName) {
        super(new UnknownPluginDataMessage(pluginName));
    }
}

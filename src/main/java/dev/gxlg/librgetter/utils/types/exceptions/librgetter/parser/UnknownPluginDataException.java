package dev.gxlg.librgetter.utils.types.exceptions.librgetter.parser;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.UnknownPluginDataMessage;

public class UnknownPluginDataException extends LibrGetterException {
    public UnknownPluginDataException(String pluginName) {
        super(new UnknownPluginDataMessage(pluginName));
    }
}

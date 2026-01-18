package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.WrongEnchantmentMessage;

public class WrongEnchantmentException extends LibrGetterException {
    public WrongEnchantmentException() {
        super(new WrongEnchantmentMessage());
    }
}

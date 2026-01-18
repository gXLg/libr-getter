package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.WrongEnchantmentMessage;

public class WrongEnchantmentException extends LibrGetterException {
    public WrongEnchantmentException() {
        super(new WrongEnchantmentMessage());
    }
}

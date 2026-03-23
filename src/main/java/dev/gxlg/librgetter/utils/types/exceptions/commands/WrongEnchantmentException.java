package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.WrongEnchantmentMessage;

public class WrongEnchantmentException extends LibrGetterException {
    public WrongEnchantmentException() {
        super(new WrongEnchantmentMessage());
    }
}

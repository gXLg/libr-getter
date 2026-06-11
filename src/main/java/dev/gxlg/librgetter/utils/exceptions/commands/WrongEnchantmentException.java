package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.WrongEnchantmentMessage;

public class WrongEnchantmentException extends LibrGetterException {
    public WrongEnchantmentException() {
        super(new WrongEnchantmentMessage());
    }
}

package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.NotInGoalsMessage;

public class NotInGoalsException extends LibrGetterException {
    public NotInGoalsException(String name) {
        super(new NotInGoalsMessage(name));
    }

    public NotInGoalsException(EnchantmentTrade trade) {
        super(new NotInGoalsMessage(trade));
    }
}

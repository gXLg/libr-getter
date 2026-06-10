package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.NotInGoalsMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class NotInGoalsException extends LibrGetterException {
    public NotInGoalsException(EnchantmentTrade trade) {
        super(new NotInGoalsMessage(trade));
    }

    public NotInGoalsException(List<EnchantmentTrade> trades) {
        super(new NotInGoalsMessage(trades));
    }
}

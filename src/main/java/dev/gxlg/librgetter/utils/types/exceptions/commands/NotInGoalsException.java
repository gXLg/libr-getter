package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.NotInGoalsMessage;

import java.util.List;

public class NotInGoalsException extends LibrGetterException {
    public NotInGoalsException(EnchantmentTrade trade) {
        super(new NotInGoalsMessage(trade));
    }

    public NotInGoalsException(List<EnchantmentTrade> trades) {
        super(new NotInGoalsMessage(trades));
    }
}

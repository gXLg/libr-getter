package dev.gxlg.librgetter.utils.types.translatable_messages.error;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class NotInGoalsMessage extends TranslatableErrorMessage {
    public NotInGoalsMessage(EnchantmentTrade trade) {
        super("librgetter.error.not", trade);
    }

    public NotInGoalsMessage(List<EnchantmentTrade> trades) {
        super("librgetter.error.not", trades);
    }
}

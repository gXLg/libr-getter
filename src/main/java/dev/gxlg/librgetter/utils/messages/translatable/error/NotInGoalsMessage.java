package dev.gxlg.librgetter.utils.messages.translatable.error;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeListMessage;
import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class NotInGoalsMessage extends TranslatableErrorMessage {
    public NotInGoalsMessage(EnchantmentTrade trade) {
        super("librgetter.error.not", new TradeMessage(trade));
    }

    public NotInGoalsMessage(List<EnchantmentTrade> trades) {
        super("librgetter.error.not", new TradeListMessage(trades));
    }
}

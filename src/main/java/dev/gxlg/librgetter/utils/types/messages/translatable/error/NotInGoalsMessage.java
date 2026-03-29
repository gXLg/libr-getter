package dev.gxlg.librgetter.utils.types.messages.translatable.error;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeListMessage;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

import java.util.List;

public class NotInGoalsMessage extends TranslatableErrorMessage {
    public NotInGoalsMessage(EnchantmentTrade trade) {
        super("librgetter.error.not", new TradeMessage(trade));
    }

    public NotInGoalsMessage(List<EnchantmentTrade> trades) {
        super("librgetter.error.not", new TradeListMessage(trades));
    }
}

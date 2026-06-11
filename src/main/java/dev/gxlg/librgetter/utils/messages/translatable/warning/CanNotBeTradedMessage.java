package dev.gxlg.librgetter.utils.messages.translatable.warning;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class CanNotBeTradedMessage extends TranslatableWarningMessage {
    public CanNotBeTradedMessage(EnchantmentTrade trade) {
        super("librgetter.warning.notrade", new TradeMessage(trade));
    }
}

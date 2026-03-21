package dev.gxlg.librgetter.utils.types.messages.translatable.warning;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class CanNotBeTradedMessage extends TranslatableWarningMessage {
    public CanNotBeTradedMessage(EnchantmentTrade trade) {
        super("librgetter.warning.notrade", new TradeMessage(trade));
    }
}

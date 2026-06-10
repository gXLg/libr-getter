package dev.gxlg.librgetter.utils.messages.translatable.success;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class CustomTradeAddedMessage extends TranslatableSuccessMessage {
    public CustomTradeAddedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.add_custom", new TradeMessage(trade), price);
    }
}

package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class CustomTradeAddedMessage extends TranslatableSuccessMessage {
    public CustomTradeAddedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.add_custom", new TradeMessage(trade), price);
    }
}

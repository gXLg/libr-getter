package dev.gxlg.librgetter.utils.messages.translatable.success;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class TradeAddedMessage extends TranslatableSuccessMessage {
    public TradeAddedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.add", new TradeMessage(trade), price);
    }
}

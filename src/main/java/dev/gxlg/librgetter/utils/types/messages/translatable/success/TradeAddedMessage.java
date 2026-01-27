package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class TradeAddedMessage extends TranslatableSuccessMessage {
    public TradeAddedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.add", new TradeMessage(trade), price);
    }
}

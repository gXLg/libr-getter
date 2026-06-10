package dev.gxlg.librgetter.utils.messages.translatable.success;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class PriceChangedMessage extends TranslatableSuccessMessage {
    public PriceChangedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.price", new TradeMessage(trade), price);
    }
}

package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class PriceChangedMessage extends TranslatableSuccessMessage {
    public PriceChangedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.price", new TradeMessage(trade), price);
    }
}

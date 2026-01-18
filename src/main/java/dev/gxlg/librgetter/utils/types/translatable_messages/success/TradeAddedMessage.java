package dev.gxlg.librgetter.utils.types.translatable_messages.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class TradeAddedMessage extends TranslatableSuccessMessage {
    public TradeAddedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.add", trade, price);
    }
}

package dev.gxlg.librgetter.utils.types.translatable_messages.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class PriceChangedMessage extends TranslatableSuccessMessage {
    public PriceChangedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.price", trade, price);
    }
}

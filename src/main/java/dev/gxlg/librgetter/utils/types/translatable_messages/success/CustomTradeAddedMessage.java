package dev.gxlg.librgetter.utils.types.translatable_messages.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class CustomTradeAddedMessage extends TranslatableSuccessMessage {
    public CustomTradeAddedMessage(EnchantmentTrade trade, int price) {
        super("librgetter.success.add_custom", trade, price);
    }
}

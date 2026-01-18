package dev.gxlg.librgetter.utils.types.messages.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class EnchantmentFoundMessage extends TranslatableSuccessMessage {
    public EnchantmentFoundMessage(EnchantmentTrade trade, int tries, int price) {
        super("librgetter.success.found", trade, tries, price);
    }
}

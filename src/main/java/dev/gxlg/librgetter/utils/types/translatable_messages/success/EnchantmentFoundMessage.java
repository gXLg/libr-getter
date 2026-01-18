package dev.gxlg.librgetter.utils.types.translatable_messages.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class EnchantmentFoundMessage extends TranslatableSuccessMessage {
    public EnchantmentFoundMessage(EnchantmentTrade trade, int tries) {
        super("librgetter.success.found", trade, tries, trade.price());
    }
}

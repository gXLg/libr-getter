package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class EnchantmentFoundMessage extends TranslatableSuccessMessage {
    public EnchantmentFoundMessage(EnchantmentTrade trade, int tries) {
        super("librgetter.success.found", new TradeMessage(trade), tries, trade.price());
    }
}

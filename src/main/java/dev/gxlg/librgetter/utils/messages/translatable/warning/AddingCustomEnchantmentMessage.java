package dev.gxlg.librgetter.utils.messages.translatable.warning;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class AddingCustomEnchantmentMessage extends TranslatableWarningMessage {
    public AddingCustomEnchantmentMessage(EnchantmentTrade trade) {
        super("librgetter.warning.custom", new TradeMessage(trade));
    }
}

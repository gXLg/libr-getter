package dev.gxlg.librgetter.utils.types.messages.translatable.warning;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class AddingCustomEnchantmentMessage extends TranslatableWarningMessage {
    public AddingCustomEnchantmentMessage(EnchantmentTrade trade) {
        super("librgetter.warning.custom", new TradeMessage(trade));
    }
}

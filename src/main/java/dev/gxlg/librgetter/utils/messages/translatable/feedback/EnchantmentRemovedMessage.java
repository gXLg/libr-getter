package dev.gxlg.librgetter.utils.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class EnchantmentRemovedMessage extends TranslatableFeedbackMessage {
    public EnchantmentRemovedMessage(EnchantmentTrade enchantment) {
        super("librgetter.feedback.removed", new TradeMessage(enchantment));
    }
}

package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class EnchantmentRemovedMessage extends TranslatableFeedbackMessage {
    public EnchantmentRemovedMessage(EnchantmentTrade enchantment) {
        super("librgetter.feedback.removed", new TradeMessage(enchantment));
    }
}

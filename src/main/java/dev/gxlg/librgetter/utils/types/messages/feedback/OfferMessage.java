package dev.gxlg.librgetter.utils.types.messages.feedback;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class OfferMessage extends TranslatableFeedbackMessage {
    public OfferMessage(List<EnchantmentTrade> trade) {
        super("librgetter.feedback.offer", trade);
    }
}

package dev.gxlg.librgetter.utils.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeListMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

import java.util.List;

public class OfferMessage extends TranslatableFeedbackMessage {
    public OfferMessage(List<EnchantmentTrade> trades) {
        super("librgetter.feedback.offer", new TradeListMessage(trades));
    }
}

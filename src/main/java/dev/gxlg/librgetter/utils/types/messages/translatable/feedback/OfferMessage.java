package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeListMessage;

import java.util.List;

public class OfferMessage extends TranslatableFeedbackMessage {
    public OfferMessage(List<EnchantmentTrade> trades) {
        super("librgetter.feedback.offer", new TradeListMessage(trades));
    }
}

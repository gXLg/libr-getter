package dev.gxlg.librgetter.utils.types.translatable_messages.warning;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class CanNotBeTradedMessage extends TranslatableWarningMessage {
    public CanNotBeTradedMessage(EnchantmentTrade trade) {
        super("librgetter.warning.notrade", trade);
    }
}

package dev.gxlg.librgetter.utils.types.translatable_messages.warning;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class AddingCustomEnchantmentMessage extends TranslatableWarningMessage {
    public AddingCustomEnchantmentMessage(EnchantmentTrade trade) {
        super("librgetter.warning.custom", trade);
    }
}

package dev.gxlg.librgetter.utils.types.translatable_messages.feedback;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class EnchantmentRemovedMessage extends TranslatableFeedbackMessage {
    public EnchantmentRemovedMessage(EnchantmentTrade enchantment) {
        super("librgetter.feedback.removed", enchantment);
    }
}

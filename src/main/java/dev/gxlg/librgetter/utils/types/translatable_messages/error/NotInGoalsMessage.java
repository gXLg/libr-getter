package dev.gxlg.librgetter.utils.types.translatable_messages.error;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class NotInGoalsMessage extends TranslatableErrorMessage {
    public NotInGoalsMessage(String enchantmentId) {
        super("librgetter.error.not", MinecraftHelper.translateEnchantmentId(enchantmentId));
    }

    public NotInGoalsMessage(EnchantmentTrade trade) {
        super("librgetter.error.not", trade);
    }
}

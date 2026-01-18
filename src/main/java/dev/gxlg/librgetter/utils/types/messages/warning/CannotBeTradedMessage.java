package dev.gxlg.librgetter.utils.types.messages.warning;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;

public class CannotBeTradedMessage extends TranslatableWarningMessage {
    public CannotBeTradedMessage(String enchantmentId) {
        super("librgetter.warning.notrade", MinecraftHelper.translateEnchantmentId(enchantmentId));
    }
}

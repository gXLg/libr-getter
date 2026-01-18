package dev.gxlg.librgetter.utils.types.messages.warning;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;

public class AddingCustomEnchantmentMessage extends TranslatableWarningMessage {
    public AddingCustomEnchantmentMessage(String enchantmentId) {
        super("librgetter.warning.custom", MinecraftHelper.translateEnchantmentId(enchantmentId));
    }
}

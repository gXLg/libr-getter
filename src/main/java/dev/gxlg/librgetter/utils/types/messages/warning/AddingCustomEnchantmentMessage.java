package dev.gxlg.librgetter.utils.types.messages.warning;

public class AddingCustomEnchantmentMessage extends TranslatableWarningMessage {
    public AddingCustomEnchantmentMessage(String enchantmentId) {
        super("librgetter.warning.custom", enchantmentId);
    }
}

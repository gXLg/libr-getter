package dev.gxlg.librgetter.utils.types.messages.warning;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import net.minecraft.resources.Identifier;

public class CanNotBeTradedMessage extends TranslatableWarningMessage {
    public CanNotBeTradedMessage(Identifier enchantmentId) {
        super("librgetter.warning.notrade", MinecraftHelper.translateEnchantmentId(enchantmentId));
    }
}

package dev.gxlg.librgetter.utils.types.translatable_messages.warning;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import net.minecraft.resources.Identifier;

public class LevelOverMaxMessage extends TranslatableWarningMessage {
    public LevelOverMaxMessage(Identifier enchantmentId, int maxLevel) {
        super("librgetter.warning.level", MinecraftHelper.translateEnchantmentId(enchantmentId), maxLevel);
    }
}

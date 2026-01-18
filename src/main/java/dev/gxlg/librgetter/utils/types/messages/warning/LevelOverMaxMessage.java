package dev.gxlg.librgetter.utils.types.messages.warning;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import net.minecraft.resources.Identifier;

public class LevelOverMaxMessage extends TranslatableWarningMessage {
    public LevelOverMaxMessage(EnchantmentTrade trade, int maxLevel) {
        super("librgetter.warning.level", trade, maxLevel);
    }

    public LevelOverMaxMessage(Identifier enchantmentId, int maxLevel) {
        super("librgetter.warning.level", MinecraftHelper.translateEnchantmentId(enchantmentId), maxLevel);
    }
}

package dev.gxlg.librgetter.utils.types.messages.warning;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class LevelOverMaxMessage extends TranslatableWarningMessage {
    public LevelOverMaxMessage(EnchantmentTrade trade, int maxLevel) {
        super("librgetter.warning.level", trade, maxLevel);
    }
}

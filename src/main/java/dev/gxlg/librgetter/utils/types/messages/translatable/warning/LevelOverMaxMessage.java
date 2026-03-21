package dev.gxlg.librgetter.utils.types.messages.translatable.warning;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;

public class LevelOverMaxMessage extends TranslatableWarningMessage {
    public LevelOverMaxMessage(EnchantmentTrade trade, int maxLevel) {
        super("librgetter.warning.level", new TradeMessage(trade), maxLevel);
    }
}

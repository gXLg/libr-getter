package dev.gxlg.librgetter.utils.messages.translatable.warning;

import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;

public class LevelOverMaxMessage extends TranslatableWarningMessage {
    public LevelOverMaxMessage(EnchantmentTrade trade, int maxLevel) {
        super("librgetter.warning.level", new TradeMessage(trade), maxLevel);
    }
}

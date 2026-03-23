package dev.gxlg.librgetter.utils.types.messages.translatable.success;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.partial.TranslatableRemoveButton;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;

public class EnchantmentFoundMessage extends TranslatableSuccessMessage {
    private final EnchantmentTrade trade;

    private final boolean showRemoveButton;

    public EnchantmentFoundMessage(EnchantmentTrade trade, int tries, boolean showRemoveButton) {
        super("librgetter.success.found", new TradeMessage(trade), tries, trade.price());
        this.trade = trade;
        this.showRemoveButton = showRemoveButton;
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = super.buildComponent();
        text = text.withStyle(ChatFormatting.GREEN());
        if (showRemoveButton) {
            text = text.append(" ");
            Style style = Style.EMPTY().withClickEvent(Texts.runnable("/librget remove \"" + trade.id() + "\" " + trade.lvl()));
            text = text.append(new TranslatableRemoveButton().getComponent().withStyle(style));
        }
        return text;
    }
}

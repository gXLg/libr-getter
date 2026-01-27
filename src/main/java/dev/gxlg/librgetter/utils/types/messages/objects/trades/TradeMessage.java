package dev.gxlg.librgetter.utils.types.messages.objects.trades;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;

public class TradeMessage extends Message {
    private final EnchantmentTrade trade;

    public TradeMessage(EnchantmentTrade trade) {
        this.trade = trade;
    }

    @Override
    public ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.LIGHT_PURPLE();
    }

    @Override
    protected MutableComponentWrapper buildComponent() {
        return Texts.getImpl().enchantmentTradeToComponent(trade);
    }
}

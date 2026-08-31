package dev.gxlg.librgetter.utils.messages.objects.trades;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.Message;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class TradeMessage extends Message {
    private final EnchantmentTrade trade;

    private final boolean showPrice;

    public TradeMessage(EnchantmentTrade trade) {
        this(trade, false);
    }

    public TradeMessage(EnchantmentTrade trade, boolean showPrice) {
        this.trade = trade;
        this.showPrice = showPrice;
    }

    @Override
    public ChatFormatting getColor() {
        return ChatFormatting.LIGHT_PURPLE();
    }

    @Override
    protected MutableComponent buildComponent() {
        Identifier id = Identifier.tryParse(trade.id());
        if (id == null) {
            return Texts.literal(trade.id());
        }
        String enchantmentName = Texts.translateIdentifier(Texts.IdentifierType.ENCHANTMENT, id);
        MutableComponent text = Texts.literal(enchantmentName + (trade.lvl() == -1 ? "" : " " + trade.lvl()));
        if (showPrice) {
            text = text.append(Texts.literal(" ("));
            text = text.append(Texts.literal(trade.price() + "").withStyle(ChatFormatting.GREEN()));
            text = text.append(Texts.literal(")").withStyle(ChatFormatting.RESET()));
        }
        return text;
    }
}

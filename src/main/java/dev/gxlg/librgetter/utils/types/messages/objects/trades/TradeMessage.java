package dev.gxlg.librgetter.utils.types.messages.objects.trades;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class TradeMessage extends Message {
    private final EnchantmentTrade trade;

    public TradeMessage(EnchantmentTrade trade) {
        this.trade = trade;
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
        return Texts.literal(enchantmentName + (trade.lvl() == -1 ? "" : " " + trade.lvl()));
    }
}

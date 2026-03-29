package dev.gxlg.librgetter.utils.types.messages.objects.trades;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class TradeListMessage extends Message {
    private final List<TradeMessage> tradeMessages;

    public TradeListMessage(List<EnchantmentTrade> tradeList) {
        this.tradeMessages = tradeList.stream().map(TradeMessage::new).toList();
    }

    @Override
    protected ChatFormatting getColor() {
        return ChatFormatting.DARK_PURPLE();
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = Texts.literal("[");
        for (int i = 0; i < tradeMessages.size(); i++) {
            text = text.append(tradeMessages.get(i).getComponent());
            if (i < tradeMessages.size() - 1) {
                text = text.append(Texts.literal(", "));
            }
        }
        return text.append(Texts.literal("]"));
    }
}

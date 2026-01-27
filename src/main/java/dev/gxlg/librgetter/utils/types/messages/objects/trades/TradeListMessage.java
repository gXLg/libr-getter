package dev.gxlg.librgetter.utils.types.messages.objects.trades;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;

import java.util.List;

public class TradeListMessage extends Message {
    private final List<EnchantmentTrade> tradeList;

    public TradeListMessage(List<EnchantmentTrade> tradeList) {
        this.tradeList = tradeList;
    }

    @Override
    protected ChatFormattingWrapper getColor() {
        return ChatFormattingWrapper.DARK_PURPLE();
    }

    @Override
    protected MutableComponentWrapper buildComponent() {
        MutableComponentWrapper text = Texts.getImpl().literal("[");
        for (int i = 0; i < tradeList.size(); i++) {
            text = text.append(new TradeMessage(tradeList.get(i)).getComponent());
            if (i < tradeList.size() - 1) {
                text = text.append(Texts.getImpl().literal(", "));
            }
        }
        return text.append(Texts.getImpl().literal("]"));
    }
}

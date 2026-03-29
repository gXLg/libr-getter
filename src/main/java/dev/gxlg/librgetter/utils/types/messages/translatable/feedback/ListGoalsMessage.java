package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.partial.TranslatableRemoveButton;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;

import java.util.List;

public class ListGoalsMessage extends TranslatableFeedbackMessage {
    private final List<EnchantmentTrade> goals;

    public ListGoalsMessage(List<EnchantmentTrade> goals) {
        super("librgetter.feedback.list");
        this.goals = List.copyOf(goals);
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = super.buildComponent();
        for (EnchantmentTrade goal : goals) {
            text = text.append("\n- ").append(new TradeMessage(goal).getComponent()).append(" (" + goal.price() + ") ");
            Style style = Style.EMPTY().withClickEvent(Texts.runnable("/librget remove \"" + goal.id() + "\" " + goal.lvl()));
            text = text.append(new TranslatableRemoveButton().getComponent().withStyle(style));
        }
        return text;
    }
}

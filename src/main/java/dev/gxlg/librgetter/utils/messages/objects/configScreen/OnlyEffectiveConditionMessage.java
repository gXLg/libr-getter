package dev.gxlg.librgetter.utils.messages.objects.configScreen;

import dev.gxlg.librgetter.savefiles.config.types.helpers.Configurable;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.Message;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

public class OnlyEffectiveConditionMessage extends Message {
    private final Configurable.OnlyEffectiveCondition condition;

    public OnlyEffectiveConditionMessage(Configurable.OnlyEffectiveCondition condition) {
        this.condition = condition;
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = Texts.literal(condition.when());
        text = text.append(Texts.literal(" = ").withStyle(ChatFormatting.DARK_PURPLE()));
        if (condition.equals().length > 1) {
            text = text.append(Texts.literal("[").withStyle(ChatFormatting.DARK_PURPLE()));
        }
        for (int i = 0; i < condition.equals().length; i++) {
            String value = condition.equals()[i];
            text = text.append(Texts.literal(value).withStyle(ChatFormatting.RESET()));
            if (i < condition.equals().length - 1) {
                text = text.append(Texts.literal("|").withStyle(ChatFormatting.DARK_PURPLE()));
            }
        }
        if (condition.equals().length > 1) {
            text = text.append(Texts.literal("]").withStyle(ChatFormatting.DARK_PURPLE()));
        }
        return text;
    }
}

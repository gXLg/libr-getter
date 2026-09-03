package dev.gxlg.librgetter.utils.messages.translatable.config;

import dev.gxlg.librgetter.savefiles.config.types.helpers.Configurable;
import dev.gxlg.librgetter.utils.messages.objects.configScreen.OnlyEffectiveConditionMessage;
import dev.gxlg.librgetter.utils.messages.translatable.TranslatableMessage;
import dev.gxlg.librgetter.utils.messages.translatable.config.description.TranslatableOnlyEffectiveText;
import dev.gxlg.librgetter.utils.messages.translatable.config.description.TranslatableWhenInstalledText;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

public class TranslatableConfigDescription extends TranslatableMessage {
    private final Configurable.Description description;

    public TranslatableConfigDescription(Configurable<?> configurable) {
        super("librgetter.config." + configurable.config().getId());
        this.description = configurable.getDescription();
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = super.buildComponent();
        if (description.conditions().isEmpty() && description.compatibility() == null) {
            return text;
        }
        text = text.append("\n\n");
        text = text.append(new TranslatableOnlyEffectiveText().getComponent());
        for (Configurable.OnlyEffectiveCondition condition : description.conditions()) {
            text = text.append("\n* ").append(new OnlyEffectiveConditionMessage(condition).getComponent());
        }
        if (description.compatibility() != null) {
            text = text.append("\n* ").append(new TranslatableWhenInstalledText(description.compatibility()).getComponent());
        }
        return text;
    }
}

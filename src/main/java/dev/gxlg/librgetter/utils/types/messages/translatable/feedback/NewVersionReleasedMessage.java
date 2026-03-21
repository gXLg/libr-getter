package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

import dev.gxlg.librgetter.utils.Updater;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;

public class NewVersionReleasedMessage extends TranslatableFeedbackMessage {
    private final String changelog;

    public NewVersionReleasedMessage(Updater.NewVersion newVersion) {
        super("librgetter.feedback.version", newVersion.version());
        this.changelog = newVersion.changelog();
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = super.buildComponent();
        Style style = Style.EMPTY().withHoverEvent(Texts.hoverable(Texts.literal(changelog)));
        return text.withStyle(style);
    }
}

package dev.gxlg.librgetter.utils.types.exceptions.signals;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStoppedMessage;

public class StopCyclingSignal extends FinishSignal {
    @Override
    protected void notifyPlayer() {
        Texts.sendTranslatable(new ProcessStoppedMessage());
    }
}

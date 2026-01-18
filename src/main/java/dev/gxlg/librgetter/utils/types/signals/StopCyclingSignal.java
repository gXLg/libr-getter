package dev.gxlg.librgetter.utils.types.signals;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.translatable_messages.feedback.ProcessStoppedMessage;

public class StopCyclingSignal extends FinishSignal {
    @Override
    protected void notifyPlayer() {
        Texts.getImpl().sendTranslatable(new ProcessStoppedMessage());
    }
}

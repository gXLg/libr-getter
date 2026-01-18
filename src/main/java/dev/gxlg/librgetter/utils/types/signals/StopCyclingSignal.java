package dev.gxlg.librgetter.utils.types.signals;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;

public class StopCyclingSignal extends FinishSignal {
    @Override
    protected void sendFeedbackToPlayer() {
        Texts.getImpl().sendTranslatableWarning("librgetter.stop");
    }
}

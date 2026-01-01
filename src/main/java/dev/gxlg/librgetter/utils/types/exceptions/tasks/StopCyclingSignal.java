package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;

public class StopCyclingSignal extends FinishSignal {
    public StopCyclingSignal() {
        Texts.getImpl().sendTranslatableWarning("librgetter.stop");
    }
}

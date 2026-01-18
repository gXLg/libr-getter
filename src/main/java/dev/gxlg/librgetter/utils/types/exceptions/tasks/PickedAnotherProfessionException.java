package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.PickedAnotherProfessionMessage;

public class PickedAnotherProfessionException extends LibrGetterException {
    public PickedAnotherProfessionException() {
        super(new PickedAnotherProfessionMessage());
    }
}

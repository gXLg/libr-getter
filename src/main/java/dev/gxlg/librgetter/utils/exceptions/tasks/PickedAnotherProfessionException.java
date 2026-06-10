package dev.gxlg.librgetter.utils.exceptions.tasks;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.PickedAnotherProfessionMessage;

public class PickedAnotherProfessionException extends LibrGetterException {
    public PickedAnotherProfessionException() {
        super(new PickedAnotherProfessionMessage());
    }
}

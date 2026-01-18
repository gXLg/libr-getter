package dev.gxlg.librgetter.utils.types.exceptions.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.PickedAnotherProfessionMessage;

public class PickedAnotherProfessionException extends LibrGetterException {
    public PickedAnotherProfessionException() {
        super(new PickedAnotherProfessionMessage());
    }
}

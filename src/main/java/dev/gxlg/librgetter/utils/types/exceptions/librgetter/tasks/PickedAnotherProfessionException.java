package dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.PickedAnotherProfessionMessage;

public class PickedAnotherProfessionException extends LibrGetterException {
    public PickedAnotherProfessionException() {
        super(new PickedAnotherProfessionMessage());
    }
}

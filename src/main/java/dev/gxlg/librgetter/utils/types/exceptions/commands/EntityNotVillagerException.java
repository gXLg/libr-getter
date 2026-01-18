package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.EntityNotVillagerMessage;

public class EntityNotVillagerException extends LibrGetterException {
    public EntityNotVillagerException() {
        super(new EntityNotVillagerMessage());
    }
}

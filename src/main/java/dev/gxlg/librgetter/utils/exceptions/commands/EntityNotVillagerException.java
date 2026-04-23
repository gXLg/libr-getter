package dev.gxlg.librgetter.utils.exceptions.commands;

import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.messages.translatable.error.EntityNotVillagerMessage;

public class EntityNotVillagerException extends LibrGetterException {
    public EntityNotVillagerException() {
        super(new EntityNotVillagerMessage());
    }
}

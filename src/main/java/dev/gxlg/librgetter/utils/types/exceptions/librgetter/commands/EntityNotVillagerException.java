package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.EntityNotVillagerMessage;

public class EntityNotVillagerException extends LibrGetterException {
    public EntityNotVillagerException() {
        super(new EntityNotVillagerMessage());
    }
}

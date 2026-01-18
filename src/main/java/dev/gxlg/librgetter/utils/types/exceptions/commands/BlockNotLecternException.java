package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.translatable_messages.error.BlockNotLecternMessage;

public class BlockNotLecternException extends LibrGetterException {
    public BlockNotLecternException() {
        super(new BlockNotLecternMessage());
    }
}

package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.error.BlockNotLecternMessage;

public class BlockNotLecternException extends LibrGetterException {
    public BlockNotLecternException() {
        super(new BlockNotLecternMessage());
    }
}

package dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands;

import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.BlockNotLecternMessage;

public class BlockNotLecternException extends LibrGetterException {
    public BlockNotLecternException() {
        super(new BlockNotLecternMessage());
    }
}

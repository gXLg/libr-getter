package dev.gxlg.librgetter.utils.types.exceptions.runtime;

public class InvalidBookIndexException extends RuntimeException {
    public InvalidBookIndexException(int index) {
        super(RuntimeExceptionMessages.INVALID_BOOK_INDEX + ": " + index);
    }
}

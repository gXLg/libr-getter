package dev.gxlg.librgetter.utils.types.exceptions.runtime;

public enum RuntimeExceptionMessages {
    UNEXPECTED_CONFIGURABLE("Unexpected configurable type encountered"),
    UNCATEGORIZED_CONFIG("Uncategorized config declaration"),
    INVALID_BOOK_INDEX("Invalid index for book page");

    private final String message;

    RuntimeExceptionMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

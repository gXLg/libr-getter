package dev.gxlg.librgetter.utils.types.exceptions.runtime;

public enum RuntimeExceptionMessages {
    UNEXPECTED_CONFIGURABLE("Unexpected configurable type encountered"),
    UNCATEGORIZED_CONFIG("Uncategorized config declaration");

    private final String message;

    RuntimeExceptionMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

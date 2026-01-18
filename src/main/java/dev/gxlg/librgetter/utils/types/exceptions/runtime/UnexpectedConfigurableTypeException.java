package dev.gxlg.librgetter.utils.types.exceptions.runtime;

public class UnexpectedConfigurableTypeException extends IllegalArgumentException {
    public UnexpectedConfigurableTypeException(Class<?> type) {
        super(RuntimeExceptionMessages.UNEXPECTED_CONFIGURABLE + ": " + type.getName());
    }
}

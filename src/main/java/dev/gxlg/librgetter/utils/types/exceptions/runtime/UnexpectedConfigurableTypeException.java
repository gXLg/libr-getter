package dev.gxlg.librgetter.utils.types.exceptions.runtime;

public class UnexpectedConfigurableTypeException extends IllegalArgumentException {
    public UnexpectedConfigurableTypeException(Class<?> type) {
        super("Unexpected configurable type encountered: " + type.getName());
    }
}

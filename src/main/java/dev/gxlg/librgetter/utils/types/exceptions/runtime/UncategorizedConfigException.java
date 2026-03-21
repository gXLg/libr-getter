package dev.gxlg.librgetter.utils.types.exceptions.runtime;

public class UncategorizedConfigException extends RuntimeException {
    public UncategorizedConfigException(String configName) {
        super(RuntimeExceptionMessages.UNCATEGORIZED_CONFIG + ": " + configName);
    }
}

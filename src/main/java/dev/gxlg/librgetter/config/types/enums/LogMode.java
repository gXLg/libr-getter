package dev.gxlg.librgetter.config.types.enums;

import dev.gxlg.librgetter.config.types.OptionsConfig;

public enum LogMode implements OptionsConfig<LogMode> {
    NONE,
    CHAT,
    ACTIONBAR;

    @Override
    public LogMode[] getValues() {
        return LogMode.values();
    }
}

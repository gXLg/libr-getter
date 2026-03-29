package dev.gxlg.librgetter.utils.types.config.enums;

import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

public enum LogMode implements OptionsConfig<LogMode> {
    NONE,
    CHAT,
    ACTIONBAR;

    @Override
    public LogMode[] getValues() {
        return LogMode.values();
    }
}

package dev.gxlg.librgetter.utils.types.config.enums;

import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

public enum MatchMode implements OptionsConfig<MatchMode> {
    VANILLA, ALL, LIST, ANY;

    @Override
    public MatchMode[] getValues() {
        return MatchMode.values();
    }
}
package dev.gxlg.librgetter.config.types.enums;

import dev.gxlg.librgetter.config.types.OptionsConfig;

public enum RotationMode implements OptionsConfig<RotationMode> {
    NONE,
    INSTANT,
    SMOOTH;

    @Override
    public RotationMode[] getValues() {
        return RotationMode.values();
    }
}

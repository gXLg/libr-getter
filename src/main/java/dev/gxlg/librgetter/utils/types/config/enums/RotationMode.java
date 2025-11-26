package dev.gxlg.librgetter.utils.types.config.enums;

import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

public enum RotationMode implements OptionsConfig<RotationMode> {
    NONE, INSTANT, SMOOTH;

    @Override
    public RotationMode[] getValues() {
        return RotationMode.values();
    }
}

package dev.gxlg.librgetter.compatibility;

import dev.gxlg.librgetter.utils.chaining.compatibility.Compatibility;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;

public class CompatibilityManager {
    private final ConfigManager configManager;

    public CompatibilityManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public boolean isUsingTradeCycling() {
        return Compatibility.isModPresent("trade_cycling") && configManager.getBoolean(Config.TRADE_CYCLING);
    }
}

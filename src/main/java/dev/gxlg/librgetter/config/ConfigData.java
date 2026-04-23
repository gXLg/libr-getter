package dev.gxlg.librgetter.config;

import dev.gxlg.librgetter.config.types.CanNotChangeWhileRunning;
import dev.gxlg.librgetter.config.types.CompatibilityWith;
import dev.gxlg.librgetter.config.types.ConfigCategory;
import dev.gxlg.librgetter.config.types.IntRange;
import dev.gxlg.librgetter.config.types.OnlyEffective;
import dev.gxlg.librgetter.config.types.enums.LogMode;
import dev.gxlg.librgetter.config.types.enums.MatchMode;
import dev.gxlg.librgetter.config.types.enums.RotationMode;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unused", "FieldMayBeFinal", "UnusedReturnValue" })
public class ConfigData {
    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean autoTool = true;

    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean offhand = false;

    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean manual = false;

    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private RotationMode rotationMode = RotationMode.INSTANT;

    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean waitLose = false;

    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean safeChecker = true;

    @IntRange(min = 0, max = 20)
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private int timeout = 0;

    @ConfigCategory(ConfigManager.Category.SUCCESS)
    private boolean notify = false;

    @ConfigCategory(ConfigManager.Category.SUCCESS)
    private boolean removeGoal = false;

    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @ConfigCategory(ConfigManager.Category.SUCCESS)
    private boolean lock = false;

    @ConfigCategory(ConfigManager.Category.MESSAGES)
    private LogMode logMode = LogMode.CHAT;

    @ConfigCategory(ConfigManager.Category.MESSAGES)
    private boolean warning = true;

    @ConfigCategory(ConfigManager.Category.MESSAGES)
    private boolean checkUpdate = true;

    @ConfigCategory(ConfigManager.Category.MATCHING)
    private boolean fallback = false;

    @NotNull
    @ConfigCategory(ConfigManager.Category.MATCHING)
    private MatchMode matchMode = MatchMode.VANILLA;

    @OnlyEffective(when = Config.MATCH_MODE, equals = "ATLEAST")
    @IntRange(min = 1)
    @ConfigCategory(ConfigManager.Category.MATCHING)
    private int matchAtLeast = 1;

    @CompatibilityWith("trade_cycling")
    @CanNotChangeWhileRunning
    @ConfigCategory(ConfigManager.Category.COMPATIBILITY)
    private boolean tradeCycling = false;
}

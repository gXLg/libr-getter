package dev.gxlg.librgetter.utils.config;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
import dev.gxlg.librgetter.utils.types.config.ConfigCategory;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.OnlyEffective;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.config.enums.RotationMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CanBeFinal")
public class ConfigData {
    @OnlyEffective(when = "manual", equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    public boolean autoTool = true;

    @OnlyEffective(when = "manual", equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    public boolean offhand = false;

    @ConfigCategory(ConfigManager.Category.PROCESS)
    public boolean manual = false;

    @OnlyEffective(when = "manual", equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    public RotationMode rotationMode = RotationMode.INSTANT;

    @ConfigCategory(ConfigManager.Category.PROCESS)
    public boolean waitLose = false;

    @ConfigCategory(ConfigManager.Category.PROCESS)
    public boolean safeChecker = true;

    @IntRange(min = 0, max = 20)
    @ConfigCategory(ConfigManager.Category.PROCESS)
    public int timeout = 0;

    @ConfigCategory(ConfigManager.Category.SUCCESS)
    public boolean notify = false;

    @ConfigCategory(ConfigManager.Category.SUCCESS)
    public boolean removeGoal = false;

    @OnlyEffective(when = "manual", equals = "false")
    @ConfigCategory(ConfigManager.Category.SUCCESS)
    public boolean lock = false;

    @ConfigCategory(ConfigManager.Category.MESSAGES)
    public LogMode logMode = LogMode.CHAT;

    @ConfigCategory(ConfigManager.Category.MESSAGES)
    public boolean warning = true;

    @ConfigCategory(ConfigManager.Category.MESSAGES)
    public boolean checkUpdate = true;

    @ConfigCategory(ConfigManager.Category.MATCHING)
    public boolean fallback = false;

    @NotNull
    @ConfigCategory(ConfigManager.Category.MATCHING)
    public MatchMode matchMode = MatchMode.VANILLA;

    @OnlyEffective(when = "matchMode", equals = "ATLEAST")
    @IntRange(min = 1)
    @ConfigCategory(ConfigManager.Category.MATCHING)
    public int matchAtLeast = 1;

    @Compatibility("trade_cycling")
    @ConfigCategory(ConfigManager.Category.COMPATIBILITY)
    public boolean tradeCycling = false;

    public List<EnchantmentTrade> goals = new ArrayList<>();
}

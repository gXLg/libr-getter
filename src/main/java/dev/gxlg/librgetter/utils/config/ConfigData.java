package dev.gxlg.librgetter.utils.config;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.CanNotChangeWhileRunning;
import dev.gxlg.librgetter.utils.types.config.CompatibilityWith;
import dev.gxlg.librgetter.utils.types.config.ConfigCategory;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.OnlyEffective;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.config.enums.RotationMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "unused", "FieldMayBeFinal", "UnusedReturnValue" })
public class ConfigData {
    private final List<EnchantmentTrade> goals = new ArrayList<>();

    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean autoTool = true;

    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean offhand = false;

    @ConfigCategory(ConfigManager.Category.PROCESS)
    private boolean manual = false;

    @OnlyEffective(when = Config.MANUAL, equals = "false")
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
    @ConfigCategory(ConfigManager.Category.PROCESS)
    private RotationMode rotationMode = RotationMode.INSTANT;

    @ConfigCategory(ConfigManager.Category.PROCESS)
    @OnlyEffective(when = Config.TRADE_CYCLING, equals = "false")
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

    public List<EnchantmentTrade> getGoals() {
        return List.copyOf(goals);
    }

    public boolean addGoal(EnchantmentTrade goal) {
        return goals.add(goal);
    }

    public boolean removeGoal(EnchantmentTrade goal) {
        return goals.remove(goal);
    }

    public boolean removeMatchingGoal(EnchantmentTrade trade) {
        for (EnchantmentTrade goal : goals) {
            if (trade.same(goal)) {
                return goals.remove(goal);
            }
        }
        return false;
    }

    public void clearGoals() {
        goals.clear();
    }
}

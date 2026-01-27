package dev.gxlg.librgetter.utils.config;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
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
    public boolean notify = false;

    @OnlyEffective(when = "manual", equals = "false")
    public boolean autoTool = true;

    public LogMode logMode = LogMode.CHAT;

    @OnlyEffective(when = "manual", equals = "false")
    public boolean lock = false;

    public boolean removeGoal = false;

    public boolean checkUpdate = true;

    public boolean warning = true;

    @OnlyEffective(when = "manual", equals = "false")
    public boolean offhand = false;

    public boolean manual = false;

    public boolean waitLose = false;

    public boolean safeChecker = true;

    public boolean fallback = false;

    @OnlyEffective(when = "manual", equals = "false")
    public RotationMode rotationMode = RotationMode.INSTANT;

    @IntRange(min = 0, max = 20)
    public int timeout = 0;

    @NotNull
    public MatchMode matchMode = MatchMode.VANILLA;

    @OnlyEffective(when = "matchMode", equals = "ATLEAST")
    @IntRange(min = 1)
    public int matchAtLeast = 1;

    @Compatibility("trade_cycling")
    public boolean tradeCycling = false;

    public List<EnchantmentTrade> goals = new ArrayList<>();
}

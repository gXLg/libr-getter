package dev.gxlg.librgetter.utils.config;

public enum Config {
    AUTO_TOOL("autoTool"),
    OFFHAND("offhand"),
    MANUAL("manual"),
    ROTATION_MODE("rotationMode"),
    WAIT_LOSE("waitLose"),
    SAFE_CHECKER("safeChecker"),
    TIMEOUT("timeout"),
    NOTIFY("notify"),
    REMOVE_GOAL("removeGoal"),
    LOCK("lock"),
    LOG_MODE("logMode"),
    WARNING("warning"),
    CHECK_UPDATE("checkUpdate"),
    FALLBACK("fallback"),
    MATCH_MODE("matchMode"),
    MATCH_AT_LEAST("matchAtLeast"),
    TRADE_CYCLING("tradeCycling");

    private final String id;

    Config(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

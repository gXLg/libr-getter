package dev.gxlg.librgetter.worker.types.task;

public record PermissionManager(boolean allowsBreakingLecterns, boolean allowsPlacingLectern, boolean allowsSettingTradeOffers, boolean allowsOpeningScreen) {
    public static PermissionManager DEFAULT = new PermissionManager(false, false, false, false);
}

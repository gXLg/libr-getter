package dev.gxlg.librgetter.worker.types.task;

public record PermissionView(boolean allowsBreakingLecterns, boolean allowsPlacingLectern, boolean allowsSettingTradeOffers, boolean allowsOpeningScreen, boolean forcesSecondaryUse) {
    public static PermissionView DEFAULT = new PermissionView(false, false, false, false, false);
}

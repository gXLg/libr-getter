package dev.gxlg.librgetter.worker.types.task;

public record PermissionManager(boolean allowsBreakingLecterns, boolean allowsPlacingLectern, boolean allowsSettingTradeOffers) {
    public static PermissionManager DEFAULT = new PermissionManager(false, false, false);
}

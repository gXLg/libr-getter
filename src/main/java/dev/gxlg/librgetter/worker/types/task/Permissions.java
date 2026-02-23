package dev.gxlg.librgetter.worker.types.task;

public record Permissions(boolean allowsBreakingLecterns, boolean allowsPlacingLectern, boolean allowsSettingTradeOffers) {
    public static Permissions DEFAULT = new Permissions(false, false, false);
}

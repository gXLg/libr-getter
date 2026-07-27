package dev.gxlg.librgetter.worker.types.task;

public record PermissionView(boolean allowsBreakingLecterns, boolean allowsPlacingLectern, boolean allowsSettingTradeOffers, boolean allowsOpeningScreen, boolean forcesSecondaryUse, boolean disablesBlockBreakStopping) {
    // forcesSecondaryUse         - pretends to press the shift key to allow placing on top of clickable blocks
    // disablesBlockBreakStopping - on gameTickEnd if !attackButton.isPressed() -> stopBreakBlock() called, when we break block: prevent
    public static final PermissionView DEFAULT = new PermissionView(false, false, false, false, false, false);
}

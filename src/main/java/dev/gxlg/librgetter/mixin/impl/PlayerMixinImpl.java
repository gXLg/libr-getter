package dev.gxlg.librgetter.mixin.impl;

import dev.gxlg.librgetter.worker.state.StateView;

import java.util.Optional;

public class PlayerMixinImpl {
    private final StateView stateView;

    public PlayerMixinImpl(StateView stateView) {
        this.stateView = stateView;
    }

    public Optional<Boolean> isSecondaryUseActive() {
        if (stateView.createPermissionView().forcesSecondaryUse()) {
            return Optional.of(true);
        }
        return Optional.empty();
    }
}

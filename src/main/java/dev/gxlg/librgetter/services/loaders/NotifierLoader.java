package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

public class NotifierLoader extends ServiceLoader<NotifierLoader> {
    public static final Export<NotifierLoader, Notifier> exportNotifier = new Export<>(n -> n.notifier);

    private Notifier notifier;

    @Override
    public void init() {
        notifier = new Notifier();
    }
}

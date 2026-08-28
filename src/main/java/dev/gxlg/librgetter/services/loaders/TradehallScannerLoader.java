package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallScanner;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class TradehallScannerLoader extends ServiceLoader<TradehallScannerLoader> {
    private final Supplier<Notifier> dependencyNotifier;

    private final Supplier<TradehallManager> dependencyTradehallManager;

    private final Supplier<ConfigManager> dependencyConfigManager;

    @SuppressWarnings({ "unused", "FieldCanBeLocal" })
    private TradehallScanner tradehallScanner;

    public TradehallScannerLoader(NotifierLoader notifierLoader, SaveFileLoader saveFileLoader) {
        dependencyNotifier = initDependency(notifierLoader, NotifierLoader.exportNotifier);
        dependencyTradehallManager = initDependency(saveFileLoader, SaveFileLoader.exportTradehallManager);
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
    }

    @Override
    public void init() {
        Notifier notifier = dependencyNotifier.get();
        TradehallManager tradehallManager = dependencyTradehallManager.get();
        ConfigManager configManager = dependencyConfigManager.get();
        tradehallScanner = new TradehallScanner(notifier, tradehallManager, configManager);
    }
}

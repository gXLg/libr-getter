package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallScanner;
import dev.gxlg.librgetter.services.ServiceLoader;

import java.util.function.Supplier;

public class TradehallScannerLoader extends ServiceLoader<TradehallScannerLoader> {
    private final Supplier<TradehallAccessor> dependencyTradehallAccessor;

    private final Supplier<ConfigManager> dependencyConfigManager;

    public TradehallScannerLoader(SaveFileLoader saveFileLoader) {
        dependencyTradehallAccessor = initDependency(saveFileLoader, SaveFileLoader.exportTradehallAccessor);
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
    }

    @Override
    public void init() {
        TradehallAccessor tradehallAccessor = dependencyTradehallAccessor.get();
        ConfigManager configManager = dependencyConfigManager.get();
        TradehallScanner tradehallScanner = new TradehallScanner(tradehallAccessor, configManager);
        tradehallScanner.start();
    }
}

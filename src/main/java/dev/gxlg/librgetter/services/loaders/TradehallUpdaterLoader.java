package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAutoSaver;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallScanner;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

import java.util.function.Supplier;

public class TradehallUpdaterLoader extends ServiceLoader<TradehallUpdaterLoader> {
    public static final Export<TradehallUpdaterLoader, TradehallAutoSaver> exportTradehallAutoSaver = new Export<>(c -> c.tradehallAutoSaver);

    private final Supplier<ConfigManager> dependencyConfigManager;

    private final Supplier<GoalListAccessor> dependencyGoalListAccessor;

    private final Supplier<TradehallAccessor> dependencyTradehallAccessor;

    private TradehallAutoSaver tradehallAutoSaver;

    public TradehallUpdaterLoader(SaveFileLoader saveFileLoader) {
        dependencyConfigManager = initDependency(saveFileLoader, SaveFileLoader.exportConfigManager);
        dependencyGoalListAccessor = initDependency(saveFileLoader, SaveFileLoader.exportGoalListAccessor);
        dependencyTradehallAccessor = initDependency(saveFileLoader, SaveFileLoader.exportTradehallAccessor);
    }

    @Override
    public void init() {
        ConfigManager configManager = dependencyConfigManager.get();
        GoalListAccessor goalListAccessor = dependencyGoalListAccessor.get();
        TradehallAccessor tradehallAccessor = dependencyTradehallAccessor.get();

        TradehallScanner tradehallScanner = new TradehallScanner(configManager, tradehallAccessor);
        tradehallScanner.start();

        tradehallAutoSaver = new TradehallAutoSaver(configManager, goalListAccessor, tradehallAccessor);
        tradehallAutoSaver.start();
    }
}

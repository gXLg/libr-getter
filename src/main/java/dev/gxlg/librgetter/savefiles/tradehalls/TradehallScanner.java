package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.TickUtil;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Block;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;

public class TradehallScanner {
    public static final int SCAN_INTERVAL_TICKS = 20 * 60;

    public static final int SCAN_RADIUS = 64;

    private final TradehallAccessor tradehallAccessor;

    private final ConfigManager configManager;

    private int ticksSinceLastScan = 0;

    public TradehallScanner(TradehallAccessor tradehallAccessor, ConfigManager configManager) {
        this.tradehallAccessor = tradehallAccessor;
        this.configManager = configManager;
    }

    public void start() {
        TickUtil.registerLevelTicker(this::tick);
    }

    private void tick(ClientLevel level) {
        if (!configManager.getBoolean(Config.TRADEHALL_SCAN)) {
            return;
        }
        if (ticksSinceLastScan < SCAN_INTERVAL_TICKS) {
            ticksSinceLastScan++;
            return;
        }
        ticksSinceLastScan = 0;
        scan(level);
    }

    private void scan(ClientLevel level) {
        LocalPlayer player = Minecraft.getInstance().getPlayerField();
        if (player == null) {
            return;
        }
        TradehallManager tradehallManager = tradehallAccessor.createAccessForCurrentManager();
        BlockPos center = player.blockPosition();
        WorkstationList workstations = tradehallManager.getWorkstations();
        workstations = workstations.filterWorkstations(center, SCAN_RADIUS);
        for (WorkstationList.Workstation workstation : workstations) {
            BlockPos pos = workstation.getPosition().toBlockPos();
            Block block = level.getBlockState(pos).getBlock();
            if (block.equals(Blocks.LECTERN())) {
                if (PathFinding.searchForEntity(level, pos, 4.0, PathFinding.DEFAULT_LIBRARIAN_PREDICATE) != null) {
                    continue;
                }
            }
            tradehallManager.removeWorkstation(pos);
        }
        tradehallManager.save();
    }
}

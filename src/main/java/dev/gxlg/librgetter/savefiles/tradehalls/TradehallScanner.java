package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents$EndTickI;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Block;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TradehallScanner {
    public static final int SCAN_INTERVAL_TICKS = 20 * 60;

    public static final int SCAN_RADIUS = 64;

    private final TradehallAccessor tradehallAccessor;

    private final ConfigManager configManager;

    private int ticksSinceLastScan = 0;

    public TradehallScanner(TradehallAccessor tradehallAccessor, ConfigManager configManager) {
        this.tradehallAccessor = tradehallAccessor;
        this.configManager = configManager;

        ClientTickEvents$EndTickI endTick = this::tick;
        ClientTickEvents.END_CLIENT_TICK.register(endTick.unwrap(ClientTickEvents.EndTick.class));
    }

    private void tick(Minecraft client) {
        if (!configManager.getBoolean(Config.TRADEHALL_SCAN)) {
            return;
        }
        if (ticksSinceLastScan < SCAN_INTERVAL_TICKS) {
            ticksSinceLastScan++;
            return;
        }
        ticksSinceLastScan = 0;
        scan(client);
    }

    private void scan(Minecraft client) {
        LocalPlayer player = client.getPlayerField();
        ClientLevel level = client.getLevelField();
        if (player == null || level == null) {
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

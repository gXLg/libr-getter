package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.MatchUtil;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.TickUtil;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TradehallAutoSaver {
    private final ConfigManager configManager;

    private final GoalListAccessor goalListAccessor;

    private final TradehallAccessor tradehallAccessor;

    private final Queue<Villager> villagers = new LinkedBlockingQueue<>();

    private final Queue<TradehallEntry> entries = new LinkedBlockingQueue<>();

    public TradehallAutoSaver(ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor) {
        this.configManager = configManager;
        this.goalListAccessor = goalListAccessor;
        this.tradehallAccessor = tradehallAccessor;
    }

    public void start() {
        TickUtil.registerLevelTicker(this::tick);
    }

    public void addVillager(Villager villager) {
        villagers.add(villager);
    }

    public void registerOffers(List<MerchantOffer> merchantOffers, boolean traded) {
        if (villagers.isEmpty()) {
            return;
        }
        Villager villager = villagers.remove();
        if (!traded || merchantOffers.isEmpty()) {
            return;
        }
        entries.add(new TradehallEntry(villager, merchantOffers));
    }

    private void tick(ClientLevel level) {
        GoalListManager goalListManager = goalListAccessor.createAccessForCurrentManager();
        TradehallManager tradehallManager = tradehallAccessor.createAccessForCurrentManager();
        boolean dirty = false;

        while (!entries.isEmpty()) {
            TradehallEntry entry = entries.remove();
            List<EnchantmentTrade> parsed;
            try {
                parsed = MatchUtil.parseTrades(entry.merchantOffers(), configManager, goalListManager);
            } catch (LibrGetterException e) {
                return;
            }
            BlockPos lecternPos = PathFinding.searchForBlock(level, entry.villager.blockPosition(), PathFinding.DEFAULT_LECTERN_VILLAGER_DISTANCE, Blocks.LECTERN(), p -> true);
            if (lecternPos == null) {
                continue;
            }
            tradehallManager.addOrUpdateWorkstation(lecternPos, parsed);
            dirty = true;
        }
        if (dirty) {
            tradehallManager.save();
        }
    }

    private record TradehallEntry(Villager villager, List<MerchantOffer> merchantOffers) { }
}

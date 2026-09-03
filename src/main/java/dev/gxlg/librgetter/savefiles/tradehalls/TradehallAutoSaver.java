package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.MatchUtil;
import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.TickUtil;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TradehallAutoSaver {
    private final ConfigManager configManager;

    private final GoalListAccessor goalListAccessor;

    private final TradehallAccessor tradehallAccessor;

    private final Queue<TimedEntry<Villager>> villagers = new LinkedBlockingQueue<>();

    private final Queue<TimedEntry<TradeOfferData>> tradeOffers = new LinkedBlockingQueue<>();

    public TradehallAutoSaver(ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor) {
        this.configManager = configManager;
        this.goalListAccessor = goalListAccessor;
        this.tradehallAccessor = tradehallAccessor;
    }

    public void start() {
        TickUtil.registerLevelTicker(this::tick);
    }

    public void addVillager(Villager villager) {
        villagers.add(new TimedEntry<>(villager));
    }

    public void addTradeOffers(TradeOfferData tradeOfferData) {
        tradeOffers.add(new TimedEntry<>(tradeOfferData));
    }

    private void tick(ClientLevel level) {
        if (!villagers.isEmpty()) {
            TimedEntry<Villager> timedVillager = villagers.element();
            if (timedVillager.isExpired()) {
                villagers.remove();
            } else {
                timedVillager.tick();
            }
        }
        if (!tradeOffers.isEmpty()) {
            TimedEntry<TradeOfferData> timedTradeOffer = tradeOffers.element();
            if (timedTradeOffer.isExpired()) {
                tradeOffers.remove();
            } else {
                timedTradeOffer.tick();
            }
        }
        if (villagers.isEmpty() || tradeOffers.isEmpty()) {
            return;
        }
        Villager villager = villagers.remove().getData();
        TradeOfferData tradeOfferData = tradeOffers.remove().getData();
        GoalListManager goalListManager = goalListAccessor.createAccessForCurrentManager();

        List<EnchantmentTrade> parsed;
        try {
            parsed = MatchUtil.parseTrades(tradeOfferData.getTradeOfferList(), configManager, goalListManager);
        } catch (LibrGetterException e) {
            return;
        }
        BlockPos lecternPos = PathFinding.searchForBlock(level, villager.blockPosition(), PathFinding.DEFAULT_LECTERN_VILLAGER_DISTANCE, Blocks.LECTERN(), p -> true);
        if (lecternPos == null) {
            return;
        }
        TradehallManager tradehallManager = tradehallAccessor.createAccessForCurrentManager();
        tradehallManager.addOrUpdateWorkstation(lecternPos, parsed);
        tradehallManager.save();
    }

    private static final int ENTRY_EXPIRATION_TICKS = 20 * 2; // 2 seconds

    private static class TimedEntry<T> {
        private final T data;

        private int timer = 0;

        public TimedEntry(T data) {
            this.data = data;
        }

        public void tick() {
            timer++;
        }

        public boolean isExpired() {
            return timer > ENTRY_EXPIRATION_TICKS;
        }

        public T getData() {
            return data;
        }
    }
}

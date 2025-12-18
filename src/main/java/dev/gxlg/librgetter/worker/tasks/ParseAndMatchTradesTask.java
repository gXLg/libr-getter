package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.ParsedEnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

import java.util.*;

public class ParseAndMatchTradesTask extends Worker.Task {
    private final TradeOfferList offers;

    public ParseAndMatchTradesTask(Worker.TaskContext taskContext, TradeOfferList trades) {
        super(taskContext);
        this.offers = trades;
    }

    @Override
    public Worker.TaskSwitch work() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");

        List<EnchantmentTrade> offeredEnchantments = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            if (i >= 2 && LibrGetter.config.matchMode == MatchMode.VANILLA) break;
            if (!isEnchantmentTrade(offers.get(i))) continue;

            ParsedEnchantmentTrade parsed = Minecraft.parseTrade(offers, i);
            if (parsed.isError()) {
                String[] ret = parsed.getError();
                Object[] args = new String[ret.length - 1];
                System.arraycopy(ret, 1, args, 0, ret.length - 1);
                return error(ret[0], (String[]) args);
            }
            offeredEnchantments.add(parsed.getTrade());
            if (LibrGetter.config.matchMode == MatchMode.VANILLA) break;
        }
        Texts.getImpl().sendTradeLog(offeredEnchantments);
        MatchMode.GoalMatching matching = LibrGetter.config.matchMode.match(offeredEnchantments);
        if (!matching.isMatch()) {
            return Support.useTradeCycling() ?
                    switchNextTick(new TradeCyclingClickTask(taskContext)) :
                    switchSameTick(new SelectAxeTask(taskContext));
        }

        Minecraft.playNotification(Minecraft.getWorld(player), player);
        matching.getMatchedEnchantments().forEach(e ->
                Texts.getImpl().sendFound(e, taskContext.attemptsCounter())
        );

        if (LibrGetter.config.removeGoal) {
            matching.getMatchedEnchantments().forEach(e -> CommandHelper.removeGoal(e.id(), e.lvl()));
        }

        return switchSameTick(new FinalizeSearchTask(taskContext, offers));
    }

    @Override
    public boolean allowsBreaking() {
        return false;
    }

    private boolean isEnchantmentTrade(TradeOffer offer) {
        return offer.getSellItem().isOf(Items.ENCHANTED_BOOK) || offer.getSellItem().isOf(Items.BOOK);
    }
}

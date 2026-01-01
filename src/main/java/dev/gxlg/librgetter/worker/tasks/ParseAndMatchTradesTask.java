package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.CommandHelper;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.ParsedEnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.TaskException;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

import java.util.ArrayList;
import java.util.List;

public class ParseAndMatchTradesTask extends TaskManager.Task {
    private final TradeOfferList offers;

    public ParseAndMatchTradesTask(TradeOfferList trades) {
        this.offers = trades;
    }

    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) throw new InternalTaskException("player", this);

        List<EnchantmentTrade> offeredEnchantments = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            if (i >= 2 && LibrGetter.config.matchMode == MatchMode.VANILLA) break;
            if (!isEnchantmentTrade(offers.get(i))) continue;

            ParsedEnchantmentTrade parsed = Minecraft.parseTrade(offers, i);
            if (parsed.isError()) {
                String[] ret = parsed.getError();
                Object[] args = new String[ret.length - 1];
                System.arraycopy(ret, 1, args, 0, ret.length - 1);
                throw new TaskException(ret[0], (String[]) args);
            }
            offeredEnchantments.add(parsed.getTrade());
            if (LibrGetter.config.matchMode == MatchMode.VANILLA) break;
        }
        Texts.getImpl().sendTradeLog(offeredEnchantments);
        MatchMode.GoalMatching matching = LibrGetter.config.matchMode.match(offeredEnchantments);
        if (!matching.isMatch()) {
            throw new StopTaskSignal(ctx -> Support.isUsingTradeCycling() ?
                    TaskManager.TaskSwitch.nextTick(new TradeCyclingClickTask(), ctx) :
                    TaskManager.TaskSwitch.sameTick(new SelectAxeTask(), ctx)
            );
        }

        Minecraft.playNotification(Minecraft.getWorld(player), player);
        matching.getMatchedEnchantments().forEach(e ->
                Texts.getImpl().sendFound(e, taskContext.attemptsCounter())
        );

        if (LibrGetter.config.removeGoal) {
            matching.getMatchedEnchantments().forEach(e -> CommandHelper.removeGoal(e.id(), e.lvl()));
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new FinalizeSearchTask(offers), ctx));
    }

    private boolean isEnchantmentTrade(TradeOffer offer) {
        return offer.getSellItem().isOf(Items.ENCHANTED_BOOK) || offer.getSellItem().isOf(Items.BOOK);
    }
}

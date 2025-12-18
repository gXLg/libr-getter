package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Nullable;

public class WaitTradesTask extends Worker.Task {
    @Nullable
    private TradeOfferList offers = null;
    private boolean canRefresh = true;

    public WaitTradesTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (!canRefresh) return error("librgetter.update");
        if (offers != null) return switchSameTick(new ParseAndMatchTradesTask(taskContext, offers));
        return noSwitch();
    }

    @Override
    public boolean shouldCloseScreen() {
        return !Support.useTradeCycling();
    }

    public void setOffers(@Nullable TradeOfferList offers) {
        this.offers = offers;
    }

    public void setNoRefresh() {
        canRefresh = false;
    }
}

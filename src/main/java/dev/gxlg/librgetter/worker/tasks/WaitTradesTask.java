package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.LibrarianCanNotUpdateTradesException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;

public class WaitTradesTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) throws LibrGetterException {
        TradeOfferData offerData = taskContext.tradeOfferData();
        if (offerData == null) {
            return;
        }
        if (!offerData.canRefresh()) {
            throw new LibrarianCanNotUpdateTradesException();
        }

        Task parseTask = new ParseAndMatchTradesTask(offerData.getTradeOfferList());
        controller.scheduleContextUpdate(ctx -> ctx.setTradeOfferData(null));
        controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> parseTask));
    }

    @Override
    protected boolean allowsSettingTradeOffers() {
        return true;
    }
}

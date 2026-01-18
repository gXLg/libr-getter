package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.LibrarianCanNotUpdateTradesException;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;

public class WaitTradesTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (taskContext.tradeOfferData() == null) {
            return;
        }
        if (!taskContext.tradeOfferData().canRefresh()) {
            throw new LibrarianCanNotUpdateTradesException();
        }
        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new ParseAndMatchTradesTask(ctx.tradeOfferData().getTradeOfferList()), ctx.withTradeOfferData(null)));
    }
}

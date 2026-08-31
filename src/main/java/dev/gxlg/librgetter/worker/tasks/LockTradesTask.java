package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.goals.GoalListAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallManager;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.MerchantScreen;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.ContainerInput;

import java.util.List;

public class LockTradesTask extends Task {
    private final int offerIndex;

    private final List<EnchantmentTrade> matchedTrades;

    public LockTradesTask(int offer, List<EnchantmentTrade> matchedTrades) {
        this.offerIndex = offer;
        this.matchedTrades = matchedTrades;
    }

    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, GoalListAccessor goalListAccessor, TradehallAccessor tradehallAccessor, CompatibilityManager compatibilityManager) throws InternalErrorException {
        MinecraftData minecraftData = taskContext.minecraftData();

        // wait for the screen to open
        if (!(Gui.getScreen(minecraftData.client) instanceof MerchantScreen)) {
            return;
        }

        LocalPlayer player = minecraftData.localPlayer;

        // wait for the first slot to be updated server-side
        if (player.getContainerMenuField().getSlot(0).getContainerField().getItem(0).isEmpty()) {
            // select the trade
            minecraftData.clientNetwork.send(new ServerboundSelectTradePacket(offerIndex));
            return;
        }
        // confirm the trade
        minecraftData.gameMode.handleContainerInput(player.getContainerMenuField().getContainerIdField(), 2, 0, ContainerInput.PICKUP(), player);
        // close the screen
        Gui.getScreen(minecraftData.client).onClose();
        // save the workstation
        TradehallManager tradehallManager = tradehallAccessor.createAccessForCurrentManager();
        tradehallManager.addOrUpdateWorkstation(taskContext.selectedLecternPos(), matchedTrades);
        tradehallManager.save();

        controller.scheduleTaskSwitch(TaskSwitch.sameTick(FinishTask::new));
    }

    @Override
    protected boolean allowsOpeningScreen() {
        return true;
    }
}

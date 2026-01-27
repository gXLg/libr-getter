package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.InventoryHelper;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.AxeItemWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlocksWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper;

public class SelectAxeTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new BreakLecternTask(), ctx));
        }

        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        InventoryWrapper inventory = player.getInventory();

        int slot = -1;
        if (LibrGetter.config.autoTool) {
            float maxBreakingSpeed = -1;
            for (int i = 0; i < InventoryWrapper.INVENTORY_SIZE(); i++) {
                ItemStackWrapper stack = inventory.getItem(i);
                if (stack.isDamageableItem() && stack.getMaxDamage() - stack.getDamageValue() < 10) {
                    continue;
                }
                float breakingSpeed = stack.getDestroySpeed(BlocksWrapper.LECTERN().defaultBlockState());
                int efficiencyLevel = Enchantments.getImpl().getEfficiencyLevel(stack);
                if (stack.getItem().isInstanceOf(AxeItemWrapper.class)) {
                    breakingSpeed += (float) (efficiencyLevel * efficiencyLevel + 1);
                }
                if (breakingSpeed > maxBreakingSpeed) {
                    maxBreakingSpeed = breakingSpeed;
                    slot = i;
                }
            }
        } else {
            if (taskContext.defaultItem() != null && taskContext.defaultItem().isDamageableItem()) {
                for (int i = 0; i < InventoryWrapper.INVENTORY_SIZE(); i++) {
                    ItemStackWrapper stack = inventory.getItem(i);
                    if (ItemStackWrapper.matches(stack, taskContext.defaultItem())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        if (slot != -1) {
            MultiPlayerGameModeWrapper game = client.getGameModeField();
            if (game == null) {
                throw new InternalErrorException("game");
            }
            ClientPacketListenerWrapper clientNetwork = client.getConnection();
            if (clientNetwork == null) {
                throw new InternalErrorException("clientNetwork");
            }
            InventoryHelper.selectItem(player, slot, game, clientNetwork);
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new RotationTask(player, Vec3Wrapper.atCenterOf(ctx.selectedLecternPos()), new BreakLecternTask()), ctx));
    }
}

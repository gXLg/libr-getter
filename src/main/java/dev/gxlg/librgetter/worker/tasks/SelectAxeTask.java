package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class SelectAxeTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new BreakLecternTask(), ctx));
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }
        Inventory inventory = player.getInventory();

        int slot = -1;
        if (LibrGetter.config.autoTool) {
            float maxBreakingSpeed = -1;
            for (int i = 0; i < Inventory.INVENTORY_SIZE; i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.isDamageableItem() && stack.getMaxDamage() - stack.getDamageValue() < 10) {
                    continue;
                }
                float breakingSpeed = stack.getDestroySpeed(Blocks.LECTERN.defaultBlockState());
                int efficiencyLevel = Enchantments.getImpl().getEfficiencyLevel(stack);
                if (stack.getItem() instanceof AxeItem) {
                    breakingSpeed += (float) (efficiencyLevel * efficiencyLevel + 1);
                }
                if (breakingSpeed > maxBreakingSpeed) {
                    maxBreakingSpeed = breakingSpeed;
                    slot = i;
                }
            }
        } else {
            if (taskContext.defaultItem() != null && taskContext.defaultItem().isDamageableItem()) {
                for (int i = 0; i < Inventory.INVENTORY_SIZE; i++) {
                    ItemStack stack = inventory.getItem(i);
                    if (ItemStack.matches(stack, taskContext.defaultItem())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        if (slot != -1) {
            MultiPlayerGameMode manager = client.gameMode;
            if (manager == null) {
                throw new InternalErrorException("managerInstance");
            }

            ClientPacketListener handler = client.getConnection();
            if (handler == null) {
                throw new InternalErrorException("handler");
            }

            // TODO: extract to method
            if (!Inventory.isHotbarSlot(slot)) {
                int syncId = player.inventoryMenu.containerId;
                int swap = inventory.getSuitableHotbarSlot();
                manager.handleInventoryMouseClick(syncId, slot, swap, ClickType.SWAP, player);
                slot = swap;
            }
            Helper.getImpl().setSelectedSlot(inventory, slot);
            ServerboundSetCarriedItemPacket packetSelect = new ServerboundSetCarriedItemPacket(slot);
            Helper.getImpl().getConnection(handler).send(packetSelect);
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new RotationTask(player, ctx.selectedLecternPos().getCenter(), new BreakLecternTask()), ctx));
    }
}

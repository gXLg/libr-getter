package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class SelectAxeTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new BreakLecternTask(), ctx));
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            throw new InternalTaskException("player", this);
        }
        PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            throw new InternalTaskException("inventory", this);
        }

        int slot = -1;
        if (LibrGetter.config.autoTool) {
            float maxBreakingSpeed = -1;
            for (int i = 0; i < PlayerInventory.MAIN_SIZE; i++) {
                ItemStack stack = inventory.getStack(i);
                if (stack.isDamageable() && stack.getMaxDamage() - stack.getDamage() < 10) {
                    continue;
                }
                float breakingSpeed = stack.getMiningSpeedMultiplier(Blocks.LECTERN.getDefaultState());
                int efficiencyLevel = Minecraft.getEfficiencyLevel(stack);
                if (stack.getItem() instanceof AxeItem) {
                    breakingSpeed += (float) (efficiencyLevel * efficiencyLevel + 1);
                }
                if (breakingSpeed > maxBreakingSpeed) {
                    maxBreakingSpeed = breakingSpeed;
                    slot = i;
                }
            }
        } else {
            if (taskContext.defaultItem() != null && taskContext.defaultItem().isDamageable()) {
                for (int i = 0; i < PlayerInventory.MAIN_SIZE; i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (ItemStack.areEqual(stack, taskContext.defaultItem())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        if (slot != -1) {
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                throw new InternalTaskException("manager", this);
            }

            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                throw new InternalTaskException("handler", this);
            }

            if (!PlayerInventory.isValidHotbarIndex(slot)) {
                int syncId = player.playerScreenHandler.syncId;
                int swap = inventory.getSwappableHotbarSlot();
                manager.clickSlot(syncId, slot, swap, SlotActionType.SWAP, player);
                slot = swap;
            }
            Minecraft.setSelectedSlot(inventory, slot);
            UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(slot);
            Minecraft.getConnection(handler).send(packetSelect);
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new RotationTask(player, ctx.selectedLecternPos().toCenterPos(), new BreakLecternTask()), ctx));
    }
}

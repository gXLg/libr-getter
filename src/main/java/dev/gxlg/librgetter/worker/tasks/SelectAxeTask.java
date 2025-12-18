package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.worker.Worker;
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

public class SelectAxeTask extends Worker.Task {
    public SelectAxeTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        if (LibrGetter.config.manual) return switchSameTick(new BreakLecternTask(taskContext));

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");

        PlayerInventory inventory = player.getInventory();
        if (inventory == null) return internalError("inventory");

        int slot = -1;
        if (LibrGetter.config.autoTool) {
            float max = -1;
            for (int i = 0; i < PlayerInventory.MAIN_SIZE; i++) {
                ItemStack stack = inventory.getStack(i);
                if (stack.isDamageable() && stack.getMaxDamage() - stack.getDamage() < 10) continue;
                float f = stack.getMiningSpeedMultiplier(Blocks.LECTERN.getDefaultState());
                int ef = Minecraft.getEfficiencyLevel(stack);
                if (stack.getItem() instanceof AxeItem) f += (float) (ef * ef + 1);
                if (f > max) {
                    max = f;
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
            if (manager == null) return internalError("manager");

            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) return internalError("handler");

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

        return switchSameTick(
                new RotationTask(
                        taskContext,
                        player,
                        taskContext.selectedLectern().toCenterPos(),
                        new BreakLecternTask(taskContext)
                )
        );
    }
}

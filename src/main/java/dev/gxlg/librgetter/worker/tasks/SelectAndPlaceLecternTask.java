package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.worker.Worker;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class SelectAndPlaceLecternTask extends Worker.Task {
    public SelectAndPlaceLecternTask(Worker.TaskContext taskContext) {
        super(taskContext);
    }

    @Override
    public Worker.TaskSwitch work() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) return internalError("player");
        ClientWorld world = Minecraft.getWorld(player);

        if (world.getBlockState(taskContext.selectedLectern()).isOf(Blocks.LECTERN)) {
            // the lectern is placed down now
            return switchSameTick(
                    new RotationTask(
                            taskContext,
                            player,
                            EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(taskContext.selectedVillager()),
                            new WaitVillagerAcceptProfessionTask(taskContext)
                    )
            );

        }

        if (LibrGetter.config.manual) return noSwitch();

        // select
        PlayerInventory inventory = player.getInventory();
        if (inventory == null) return internalError("inventory");

        int slot;
        boolean mainhand = true;
        if (ItemStack.areItemsEqual(inventory.getStack(PlayerInventory.OFF_HAND_SLOT), Items.LECTERN.getDefaultStack())) {
            slot = PlayerInventory.OFF_HAND_SLOT;
        } else {
            slot = inventory.getSlotWithStack(Items.LECTERN.getDefaultStack());
        }
        if (slot == -1) return noSwitch();

        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) return internalError("manager");
        ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) return internalError("handler");

        if (slot != PlayerInventory.OFF_HAND_SLOT) {
            if (LibrGetter.config.offhand) {
                if (PlayerInventory.isValidHotbarIndex(slot)) slot += 36;
                manager.clickSlot(player.currentScreenHandler.syncId, slot, PlayerInventory.OFF_HAND_SLOT, SlotActionType.SWAP, player);
                mainhand = false;
            } else {
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
        } else {
            mainhand = false;
        }

        // place
        Vec3d lowBlockPos = taskContext.selectedLectern().toBottomCenterPos();
        BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, taskContext.selectedLectern().down(), false);
        Minecraft.interactBlock(manager, player, lowBlock, mainhand);
        return noSwitch();
    }
}

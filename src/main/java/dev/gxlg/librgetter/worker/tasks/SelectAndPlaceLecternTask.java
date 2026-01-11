package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.InternalTaskException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.TaskException;
import dev.gxlg.librgetter.worker.TaskManager;
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

public class SelectAndPlaceLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            throw new InternalTaskException("player", this);
        }
        ClientWorld world = Minecraft.getWorld(player);

        if (!taskContext.selectedLecternPos().isWithinDistance(player.getBlockPos(), 3.4f)) {
            throw new TaskException("librgetter.far");
        }

        if (world.getBlockState(taskContext.selectedLecternPos()).isOf(Blocks.LECTERN)) {
            // the lectern is placed down now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(
                new RotationTask(
                    player,
                    EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(ctx.selectedVillager()),
                    new WaitVillagerAcceptProfessionTask()
                ), ctx
            ));
        }

        if (LibrGetter.config.manual) {
            return;
        }

        // select
        PlayerInventory inventory = player.getInventory();
        if (inventory == null) {
            throw new InternalTaskException("inventory", this);
        }

        int slot;
        boolean mainhand = true;
        if (ItemStack.areItemsEqual(inventory.getStack(PlayerInventory.OFF_HAND_SLOT), Items.LECTERN.getDefaultStack())) {
            slot = PlayerInventory.OFF_HAND_SLOT;
        } else {
            slot = inventory.getSlotWithStack(Items.LECTERN.getDefaultStack());
        }
        if (slot == -1) {
            return;
        }

        ClientPlayerInteractionManager manager = client.interactionManager;
        if (manager == null) {
            throw new InternalTaskException("manager", this);
        }
        ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) {
            throw new InternalTaskException("handler", this);
        }

        if (slot != PlayerInventory.OFF_HAND_SLOT) {
            if (LibrGetter.config.offhand) {
                if (PlayerInventory.isValidHotbarIndex(slot)) {
                    slot += 36;
                }
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
        Vec3d lowBlockPos = taskContext.selectedLecternPos().toBottomCenterPos();
        BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, taskContext.selectedLecternPos().down(), false);
        Minecraft.interactBlock(manager, player, lowBlock, mainhand);
    }

    @Override
    public boolean allowsPlacing() {
        return true;
    }
}

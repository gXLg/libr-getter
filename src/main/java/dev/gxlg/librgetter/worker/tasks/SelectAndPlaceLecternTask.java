package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.VillagerTooFarException;
import dev.gxlg.librgetter.utils.types.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class SelectAndPlaceLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevel world = Helper.getImpl().getWorld(player);

        if (!taskContext.selectedLecternPos().closerThan(player.blockPosition(), 3.4f)) {
            throw new VillagerTooFarException();
        }

        if (world.getBlockState(taskContext.selectedLecternPos()).is(Blocks.LECTERN)) {
            // the lectern is placed down now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(
                new RotationTask(
                    player, EntityAnchorArgument.Anchor.EYES.apply(ctx.selectedVillager()),
                    new WaitVillagerAcceptProfessionTask()
                ), ctx
            ));
        }

        if (LibrGetter.config.manual) {
            return;
        }

        // select
        Inventory inventory = player.getInventory();
        int slot;
        boolean mainhand = true;
        if (ItemStack.isSameItem(inventory.getItem(Inventory.SLOT_OFFHAND), Items.LECTERN.getDefaultInstance())) {
            slot = Inventory.SLOT_OFFHAND;
        } else {
            slot = inventory.findSlotMatchingItem(Items.LECTERN.getDefaultInstance());
        }
        if (slot == -1) {
            return;
        }

        MultiPlayerGameMode manager = client.gameMode;
        if (manager == null) {
            throw new InternalErrorException("managerInstance");
        }
        ClientPacketListener handler = client.getConnection();
        if (handler == null) {
            throw new InternalErrorException("handler");
        }

        if (slot != Inventory.SLOT_OFFHAND) {
            if (LibrGetter.config.offhand) {
                if (Inventory.isHotbarSlot(slot)) {
                    slot += 36;
                }
                manager.handleInventoryMouseClick(player.containerMenu.containerId, slot, Inventory.SLOT_OFFHAND, ClickType.SWAP, player);
                mainhand = false;
            } else {
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
        } else {
            mainhand = false;
        }

        // place
        Vec3 lowBlockPos = taskContext.selectedLecternPos().getBottomCenter();
        BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, taskContext.selectedLecternPos().below(), false);
        Helper.getImpl().interactBlock(manager, player, lowBlock, mainhand);
    }

    @Override
    public boolean allowsPlacing() {
        return true;
    }
}

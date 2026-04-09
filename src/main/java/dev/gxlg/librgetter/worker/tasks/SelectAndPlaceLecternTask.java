package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.compatibility.CompatibilityManager;
import dev.gxlg.librgetter.utils.InventoryHelper;
import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.VillagerTooFarException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.core.Direction;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.ContainerInput;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

public class SelectAndPlaceLecternTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller, ConfigManager configManager, CompatibilityManager compatibilityManager) throws LibrGetterException {
        MinecraftData minecraftData = taskContext.minecraftData();
        LocalPlayer player = minecraftData.localPlayer;
        if (!taskContext.selectedLecternPos().closerThan(player.blockPosition(), MAX_INTERACTION_DISTANCE)) {
            throw new VillagerTooFarException();
        }

        if (minecraftData.clientLevel.getBlockState(taskContext.selectedLecternPos()).getBlock().equals(Blocks.LECTERN())) {
            // the lectern is placed down now
            Task rotationTask = new RotationTask(player, EntityAnchorArgument$Anchor.EYES().apply(taskContext.selectedVillager()), new WaitVillagerAcceptProfessionTask());
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> rotationTask));
            return;
        }

        if (configManager.getBoolean(Config.MANUAL)) {
            return;
        }

        // select
        Inventory inventory = player.getInventory();
        int slot;
        boolean mainhand = true;

        if (inventory.getItem(Inventory.SLOT_OFFHAND()).getItem().equals(Items.LECTERN())) {
            slot = Inventory.SLOT_OFFHAND();
        } else {
            slot = inventory.findSlotMatchingItem(Items.LECTERN().getDefaultInstance());
        }
        if (slot == -1) {
            return;
        }

        MultiPlayerGameMode gameMode = minecraftData.gameMode;
        if (slot != Inventory.SLOT_OFFHAND()) {
            if (configManager.getBoolean(Config.OFFHAND)) {
                if (Inventory.isHotbarSlot(slot)) {
                    slot += 36;
                }
                gameMode.handleContainerInput(player.getInventoryMenuField().getContainerIdField(), slot, Inventory.SLOT_OFFHAND(), ContainerInput.SWAP(), player);
                mainhand = false;
            } else {
                InventoryHelper.selectItem(player, slot, gameMode, minecraftData.clientNetwork);
            }
        } else {
            mainhand = false;
        }

        // place
        Vec3 lowBlockPos = Vec3.atBottomCenterOf(taskContext.selectedLecternPos());
        BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP(), taskContext.selectedLecternPos().below(), false);
        Players.interactBlock(gameMode, player, lowBlock, mainhand);
    }

    @Override
    protected boolean allowsPlacingLectern() {
        return true;
    }

    @Override
    protected boolean forcesSecondaryUse() {
        return true;
    }
}

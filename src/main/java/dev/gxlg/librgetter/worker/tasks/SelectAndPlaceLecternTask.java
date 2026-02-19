package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.InventoryHelper;
import dev.gxlg.librgetter.utils.chaining.players.Players;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.VillagerTooFarException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.EntityAnchorArgument$Anchor;
import dev.gxlg.versiont.gen.net.minecraft.core.Direction;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.inventory.ClickType;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

public class SelectAndPlaceLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevel world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }

        if (!taskContext.selectedLecternPos().closerThan(player.blockPosition(), 3.4f)) {
            throw new VillagerTooFarException();
        }

        if (world.getBlockState(taskContext.selectedLecternPos()).is(Blocks.LECTERN())) {
            // the lectern is placed down now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(
                new RotationTask(
                    player,
                    EntityAnchorArgument$Anchor.EYES().apply(ctx.selectedVillager()),
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

        if (inventory.getItem(Inventory.SLOT_OFFHAND()).is(Items.LECTERN())) {
            slot = Inventory.SLOT_OFFHAND();
        } else {
            slot = inventory.findSlotMatchingItem(Items.LECTERN().getDefaultInstance());
        }
        if (slot == -1) {
            return;
        }

        MultiPlayerGameMode game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }
        ClientPacketListener clientNetwork = client.getConnection();
        if (clientNetwork == null) {
            throw new InternalErrorException("clientNetwork");
        }

        if (slot != Inventory.SLOT_OFFHAND()) {
            if (LibrGetter.config.offhand) {
                if (Inventory.isHotbarSlot(slot)) {
                    slot += 36;
                }
                game.handleInventoryMouseClick(player.getInventoryMenuField().getContainerIdField(), slot, Inventory.SLOT_OFFHAND(), ClickType.SWAP(), player);
                mainhand = false;
            } else {
                InventoryHelper.selectItem(player, slot, game, clientNetwork);
            }
        } else {
            mainhand = false;
        }

        // place
        Vec3 lowBlockPos = Vec3.atBottomCenterOf(taskContext.selectedLecternPos());
        BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP(), taskContext.selectedLecternPos().below(), false);
        Players.getImpl().interactBlock(game, player, lowBlock, mainhand);
    }

    @Override
    public boolean allowsPlacing() {
        return true;
    }
}

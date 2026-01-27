package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.InventoryHelper;
import dev.gxlg.librgetter.utils.chaining.helper.Helper;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.VillagerTooFarException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.EntityAnchorArgument$AnchorWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.inventory.ClickTypeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlocksWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper;

public class SelectAndPlaceLecternTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevelWrapper world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }

        if (!taskContext.selectedLecternPos().closerThan(player.blockPosition(), 3.4f)) {
            throw new VillagerTooFarException();
        }

        if (world.getBlockState(taskContext.selectedLecternPos()).is(BlocksWrapper.LECTERN())) {
            // the lectern is placed down now
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(
                new RotationTask(
                    player,
                    EntityAnchorArgument$AnchorWrapper.EYES().apply(ctx.selectedVillager()),
                    new WaitVillagerAcceptProfessionTask()
                ), ctx
            ));
        }

        if (LibrGetter.config.manual) {
            return;
        }

        // select
        InventoryWrapper inventory = player.getInventory();
        int slot;
        boolean mainhand = true;

        if (inventory.getItem(InventoryWrapper.SLOT_OFFHAND()).is(ItemsWrapper.LECTERN())) {
            slot = InventoryWrapper.SLOT_OFFHAND();
        } else {
            slot = inventory.findSlotMatchingItem(ItemsWrapper.LECTERN().getDefaultInstance());
        }
        if (slot == -1) {
            return;
        }

        MultiPlayerGameModeWrapper game = client.getGameModeField();
        if (game == null) {
            throw new InternalErrorException("game");
        }
        ClientPacketListenerWrapper clientNetwork = client.getConnection();
        if (clientNetwork == null) {
            throw new InternalErrorException("clientNetwork");
        }

        if (slot != InventoryWrapper.SLOT_OFFHAND()) {
            if (LibrGetter.config.offhand) {
                if (InventoryWrapper.isHotbarSlot(slot)) {
                    slot += 36;
                }
                game.handleInventoryMouseClick(player.getInventoryMenuField().getContainerIdField(), slot, InventoryWrapper.SLOT_OFFHAND(), ClickTypeWrapper.SWAP(), player);
                mainhand = false;
            } else {
                InventoryHelper.selectItem(player, slot, game, clientNetwork);
            }
        } else {
            mainhand = false;
        }

        // place
        Vec3Wrapper lowBlockPos = Vec3Wrapper.atBottomCenterOf(taskContext.selectedLecternPos());
        BlockHitResultWrapper lowBlock = new BlockHitResultWrapper(lowBlockPos, DirectionWrapper.UP(), taskContext.selectedLecternPos().below(), false);
        Helper.getImpl().interactBlock(game, player, lowBlock, mainhand);
    }

    @Override
    public boolean allowsPlacing() {
        return true;
    }
}

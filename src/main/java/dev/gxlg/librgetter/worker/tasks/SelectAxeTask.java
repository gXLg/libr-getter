package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.InventoryHelper;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.signals.StopTaskSignal;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.item.AxeItem;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

public class SelectAxeTask extends TaskManager.Task {
    @Override
    public void work(TaskManager.TaskContext taskContext) throws StopTaskSignal, LibrGetterException {
        if (LibrGetter.config.manual) {
            throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new BreakLecternTask(), ctx));
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        Inventory inventory = player.getInventory();

        int slot = -1;
        if (LibrGetter.config.autoTool) {
            float maxBreakingSpeed = -1;
            for (int i = 0; i < Inventory.INVENTORY_SIZE(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.isDamageableItem() && stack.getMaxDamage() - stack.getDamageValue() < 10) {
                    continue;
                }
                float breakingSpeed = stack.getDestroySpeed(Blocks.LECTERN().defaultBlockState());
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
                for (int i = 0; i < Inventory.INVENTORY_SIZE(); i++) {
                    ItemStack stack = inventory.getItem(i);
                    if (ItemStack.matches(stack, taskContext.defaultItem())) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        if (slot != -1) {
            MultiPlayerGameMode game = client.getGameModeField();
            if (game == null) {
                throw new InternalErrorException("game");
            }
            ClientPacketListener clientNetwork = client.getConnection();
            if (clientNetwork == null) {
                throw new InternalErrorException("clientNetwork");
            }
            InventoryHelper.selectItem(player, slot, game, clientNetwork);
        }

        throw new StopTaskSignal(ctx -> TaskManager.TaskSwitch.sameTick(new RotationTask(player, Vec3.atCenterOf(ctx.selectedLecternPos()), new BreakLecternTask()), ctx));
    }
}

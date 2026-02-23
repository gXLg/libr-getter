package dev.gxlg.librgetter.worker.tasks;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.InventoryHelper;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.worker.scheduling.controllers.TaskSchedulerController;
import dev.gxlg.librgetter.worker.types.context.MinecraftData;
import dev.gxlg.librgetter.worker.types.context.TaskContext;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.librgetter.worker.types.task.Task;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.player.Inventory;
import dev.gxlg.versiont.gen.net.minecraft.world.item.AxeItem;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;

public class SelectAxeTask extends Task {
    @Override
    public void work(TaskContext taskContext, TaskSchedulerController controller) throws LibrGetterException {
        if (LibrGetter.config.manual) {
            controller.scheduleTaskSwitch(TaskSwitch.sameTick(BreakLecternTask::new));
            return;
        }

        MinecraftData minecraftData = taskContext.minecraftData();
        LocalPlayer player = minecraftData.localPlayer;
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
                int efficiencyLevel = Enchantments.getEfficiencyLevel(stack);
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
            InventoryHelper.selectItem(player, slot, minecraftData.gameMode, minecraftData.clientNetwork);
        }

        Task rotationTask = new RotationTask(player, Vec3.atCenterOf(taskContext.selectedLecternPos()), new BreakLecternTask());
        controller.scheduleTaskSwitch(TaskSwitch.sameTick(() -> rotationTask));
    }
}

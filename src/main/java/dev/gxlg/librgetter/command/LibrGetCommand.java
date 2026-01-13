package dev.gxlg.librgetter.command;

import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("SameReturnValue")
public class LibrGetCommand {

    public static int add(CommandContext<?> context) {
        return CommandHelper.manageGoals(context, false);
    }

    public static int addCustom(CommandContext<?> context) {
        return CommandHelper.manageGoalsCustom(context, false);
    }

    public static int remove(CommandContext<?> context) {
        return CommandHelper.manageGoals(context, true);
    }

    public static int removeCustom(CommandContext<?> context) {
        return CommandHelper.manageGoalsCustom(context, true);
    }

    public static int list() {
        Texts.getImpl().sendListOfGoals();
        return 0;
    }

    public static <T> int config(CommandContext<?> context, Configurable<T> config) {
        T value = config.get();
        try {
            value = context.getArgument("value", config.type());
        } catch (IllegalArgumentException ignored) {
        }

        config.set(value);
        config.instance().save();

        if (!ConfigScreen.configChange()) {
            Texts.getImpl().sendTranslatableFeedback("librgetter.config", config.name(), value);
        }

        return 0;
    }

    public static int autostart() {
        if (TaskManager.isWorking()) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "player", "LibrGetCommand#autostart");
            return 1;
        }
        ClientLevel world = client.level;
        if (world == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "world", "LibrGetCommand#autostart");
            return 1;
        }

        BlockPos foundLecternPos = null;
        for (int distance = 1; distance < 5; distance++) {
            for (int deltaX = -distance; deltaX <= distance; deltaX++) {
                for (int deltaY = -distance; deltaY <= distance; deltaY++) {
                    for (int deltaZ = -distance; deltaZ <= distance; deltaZ++) {
                        if (distance != Math.abs(deltaX) && distance != Math.abs(deltaY) && distance != Math.abs(deltaZ)) {
                            continue;
                        }

                        BlockPos pos = player.blockPosition().offset(deltaX, deltaY, deltaZ);
                        if (world.getBlockState(pos).is(Blocks.LECTERN)) {
                            foundLecternPos = pos;
                            break;
                        }
                    }
                    if (foundLecternPos != null) {
                        break;
                    }
                }
                if (foundLecternPos != null) {
                    break;
                }
            }
            if (foundLecternPos != null) {
                break;
            }
        }
        if (foundLecternPos == null) {
            Texts.getImpl().sendTranslatableError("librgetter.find_lectern");
            return 1;
        }
        Iterable<Entity> worldEntities = world.entitiesForRendering();
        Villager foundVillager = null;
        float minDistance = Float.MAX_VALUE;
        for (Entity entity : worldEntities) {
            if (entity instanceof Villager villager) {
                if (MinecraftHelper.isVillagerLibrarian(villager)) {
                    float distance = villager.distanceTo(player);
                    if (distance < minDistance && distance < 10) {
                        foundVillager = villager;
                        minDistance = distance;
                    }
                }
            }
        }
        if (foundVillager == null) {
            Texts.getImpl().sendTranslatableError("librgetter.find_librarian");
            return 1;
        }

        BlockPos finalLecTernPos = foundLecternPos;
        Villager finalVi = foundVillager;
        TaskManager.switchTask(ctx -> TaskManager.TaskSwitch.nextTick(new StartTask(true), ctx.withLecternPos(finalLecTernPos).withVillager(finalVi)));

        return 0;
    }

    public static int clearGoals() {
        LibrGetter.config.goals.clear();
        LibrGetter.config.save();
        Texts.getImpl().sendTranslatableWarning("librgetter.cleared");
        return 0;
    }

    public static int stopWorking() {
        TaskManager.stop();
        return 0;
    }

    public static int startWorking() {
        if (TaskManager.isWorking()) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }
        TaskManager.switchTask(ctx -> TaskManager.TaskSwitch.nextTick(new StartTask(true), ctx));
        return 0;
    }

    public static int continueWorking() {
        if (TaskManager.isWorking()) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }
        TaskManager.switchTask(ctx -> TaskManager.TaskSwitch.nextTick(new StartTask(false), ctx));
        return 0;
    }

    public static int selector() {
        if (TaskManager.isWorking()) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }

        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
        if (world == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "world", "LibrGetCommand#selector");
            return 1;
        }
        LocalPlayer player = client.player;
        if (player == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "player", "LibrGetCommand#selector");
            return 1;
        }
        HitResult hit = client.hitResult;
        if (hit == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "hit", "LibrGetCommand#selector");
            return 1;
        }
        HitResult.Type hitType = hit.getType();
        if (hitType == HitResult.Type.MISS) {
            Texts.getImpl().sendTranslatableError("librgetter.nothing");
            return 1;
        }

        if (hitType == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).is(Blocks.LECTERN)) {
                Texts.getImpl().sendTranslatableError("librgetter.not_lectern");
                return 1;
            }

            TaskManager.updateContext(ctx -> ctx.withLecternPos(blockPos));
            Texts.getImpl().sendTranslatableFeedback("librgetter.lectern");

        } else if (hitType == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if (!(entity instanceof Villager villager)) {
                Texts.getImpl().sendTranslatableError("librgetter.not_villager");
                return 1;
            }
            if (!MinecraftHelper.isVillagerLibrarian(villager)) {
                Texts.getImpl().sendTranslatableError("librgetter.not_librarian");
                return 1;
            }

            TaskManager.updateContext(ctx -> ctx.withVillager(villager));
            Texts.getImpl().sendTranslatableFeedback("librgetter.librarian");
        }

        return 0;
    }
}
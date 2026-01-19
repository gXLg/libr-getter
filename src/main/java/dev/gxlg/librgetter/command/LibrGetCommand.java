package dev.gxlg.librgetter.command;

import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.AlreadyRunningException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.BlockNotLecternException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.CouldNotFindLecternException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.CouldNotFindLibrarianException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.EntityNotVillagerException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.NothingTargetedException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.VillagerNotLibrarianException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.ProcessNotRunningException;
import dev.gxlg.librgetter.utils.types.translatable_messages.feedback.ConfigValueMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.feedback.GoalsListClearedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.feedback.LecternSelectedMessage;
import dev.gxlg.librgetter.utils.types.translatable_messages.feedback.LibrarianSelectedMessage;
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

public class LibrGetCommand {

    public static void add(CommandContext<?> context) throws LibrGetterException {
        CommandHelper.manageGoals(context, false);
    }

    public static void addCustom(CommandContext<?> context) throws LibrGetterException {
        CommandHelper.manageGoalsCustom(context, false);
    }

    public static void remove(CommandContext<?> context) throws LibrGetterException {
        CommandHelper.manageGoals(context, true);
    }

    public static void removeCustom(CommandContext<?> context) throws LibrGetterException {
        CommandHelper.manageGoalsCustom(context, true);
    }

    public static void list() {
        Texts.getImpl().sendListOfGoals();
    }

    public static <T> void config(CommandContext<?> context, Configurable<T> config) {
        T value = config.get();
        try {
            value = context.getArgument("value", config.type());
            config.set(value);
            config.managerInstance().save();
        } catch (IllegalArgumentException ignored) {
        }

        if (Minecraft.getInstance().screen instanceof ConfigScreen cs) {
            cs.updateScreen();
        } else {
            Texts.getImpl().sendTranslatable(new ConfigValueMessage(config.name(), value));
        }
    }

    public static void autostart() throws LibrGetterException {
        if (TaskManager.isWorking()) {
            throw new AlreadyRunningException();
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevel world = client.level;
        if (world == null) {
            throw new InternalErrorException("world");
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
            throw new CouldNotFindLecternException();
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
            throw new CouldNotFindLibrarianException();
        }

        BlockPos finalLecTernPos = foundLecternPos;
        Villager finalVi = foundVillager;
        TaskManager.switchTask(ctx -> TaskManager.TaskSwitch.nextTick(new StartTask(true), ctx.withLecternPos(finalLecTernPos).withVillager(finalVi)));
    }

    public static void clearGoals() {
        LibrGetter.config.goals.clear();
        LibrGetter.configManager.save();
        Texts.getImpl().sendTranslatable(new GoalsListClearedMessage());
    }

    public static void stopWorking() throws ProcessNotRunningException {
        TaskManager.stop();
    }

    public static void startWorking() throws AlreadyRunningException {
        if (TaskManager.isWorking()) {
            throw new AlreadyRunningException();
        }
        TaskManager.switchTask(ctx -> TaskManager.TaskSwitch.nextTick(new StartTask(true), ctx));
    }

    public static void continueWorking() throws AlreadyRunningException {
        if (TaskManager.isWorking()) {
            throw new AlreadyRunningException();
        }
        TaskManager.switchTask(ctx -> TaskManager.TaskSwitch.nextTick(new StartTask(false), ctx));
    }

    public static void selector() throws LibrGetterException {
        if (TaskManager.isWorking()) {
            throw new AlreadyRunningException();
        }

        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
        if (world == null) {
            throw new InternalErrorException("world");
        }
        LocalPlayer player = client.player;
        if (player == null) {
            throw new InternalErrorException("player");
        }
        HitResult hit = client.hitResult;
        if (hit == null) {
            throw new InternalErrorException("hit");
        }
        HitResult.Type hitType = hit.getType();
        if (hitType == HitResult.Type.MISS) {
            throw new NothingTargetedException();
        }

        if (hitType == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).is(Blocks.LECTERN)) {
                throw new BlockNotLecternException();
            }

            TaskManager.updateContext(ctx -> ctx.withLecternPos(blockPos));
            Texts.getImpl().sendTranslatable(new LecternSelectedMessage());

        } else if (hitType == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if (!(entity instanceof Villager villager)) {
                throw new EntityNotVillagerException();
            }
            if (!MinecraftHelper.isVillagerLibrarian(villager)) {
                throw new VillagerNotLibrarianException();
            }

            TaskManager.updateContext(ctx -> ctx.withVillager(villager));
            Texts.getImpl().sendTranslatable(new LibrarianSelectedMessage());
        }
    }
}
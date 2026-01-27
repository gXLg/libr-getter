package dev.gxlg.librgetter.command;

import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
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
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ConfigValueMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.GoalsListClearedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.LecternSelectedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.LibrarianSelectedMessage;
import dev.gxlg.librgetter.worker.TaskManager;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlocksWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.BlockHitResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.EntityHitResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResult$TypeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.phys.HitResultWrapper;

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

        ScreenWrapper screen = MinecraftWrapper.getInstance().getScreenField();
        if (screen.isInstanceOf(ConfigScreen.class)) {
            screen.downcast(ConfigScreen.class).updateScreen();
        } else {
            Texts.getImpl().sendTranslatable(new ConfigValueMessage(config.name(), value));
        }
    }

    public static void autostart() throws LibrGetterException {
        if (TaskManager.isWorking()) {
            throw new AlreadyRunningException();
        }

        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevelWrapper world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }

        BlockPosWrapper foundLecternPos = null;
        for (int distance = 1; distance < 5; distance++) {
            for (int deltaX = -distance; deltaX <= distance; deltaX++) {
                for (int deltaY = -distance; deltaY <= distance; deltaY++) {
                    for (int deltaZ = -distance; deltaZ <= distance; deltaZ++) {
                        if (distance != Math.abs(deltaX) && distance != Math.abs(deltaY) && distance != Math.abs(deltaZ)) {
                            continue;
                        }

                        BlockPosWrapper pos = player.blockPosition().offset(deltaX, deltaY, deltaZ);
                        if (world.getBlockState(pos).is(BlocksWrapper.LECTERN())) {
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
        Iterable<EntityWrapper> worldEntities = world.entitiesForRendering();
        VillagerWrapper foundVillager = null;
        float minDistance = Float.MAX_VALUE;
        for (EntityWrapper entity : worldEntities) {
            if (entity.isInstanceOf(VillagerWrapper.class)) {
                VillagerWrapper villager = entity.downcast(VillagerWrapper.class);
                if (Villagers.getImpl().isVillagerLibrarian(villager)) {
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

        BlockPosWrapper finalLecTernPos = foundLecternPos;
        VillagerWrapper finalVi = foundVillager;
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

        MinecraftWrapper client = MinecraftWrapper.getInstance();
        ClientLevelWrapper world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }
        LocalPlayerWrapper player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        HitResultWrapper hit = client.getHitResultField();
        if (hit == null) {
            throw new InternalErrorException("hit");
        }
        HitResult$TypeWrapper hitType = hit.getType();
        if (hitType.equals(HitResult$TypeWrapper.MISS())) {
            throw new NothingTargetedException();
        }

        if (hitType.equals(HitResult$TypeWrapper.BLOCK())) {
            BlockPosWrapper blockPos = hit.downcast(BlockHitResultWrapper.class).getBlockPos();
            if (!world.getBlockState(blockPos).is(BlocksWrapper.LECTERN())) {
                throw new BlockNotLecternException();
            }
            TaskManager.updateContext(ctx -> ctx.withLecternPos(blockPos));
            Texts.getImpl().sendTranslatable(new LecternSelectedMessage());

        } else if (hitType.equals(HitResult$TypeWrapper.ENTITY())) {
            EntityWrapper entity = hit.downcast(EntityHitResultWrapper.class).getEntity();
            if (!(entity.isInstanceOf(VillagerWrapper.class))) {
                throw new EntityNotVillagerException();
            }
            VillagerWrapper villager = entity.downcast(VillagerWrapper.class);
            if (!Villagers.getImpl().isVillagerLibrarian(villager)) {
                throw new VillagerNotLibrarianException();
            }

            TaskManager.updateContext(ctx -> ctx.withVillager(villager));
            Texts.getImpl().sendTranslatable(new LibrarianSelectedMessage());
        }
    }
}
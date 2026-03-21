package dev.gxlg.librgetter.command;

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
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStoppedMessage;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import dev.gxlg.librgetter.worker.types.scheduling.AbstractSchedulerController;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.Entity;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.EntityHitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.HitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.HitResult$Type;

public class LibrGetCommand {

    public static void add(CommandContext context) throws LibrGetterException {
        CommandHelper.manageGoals(context, false);
    }

    public static void addCustom(CommandContext context) throws LibrGetterException {
        CommandHelper.manageGoalsCustom(context, false);
    }

    public static void remove(CommandContext context) throws LibrGetterException {
        CommandHelper.manageGoals(context, true);
    }

    public static void removeCustom(CommandContext context) throws LibrGetterException {
        CommandHelper.manageGoalsCustom(context, true);
    }

    public static void list() {
        Texts.sendListOfGoals();
    }

    public static <T> void config(CommandContext context, Configurable<T> config) {
        T value = config.get();
        try {
            Class<T> type = config.type();
            value = context.getArgument("value", R.clz(type)).unwrap(type);
            config.set(value);
            config.managerInstance().save();
        } catch (IllegalArgumentException ignored) {
        }

        Screen screen = Minecraft.getInstance().getScreenField();
        if (screen instanceof ConfigScreen configScreen) {
            configScreen.updateScreen();
        } else {
            Texts.sendTranslatable(new ConfigValueMessage(config.name(), value));
        }
    }

    public static void autostart() throws LibrGetterException {
        if (LibrGetter.worker.getStateView().isWorking()) {
            throw new AlreadyRunningException();
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevel world = client.getLevelField();
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
                        if (world.getBlockState(pos).is(Blocks.LECTERN())) {
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
                if (Villagers.isVillagerLibrarian(villager)) {
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

        BlockPos finalLecternPos = foundLecternPos;
        Villager finalVillager = foundVillager;

        AbstractSchedulerController controller = LibrGetter.worker.getUserSchedulerController();
        controller.scheduleContextUpdate(ctx -> ctx.setLecternPos(finalLecternPos).setVillager(finalVillager));
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(true)));
    }

    public static void clearGoals() {
        LibrGetter.config.goals.clear();
        LibrGetter.configManager.save();
        Texts.sendTranslatable(new GoalsListClearedMessage());
    }

    public static void stopWorking() throws ProcessNotRunningException {
        if (!LibrGetter.worker.getStateView().isWorking()) {
            throw new ProcessNotRunningException();
        }
        LibrGetter.worker.getUserSchedulerController().scheduleTaskSwitch(TaskSwitch.nextTick(() -> {
            Texts.sendTranslatable(new ProcessStoppedMessage());
            return new StandbyTask();
        }));
    }

    public static void startWorking() throws AlreadyRunningException {
        if (LibrGetter.worker.getStateView().isWorking()) {
            throw new AlreadyRunningException();
        }
        LibrGetter.worker.getUserSchedulerController().scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(true)));
    }

    public static void continueWorking() throws AlreadyRunningException {
        if (LibrGetter.worker.getStateView().isWorking()) {
            throw new AlreadyRunningException();
        }
        LibrGetter.worker.getUserSchedulerController().scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(false)));
    }

    public static void selector() throws LibrGetterException {
        if (LibrGetter.worker.getStateView().isWorking()) {
            throw new AlreadyRunningException();
        }

        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        HitResult hit = client.getHitResultField();
        if (hit == null) {
            throw new InternalErrorException("hit");
        }
        HitResult$Type hitType = hit.getType();
        if (hitType.equals(HitResult$Type.MISS())) {
            throw new NothingTargetedException();
        }

        if (hitType.equals(HitResult$Type.BLOCK())) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).is(Blocks.LECTERN())) {
                throw new BlockNotLecternException();
            }
            LibrGetter.worker.getUserSchedulerController().scheduleContextUpdate(ctx -> {
                ctx.setLecternPos(blockPos);
                Texts.sendTranslatable(new LecternSelectedMessage());
            });

        } else if (hitType.equals(HitResult$Type.ENTITY())) {
            Entity entity = ((EntityHitResult) hit).getEntity();
            if (!(entity instanceof Villager villager)) {
                throw new EntityNotVillagerException();
            }
            if (!Villagers.isVillagerLibrarian(villager)) {
                throw new VillagerNotLibrarianException();
            }
            LibrGetter.worker.getUserSchedulerController().scheduleContextUpdate(ctx -> {
                ctx.setVillager(villager);
                Texts.sendTranslatable(new LibrarianSelectedMessage());
            });
        }
    }
}
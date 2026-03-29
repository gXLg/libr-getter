package dev.gxlg.librgetter.controller;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.AlreadyRunningException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.BlockNotLecternException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.CouldNotFindLecternException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.CouldNotFindLibrarianException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.EntityNotVillagerException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.NothingTargetedException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.VillagerNotLibrarianException;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.ProcessNotRunningException;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.LecternSelectedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.LibrarianSelectedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStoppedMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
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

public class SharedController {
    private final StateView stateView;

    private final UserSchedulerController controller;

    public SharedController(StateView stateView, UserSchedulerController controller) {
        this.stateView = stateView;
        this.controller = controller;
    }

    public void autostart() throws LibrGetterException {
        if (stateView.isWorking()) {
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
                        if (world.getBlockState(pos).getBlock().equals(Blocks.LECTERN())) {
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

        controller.scheduleContextUpdate(ctx -> ctx.setLecternPos(finalLecternPos).setVillager(finalVillager));
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(true)));
    }

    public void stopWorking() throws ProcessNotRunningException {
        if (!stateView.isWorking()) {
            throw new ProcessNotRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> {
            Texts.sendMessage(new ProcessStoppedMessage());
            return new StandbyTask();
        }));
    }

    public void startWorking() throws AlreadyRunningException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(true)));
    }

    public void continueWorking() throws AlreadyRunningException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(false)));
    }

    public void selector() throws LibrGetterException {
        if (stateView.isWorking()) {
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
            if (!world.getBlockState(blockPos).getBlock().equals(Blocks.LECTERN())) {
                throw new BlockNotLecternException();
            }
            controller.scheduleContextUpdate(ctx -> {
                ctx.setLecternPos(blockPos);
                Texts.sendMessage(new LecternSelectedMessage());
            });

        } else if (hitType.equals(HitResult$Type.ENTITY())) {
            Entity entity = ((EntityHitResult) hit).getEntity();
            if (!(entity instanceof Villager villager)) {
                throw new EntityNotVillagerException();
            }
            if (!Villagers.isVillagerLibrarian(villager)) {
                throw new VillagerNotLibrarianException();
            }
            controller.scheduleContextUpdate(ctx -> {
                ctx.setVillager(villager);
                Texts.sendMessage(new LibrarianSelectedMessage());
            });
        }
    }

    public StateView getStateView() {
        return stateView;
    }
}
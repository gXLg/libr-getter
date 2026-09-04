package dev.gxlg.librgetter.controller;

import dev.gxlg.librgetter.utils.PathFinding;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.exceptions.commands.AlreadyRunningException;
import dev.gxlg.librgetter.utils.exceptions.commands.BlockNotLecternException;
import dev.gxlg.librgetter.utils.exceptions.commands.EntityNotVillagerException;
import dev.gxlg.librgetter.utils.exceptions.commands.NothingTargetedException;
import dev.gxlg.librgetter.utils.exceptions.commands.VillagerNotLibrarianException;
import dev.gxlg.librgetter.utils.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.exceptions.tasks.ProcessNotRunningException;
import dev.gxlg.librgetter.utils.messages.translatable.feedback.LecternSelectedMessage;
import dev.gxlg.librgetter.utils.messages.translatable.feedback.LibrarianSelectedMessage;
import dev.gxlg.librgetter.utils.messages.translatable.feedback.ProcessStoppedMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import dev.gxlg.librgetter.worker.types.context.TaskContextBuilder;
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

        PathFinding.Jobsite workstation = PathFinding.findJobsite(world, player.blockPosition(), p -> true);
        controller.scheduleContextUpdate(ctx -> ctx.setLecternPos(workstation.lectern()).setVillager(workstation.librarian()).resetAttemptsCounter());
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(StartTask::new));
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
        controller.scheduleContextUpdate(TaskContextBuilder::resetAttemptsCounter);
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(StartTask::new));
    }

    public void continueWorking() throws AlreadyRunningException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(StartTask::new));
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
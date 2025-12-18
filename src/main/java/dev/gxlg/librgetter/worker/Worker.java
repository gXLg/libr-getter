package dev.gxlg.librgetter.worker;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class Worker {
    @NotNull
    private static Task currentTask = new StandbyTask(TaskContext.EMPTY);

    public static void work() {
        while (true) {
            TaskSwitch taskSwitch = currentTask.work();
            currentTask = taskSwitch.nextTask();
            if (taskSwitch.tickBehaviour() == TaskSwitch.TickBehaviour.NEXT_TICK) break;
        }
    }

    public static void stop() {
        if (currentTask instanceof StandbyTask) {
            Texts.getImpl().sendTranslatableError("librgetter.not_running");
            return;
        }
        Texts.getImpl().sendTranslatableWarning("librgetter.stop");
        currentTask = new StandbyTask(currentTask.getTaskContext());
    }

    public static @NotNull Task getCurrentTask() {
        return currentTask;
    }

    public record TaskSwitch(@NotNull Task nextTask, TickBehaviour tickBehaviour) {
        public enum TickBehaviour {
            SAME_TICK, NEXT_TICK
        }

        public static TaskSwitch sameTick(Task nextTask) {
            return new TaskSwitch(nextTask, TaskSwitch.TickBehaviour.SAME_TICK);
        }

        public static TaskSwitch nextTick(Task nextTask) {
            return new TaskSwitch(nextTask, TaskSwitch.TickBehaviour.NEXT_TICK);
        }
    }

    public record TaskContext(BlockPos selectedLectern, ItemStack defaultItem,
                              VillagerEntity selectedVillager, int attemptsCounter) {
        public static final TaskContext EMPTY = new TaskContext(null, null, null, 0);

        public TaskContext withLectern(BlockPos pos) {
            return new TaskContext(pos, defaultItem, selectedVillager, attemptsCounter);
        }

        public TaskContext withDefaultItem(ItemStack item) {
            return new TaskContext(selectedLectern, item, selectedVillager, attemptsCounter);
        }

        public TaskContext withVillager(VillagerEntity villager) {
            return new TaskContext(selectedLectern, defaultItem, villager, attemptsCounter);
        }

        public TaskContext withIncreasedAttemptsCounter() {
            return new TaskContext(selectedLectern, defaultItem, selectedVillager, attemptsCounter + 1);
        }

        public TaskContext withResetAttemptsCounter() {
            return new TaskContext(selectedLectern, defaultItem, selectedVillager, 0);
        }
    }

    public abstract static class Task {
        protected final TaskContext taskContext;

        public Task(TaskContext taskContext) {
            this.taskContext = taskContext;
        }

        public abstract TaskSwitch work();

        public TaskContext getTaskContext() {
            return taskContext;
        }

        public boolean allowsBreaking() {
            return false;
        }

        public boolean shouldCloseScreen() {
            return false;
        }

        public boolean allowsPlacing() {
            return true;
        }


        protected TaskSwitch internalError(String varName) {
            return error("librgetter.internal", varName, this.getClass().getName());
        }

        protected TaskSwitch error(String msg, String... args) {
            Texts.getImpl().sendTranslatableError(msg, (Object[]) args);
            return finish();
        }

        protected TaskSwitch finish() {
            return switchNextTick(new StandbyTask(taskContext));
        }

        protected TaskSwitch switchNextTick(Task nextTask) {
            return TaskSwitch.nextTick(nextTask);
        }

        protected TaskSwitch switchSameTick(Task nextTask) {
            return TaskSwitch.sameTick(nextTask);
        }

        protected TaskSwitch noSwitch() {
            return switchNextTick(this);
        }
    }


    static {
        ClientPlayConnectionEvents.JOIN.register((h, s, c) -> currentTask = new StandbyTask(Worker.TaskContext.EMPTY));
        ClientPlayConnectionEvents.DISCONNECT.register((h, c) -> currentTask = new StandbyTask(Worker.TaskContext.EMPTY));
    }
}

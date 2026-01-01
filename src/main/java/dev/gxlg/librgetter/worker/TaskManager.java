package dev.gxlg.librgetter.worker;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.librgetter.utils.types.exceptions.tasks.StopTaskSignal;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class TaskManager {
    @NotNull
    private static Task currentTask = new StandbyTask();
    @NotNull
    private static TaskContext taskContext = TaskContext.EMPTY;

    private static final BlockingQueue<Function<TaskContext, TaskContext>> contextUpdates = new LinkedBlockingQueue<>();
    private static final AtomicReference<Function<TaskContext, TaskSwitch>> taskSwitcherReference = new AtomicReference<>(null);

    public static void updateContext(Function<TaskContext, TaskContext> updater) {
        try {
            contextUpdates.put(updater);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void switchTask(Function<TaskContext, TaskSwitch> switcher) {
        taskSwitcherReference.set(switcher);
    }

    public static void work() {
        do {
            try {
                currentTask.work(taskContext);
            } catch (StopTaskSignal signal) {
                signal.switchTask();
            }
        } while (updateContextAndTaskAndReturnIsNextTick());
    }

    private static boolean updateContextAndTaskAndReturnIsNextTick() {
        Function<TaskContext, TaskSwitch> taskSwitcher = taskSwitcherReference.getAndSet(null);
        while (!contextUpdates.isEmpty()) {
            try {
                taskContext = contextUpdates.take().apply(taskContext);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (taskSwitcher == null) return false;
        TaskSwitch taskSwitch = taskSwitcher.apply(taskContext);

        currentTask = taskSwitch.getNextTask();
        taskContext = taskSwitch.getNewContext();
        return !taskSwitch.isNextTick();
    }

    public static void stop() {
        if (!isWorking()) {
            Texts.getImpl().sendTranslatableError("librgetter.not_running");
            return;
        }
        Texts.getImpl().sendTranslatableWarning("librgetter.stop");
        switchTask(ctx -> TaskSwitch.nextTick(new StandbyTask(), TaskContext.EMPTY));
    }

    public static @NotNull Task getCurrentTask() {
        return currentTask;
    }

    public static @NotNull TaskContext getTaskContext() {
        return taskContext;
    }

    public static boolean isWorking() {
        return !(currentTask instanceof StandbyTask);
    }

    public static class TaskSwitch {
        private final Task nextTask;
        private final TaskContext newContext;
        private final boolean nextTick;

        private TaskSwitch(Task nextTask, TaskContext newContext, boolean nextTick) {
            this.nextTask = nextTask;
            this.newContext = newContext;
            this.nextTick = nextTick;
        }

        public Task getNextTask() {
            return nextTask;
        }

        public TaskContext getNewContext() {
            return newContext;
        }

        public boolean isNextTick() {
            return nextTick;
        }

        public static TaskSwitch sameTick(Task nextTask, TaskContext newContext) {
            return new TaskSwitch(nextTask, newContext, false);
        }

        public static TaskSwitch nextTick(Task nextTask, TaskContext newContext) {
            return new TaskSwitch(nextTask, newContext, true);
        }
    }

    public record TaskContext(BlockPos selectedLectern, ItemStack defaultItem,
                              VillagerEntity selectedVillager, int attemptsCounter, TradeOfferData tradeOfferData) {
        public static final TaskContext EMPTY = new TaskContext(null, null, null, 0, null);

        public TaskContext withLectern(BlockPos pos) {
            return new TaskContext(pos, defaultItem, selectedVillager, attemptsCounter, tradeOfferData);
        }

        public TaskContext withDefaultItem(ItemStack item) {
            return new TaskContext(selectedLectern, item, selectedVillager, attemptsCounter, tradeOfferData);
        }

        public TaskContext withVillager(VillagerEntity villager) {
            return new TaskContext(selectedLectern, defaultItem, villager, attemptsCounter, tradeOfferData);
        }

        public TaskContext withIncreasedAttemptsCounter() {
            return new TaskContext(selectedLectern, defaultItem, selectedVillager, attemptsCounter + 1, tradeOfferData);
        }

        public TaskContext withResetAttemptsCounter() {
            return new TaskContext(selectedLectern, defaultItem, selectedVillager, 0, tradeOfferData);
        }

        public TaskContext withTradeOfferData(TradeOfferData data) {
            return new TaskContext(selectedLectern, defaultItem, selectedVillager, attemptsCounter, data);
        }
    }

    public abstract static class Task {
        public Task() {
            Texts.getImpl().sendTranslatableFeedback("task: " + this.getClass().getSimpleName());
        }

        public abstract void work(TaskContext taskContext) throws StopTaskSignal;

        public boolean allowsBreaking() {
            return false;
        }

        public boolean allowsPlacing() {
            return false;
        }
    }

    static {
        ClientPlayConnectionEvents.JOIN.register((h, s, c) ->
                switchTask(ctx -> TaskSwitch.nextTick(new StandbyTask(), TaskContext.EMPTY))
        );
        ClientPlayConnectionEvents.DISCONNECT.register((h, c) ->
                switchTask(ctx -> TaskSwitch.nextTick(new StandbyTask(), TaskContext.EMPTY))
        );
    }
}

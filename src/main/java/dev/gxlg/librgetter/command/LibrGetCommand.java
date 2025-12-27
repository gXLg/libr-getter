package dev.gxlg.librgetter.command;

import com.mojang.brigadier.context.CommandContext;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.utils.reflection.Minecraft;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.worker.Worker;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("SameReturnValue")
public class LibrGetCommand {

    public static int remove(CommandContext<?> context) {
        return CommandHelper.manageGoals(context, true);
    }

    public static int add(CommandContext<?> context) {
        return CommandHelper.manageGoals(context, false);
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
        if (!(Worker.getCurrentTask() instanceof StandbyTask standbyTask)) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "player", "LibrGetCommand#autostart");
            return 1;
        }
        ClientWorld world = client.world;
        if (world == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "world", "LibrGetCommand#autostart");
            return 1;
        }

        BlockPos lec = null;
        for (int dis = 1; dis < 5; dis++) {
            for (int dx = -dis; dx <= dis; dx++) {
                for (int dy = -dis; dy <= dis; dy++) {
                    for (int dz = -dis; dz <= dis; dz++) {
                        if (dis != Math.abs(dx) && dis != Math.abs(dy) && dis != Math.abs(dz)) continue;

                        BlockPos pos = player.getBlockPos().add(dx, dy, dz);
                        if (world.getBlockState(pos).isOf(Blocks.LECTERN)) {
                            lec = pos;
                            break;
                        }
                    }
                    if (lec != null) break;
                }
                if (lec != null) break;
            }
            if (lec != null) break;
        }
        if (lec == null) {
            Texts.getImpl().sendTranslatableError("librgetter.find_lectern");
            return 1;
        }
        Iterable<Entity> all = world.getEntities();
        VillagerEntity vi = null;
        float d = Float.MAX_VALUE;
        for (Entity e : all) {
            if (e instanceof VillagerEntity v) {
                if (Minecraft.isVillagerLibrarian(v)) {
                    float dd = v.distanceTo(player);
                    if (dd < d && dd < 10) {
                        vi = v;
                        d = dd;
                    }
                }
            }
        }
        if (vi == null) {
            Texts.getImpl().sendTranslatableError("librgetter.find_librarian");
            return 1;
        }

        BlockPos finalLec = lec;
        VillagerEntity finalVi = vi;
        standbyTask.switchTask(ctx -> new StartTask(ctx.withLectern(finalLec).withVillager(finalVi), true));

        return 0;
    }

    public static int clearGoals() {
        LibrGetter.config.goals.clear();
        LibrGetter.config.save();
        Texts.getImpl().sendTranslatableWarning("librgetter.cleared");
        return 0;
    }

    public static int stopWorking() {
        Worker.stop();
        return 0;
    }

    public static int startWorking() {
        if (!(Worker.getCurrentTask() instanceof StandbyTask standbyTask)) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }
        standbyTask.switchTask(ctx -> new StartTask(ctx, true));

        return 0;
    }

    public static int continueWorking() {
        if (!(Worker.getCurrentTask() instanceof StandbyTask standbyTask)) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }
        standbyTask.switchTask(ctx -> new StartTask(ctx, false));

        return 0;
    }

    public static int selector() {
        if (!(Worker.getCurrentTask() instanceof StandbyTask standbyTask)) {
            Texts.getImpl().sendTranslatableError("librgetter.running");
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "world", "LibrGetCommand#selector");
            return 1;
        }
        ClientPlayerEntity player = client.player;
        if (player == null) {
            Texts.getImpl().sendTranslatableError("librgetter.internal", "player", "LibrGetCommand#selector");
            return 1;
        }
        HitResult hit = client.crosshairTarget;
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
            if (!world.getBlockState(blockPos).isOf(Blocks.LECTERN)) {
                Texts.getImpl().sendTranslatableError("librgetter.not_lectern");
                return 1;
            }

            standbyTask.updateContext(ctx -> ctx.withLectern(blockPos));
            Texts.getImpl().sendTranslatableFeedback("librgetter.lectern");

        } else if (hitType == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if (!(entity instanceof VillagerEntity villager)) {
                Texts.getImpl().sendTranslatableError("librgetter.not_villager");
                return 1;
            }
            if (!Minecraft.isVillagerLibrarian(villager)) {
                Texts.getImpl().sendTranslatableError("librgetter.not_librarian");
                return 1;
            }

            standbyTask.updateContext(ctx -> ctx.withVillager(villager));
            Texts.getImpl().sendTranslatableFeedback("librgetter.librarian");
        }

        return 0;
    }
}
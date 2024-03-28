package com.gxlg.librgetter.command;

import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.Worker;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.List;

public class LibrGetCommand {

    public static int runRemove(CommandContext<?> context) {
        return enchanter(context, true);
    }

    public static int runAdd(CommandContext<?> context) {
        return enchanter(context, false);
    }

    public static int runList(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.list();
        return 0;
    }

    public static int runNotify(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "Notification", toggle);
        LibrGetter.config.notify = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runTool(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "AutoTool", toggle);
        LibrGetter.config.autoTool = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runActionBar(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "ActionBar", toggle);
        LibrGetter.config.actionBar = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runLock(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "Lock", toggle);
        LibrGetter.config.lock = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runRemoveGoal(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "RemoveGoal", toggle);
        LibrGetter.config.removeGoal = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runCheckUpdate(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "CheckUpdate", toggle);
        LibrGetter.config.checkUpdate = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runWarning(CommandContext<?> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.config", null, "Warning", toggle);
        LibrGetter.config.warning = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int runAutostart(CommandContext<?> context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.internal", "player");
            return 1;
        }
        ClientWorld world = client.world;
        if (world == null) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.internal", "world");
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
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.find_lectern");
            return 1;
        }
        Iterable<Entity> all = world.getEntities();
        VillagerEntity vi = null;
        float d = -1;
        for (Entity e : all) {
            if (e instanceof VillagerEntity) {
                VillagerEntity v = (VillagerEntity) e;
                if (v.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN) {
                    float dd = v.distanceTo(player);
                    if ((d == -1 || dd < d) && dd < 10) {
                        vi = v;
                        d = dd;
                    }
                }
            }
        }
        if (vi == null) {
            LibrGetter.MULTI.sendError(context, "librgetter.find_librarian");
            return 1;
        }

        Worker.setSource(context.getSource());
        Worker.setBlock(lec);
        Worker.setVillager(vi);
        Worker.begin();

        return 0;
    }

    private static int enchanter(CommandContext<?> context, boolean remove) {
        List<Either<Enchantment, String>> list = new ArrayList<>();

        if (!LibrGetter.MULTI.getEnchantments(list, context)) return 1;

        int lvl = -1;
        try {
            lvl = context.getArgument("level", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        int price = 64;
        try {
            price = context.getArgument("maxprice", Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        for (Either<Enchantment, String> item : list) {
            if (item.left().isPresent()) {
                Enchantment enchantment = item.left().get();

                Identifier id = LibrGetter.MULTI.enchantmentId(enchantment);

                if (lvl > enchantment.getMaxLevel() && LibrGetter.config.warning) {
                    LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.level", Formatting.YELLOW, id, enchantment.getMaxLevel());
                }
                int level = lvl;
                if (lvl == -1) level = enchantment.getMaxLevel();


                if (!enchantment.isAvailableForEnchantedBookOffer() && LibrGetter.config.warning) {
                    LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.notrade", Formatting.YELLOW, id);
                }

                if (id == null) {
                    LibrGetter.MULTI.sendError(context.getSource(), "librgetter.internal", "id");
                    return 1;
                }

                Worker.setSource(context.getSource());
                if (remove)
                    Worker.remove(id.toString(), level);
                else
                    Worker.add(id.toString(), level, price, false);
            } else if (item.right().isPresent()) {
                String custom = item.right().get();

                Identifier enchantment = Identifier.tryParse(custom);
                if (enchantment == null) {
                    LibrGetter.MULTI.sendError(context.getSource(), "librgetter.parse");
                    return 1;
                }

                if (!remove && LibrGetter.config.warning)
                    LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.custom", Formatting.YELLOW, enchantment);

                Worker.setSource(context.getSource());
                if (remove)
                    Worker.remove(enchantment.toString(), lvl);
                else
                    Worker.add(enchantment.toString(), lvl, price, true);
            }
        }

        return 0;
    }

    public static int runClear(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.clear();
        return 0;
    }

    public static int runStop(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.stop();
        return 0;
    }

    public static int runStart(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.begin();
        return 0;
    }

    public static int runSelector(CommandContext<?> context) {

        Worker.setSource(context.getSource());
        if (Worker.getState() != Worker.State.STANDBY) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.running");
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.internal", "world");
            return 1;
        }
        ClientPlayerEntity player = client.player;
        if (player == null) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.internal", "player");
            return 1;
        }
        HitResult hit = client.crosshairTarget;
        if (hit == null) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.internal", "hit");
            return 1;
        }
        HitResult.Type hitType = hit.getType();
        if (hitType == HitResult.Type.MISS) {
            LibrGetter.MULTI.sendError(context.getSource(), "librgetter.nothing");
            return 1;
        }

        if (hitType == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).isOf(Blocks.LECTERN)) {
                LibrGetter.MULTI.sendError(context.getSource(), "librgetter.not_lectern");
                return 1;
            }

            Worker.setBlock(blockPos);
            LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.lectern", null);

        } else if (hitType == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if (!(entity instanceof VillagerEntity)) {
                LibrGetter.MULTI.sendError(context.getSource(), "librgetter.not_villager");
                return 1;
            }
            VillagerEntity villager = (VillagerEntity) entity;
            if (villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN) {
                LibrGetter.MULTI.sendError(context.getSource(), "librgetter.not_librarian");
                return 1;
            }
            LibrGetter.MULTI.sendFeedback(context.getSource(), "librgetter.librarian", null);
            Worker.setVillager(villager);

        }

        return 0;
    }
}
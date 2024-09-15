package com.gxlg.librgetter.command;

import com.gxlg.librgetter.Config;
import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.Worker;
import com.gxlg.librgetter.utils.Commands;
import com.gxlg.librgetter.utils.Messages;
import com.gxlg.librgetter.utils.Minecraft;
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

@SuppressWarnings("SameReturnValue")
public class LibrGetCommand {

    public static int remove(CommandContext<?> context) {
        return enchanter(context, true);
    }

    public static int add(CommandContext<?> context) {
        return enchanter(context, false);
    }

    public static int list(CommandContext<?> context) {
        Messages.list(context.getSource());
        return 0;
    }

    public static int config(CommandContext<?> context, String config) {
        boolean toggle = Config.getBoolean(config);
        try {
            toggle = context.getArgument("toggle", Boolean.class);
        } catch (IllegalArgumentException ignored) {
        }
        Messages.sendFeedback(context.getSource(), "librgetter.config", null, config, toggle);
        Config.setBoolean(config, toggle);
        LibrGetter.saveConfigs();
        return 0;
    }

    public static int autostart(CommandContext<?> context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            Messages.sendError(context.getSource(), "librgetter.internal", "player");
            return 1;
        }
        ClientWorld world = client.world;
        if (world == null) {
            Messages.sendError(context.getSource(), "librgetter.internal", "world");
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
            Messages.sendError(context.getSource(), "librgetter.find_lectern");
            return 1;
        }
        Iterable<Entity> all = world.getEntities();
        VillagerEntity vi = null;
        float d = -1;
        for (Entity e : all) {
            if (e instanceof VillagerEntity v) {
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
            Messages.sendError(context.getSource(), "librgetter.find_librarian");
            return 1;
        }

        Worker.setSource(context.getSource());
        Worker.setBlock(lec);
        Worker.setVillager(vi);
        Worker.start();

        return 0;
    }

    private static int enchanter(CommandContext<?> context, boolean remove) {
        List<Either<Enchantment, String>> list = new ArrayList<>();
        if (!Commands.getEnchantments(list, context)) return 1;

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

                Identifier id = Minecraft.enchantmentId(enchantment);

                if (lvl > enchantment.getMaxLevel() && LibrGetter.config.warning) {
                    Messages.sendFeedback(context.getSource(), "librgetter.level", Formatting.YELLOW, id, enchantment.getMaxLevel());
                }
                int level = lvl;
                if (lvl == -1) level = enchantment.getMaxLevel();

                if (!Minecraft.canBeTraded(enchantment) && LibrGetter.config.warning) {
                    Messages.sendFeedback(context.getSource(), "librgetter.notrade", Formatting.YELLOW, id);
                }

                if (id == null) {
                    Messages.sendError(context.getSource(), "librgetter.internal", "id");
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
                    Messages.sendError(context.getSource(), "librgetter.parse");
                    return 1;
                }

                if (!remove && LibrGetter.config.warning)
                    Messages.sendFeedback(context.getSource(), "librgetter.custom", Formatting.YELLOW, enchantment);

                Worker.setSource(context.getSource());
                if (remove)
                    Worker.remove(enchantment.toString(), lvl);
                else
                    Worker.add(enchantment.toString(), lvl, price, true);
            }
        }

        return 0;
    }

    public static int clear(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.clear();
        return 0;
    }

    public static int stop(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.stop();
        return 0;
    }

    public static int start(CommandContext<?> context) {
        Worker.setSource(context.getSource());
        Worker.start();
        return 0;
    }

    public static int selector(CommandContext<?> context) {

        Worker.setSource(context.getSource());
        if (Worker.getState() != Worker.State.STANDBY) {
            Messages.sendError(context.getSource(), "librgetter.running");
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) {
            Messages.sendError(context.getSource(), "librgetter.internal", "world");
            return 1;
        }
        ClientPlayerEntity player = client.player;
        if (player == null) {
            Messages.sendError(context.getSource(), "librgetter.internal", "player");
            return 1;
        }
        HitResult hit = client.crosshairTarget;
        if (hit == null) {
            Messages.sendError(context.getSource(), "librgetter.internal", "hit");
            return 1;
        }
        HitResult.Type hitType = hit.getType();
        if (hitType == HitResult.Type.MISS) {
            Messages.sendError(context.getSource(), "librgetter.nothing");
            return 1;
        }

        if (hitType == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).isOf(Blocks.LECTERN)) {
                Messages.sendError(context.getSource(), "librgetter.not_lectern");
                return 1;
            }

            Worker.setBlock(blockPos);
            Messages.sendFeedback(context.getSource(), "librgetter.lectern", null);

        } else if (hitType == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if (!(entity instanceof VillagerEntity villager)) {
                Messages.sendError(context.getSource(), "librgetter.not_villager");
                return 1;
            }
            if (villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN) {
                Messages.sendError(context.getSource(), "librgetter.not_librarian");
                return 1;
            }
            Messages.sendFeedback(context.getSource(), "librgetter.librarian", null);
            Worker.setVillager(villager);

        }

        return 0;
    }
}
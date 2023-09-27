package com.gxlg.librgetter.command;

import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.Worker;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryPredicateArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibrGetCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager
                .literal("librget")
                .then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("enchantment", RegistryEntryPredicateArgumentType.registryEntryPredicate(registryAccess, Registries.ENCHANTMENT.getKey()))
                                .executes(LibrGetCommand::runAdd)
                                .then(ClientCommandManager.argument("level", IntegerArgumentType.integer(1))
                                        .executes(LibrGetCommand::runAdd)
                                        .then(ClientCommandManager.argument("maxprice", IntegerArgumentType.integer(1, 64))
                                                .executes(LibrGetCommand::runAdd)))))
                .then(ClientCommandManager.literal("remove")
                        .then(ClientCommandManager.argument("enchantment", RegistryEntryPredicateArgumentType.registryEntryPredicate(registryAccess, Registries.ENCHANTMENT.getKey()))
                                .then(ClientCommandManager.argument("level", IntegerArgumentType.integer(1))
                                        .executes(LibrGetCommand::runRemove))))
                .then(ClientCommandManager.literal("clear")
                        .executes(LibrGetCommand::runClear))
                .then(ClientCommandManager.literal("list")
                        .executes(LibrGetCommand::runList))
                .then(ClientCommandManager.literal("stop")
                        .executes(LibrGetCommand::runStop))
                .then(ClientCommandManager.literal("start")
                        .executes(LibrGetCommand::runStart))
                .then(ClientCommandManager.literal("auto")
                        .executes(LibrGetCommand::runAutostart))
                .then(ClientCommandManager.literal("config")
                        .then(ClientCommandManager.literal("notify")
                                .then(ClientCommandManager.argument("toggle", BoolArgumentType.bool())
                                        .executes(LibrGetCommand::runNotify)))
                        .then(ClientCommandManager.literal("autotool")
                                .then(ClientCommandManager.argument("toggle", BoolArgumentType.bool())
                                        .executes(LibrGetCommand::runTool)))
                        .then(ClientCommandManager.literal("actionbar")
                                .then(ClientCommandManager.argument("toggle", BoolArgumentType.bool())
                                        .executes(LibrGetCommand::runActionBar)))
                        .then(ClientCommandManager.literal("lock")
                                .then(ClientCommandManager.argument("toggle", BoolArgumentType.bool())
                                        .executes(LibrGetCommand::runLock)))
                        .then(ClientCommandManager.literal("removegoal")
                                .then(ClientCommandManager.argument("toggle", BoolArgumentType.bool())
                                        .executes(LibrGetCommand::runRemoveGoal)))
                        .executes(LibrGetCommand::runSelector)
                )
        );
    }

    private static int runRemove(CommandContext<FabricClientCommandSource> context) {
        return enchanter(context, true);
    }

    private static int runAdd(CommandContext<FabricClientCommandSource> context) {
        return enchanter(context, false);
    }

    private static int runList(CommandContext<FabricClientCommandSource> context) {
        Worker.setSource(context.getSource());
        Worker.list();
        return 0;
    }

    private static int runNotify(CommandContext<FabricClientCommandSource> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        context.getSource().sendFeedback(Text.literal("Notification config was set to " + toggle));
        LibrGetter.config.notify = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    private static int runTool(CommandContext<FabricClientCommandSource> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        context.getSource().sendFeedback(Text.literal("AutoTool config was set to " + toggle));
        LibrGetter.config.autoTool = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    private static int runActionBar(CommandContext<FabricClientCommandSource> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        context.getSource().sendFeedback(Text.literal("ActionBar config was set to " + toggle));
        LibrGetter.config.actionBar = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    private static int runLock(CommandContext<FabricClientCommandSource> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        context.getSource().sendFeedback(Text.literal("Lock config was set to " + toggle));
        LibrGetter.config.lock = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    private static int runRemoveGoal(CommandContext<FabricClientCommandSource> context) {
        boolean toggle = context.getArgument("toggle", Boolean.class);
        context.getSource().sendFeedback(Text.literal("RemoveGoal config was set to " + toggle));
        LibrGetter.config.removeGoal = toggle;
        LibrGetter.saveConfigs();
        return 0;
    }

    private static int runAutostart(CommandContext<FabricClientCommandSource> context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            context.getSource().sendError(Text.literal("InternalError: player == null"));
            return 1;
        }
        ClientWorld world = client.world;
        if (world == null) {
            context.getSource().sendError(Text.literal("InternalError: world == null"));
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
            context.getSource().sendError(Text.literal("Could not find a lectern near you!"));
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
            context.getSource().sendError(Text.literal("Could not find a Librarian near you!"));
            return 1;
        }

        Worker.setSource(context.getSource());
        Worker.setBlock(lec);
        Worker.setVillager(vi);
        Worker.begin();

        return 0;
    }

    private static int enchanter(CommandContext<FabricClientCommandSource> context, boolean remove) {
        RegistryEntryPredicateArgumentType.EntryPredicate<?> argument = context.getArgument("enchantment", RegistryEntryPredicateArgumentType.EntryPredicate.class);
        Optional<RegistryEntryPredicateArgumentType.EntryPredicate<Enchantment>> opt = argument.tryCast(Registries.ENCHANTMENT.getKey());
        if(!opt.isPresent()){
            context.getSource().sendError(Text.literal("This argument type is not supported!"));
            return 1;
        }
        RegistryEntryPredicateArgumentType.EntryPredicate<Enchantment> predicate = opt.get();
        Optional<RegistryEntry.Reference<Enchantment>> optrefl = predicate.getEntry().left();
        Optional<RegistryEntryList.Named<Enchantment>> optrefr = predicate.getEntry().right();

        List<Enchantment> list = new ArrayList<>();

        if(!optrefl.isPresent()){
            if(!optrefr.isPresent()){
                context.getSource().sendError(Text.literal("Wrong enchantment provided!"));
                return 1;
            }
            RegistryEntryList.Named<Enchantment> named = optrefr.get();
            for(RegistryEntry<Enchantment> ref: named){
                list.add(ref.value());
            }
        } else {
            RegistryEntry.Reference<Enchantment> reference = optrefl.get();
            Enchantment enchantment = reference.value();
            list.add(enchantment);
        }

        int lvl = -1;
        try {
            lvl = context.getArgument("level", Integer.class);
        } catch (IllegalArgumentException ignored) { }

        for(Enchantment enchantment : list) {
            Identifier id = Registries.ENCHANTMENT.getId(enchantment);

            if (lvl > enchantment.getMaxLevel()) {
                context.getSource().sendError(Text.literal("Level for " + id + " over the max! Max level: " + enchantment.getMaxLevel()));
                continue;
            }
            int level = lvl;
            if(lvl == -1) level = enchantment.getMaxLevel();

            int price = 64;
            try {
                price = context.getArgument("maxprice", Integer.class);
            } catch (IllegalArgumentException ignored) {
            }

            if (!enchantment.isAvailableForEnchantedBookOffer()) {
                context.getSource().sendError(Text.literal( id + " can not be traded by villagers!"));
                continue;
            }

            if (id == null) {
                context.getSource().sendError(Text.literal("InternalError: id == null"));
                return 1;
            }

            Worker.setSource(context.getSource());
            if (remove)
                Worker.remove(id.toString(), level);
            else
                Worker.add(id.toString(), level, price);
        }

        return 0;
    }

    private static int runClear(CommandContext<FabricClientCommandSource> context) {
        Worker.setSource(context.getSource());
        Worker.clear();
        return 0;
    }

    private static int runStop(CommandContext<FabricClientCommandSource> context) {
        Worker.setSource(context.getSource());
        Worker.stop();
        return 0;
    }

    private static int runStart(CommandContext<FabricClientCommandSource> context) {
        Worker.setSource(context.getSource());
        Worker.begin();
        return 0;
    }

    private static int runSelector(CommandContext<FabricClientCommandSource> context) {

        Worker.setSource(context.getSource());
        if (Worker.getState() != Worker.State.STANDBY) {
            context.getSource().sendError(Text.literal("LibrGetter is running!"));
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if (world == null) {
            context.getSource().sendError(Text.literal("InternalError: world == null"));
            return 1;
        }
        ClientPlayerEntity player = client.player;
        if (player == null) {
            context.getSource().sendError(Text.literal("InternalError: player == null"));
            return 1;
        }
        HitResult hit = client.crosshairTarget;
        if (hit == null) {
            context.getSource().sendError(Text.literal("InternalError: hit == null"));
            return 1;
        }
        HitResult.Type hitType = hit.getType();
        if (hitType == HitResult.Type.MISS) {
            context.getSource().sendError(Text.literal("You are not targeting anything!"));
            return 1;
        }

        if (hitType == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).isOf(Blocks.LECTERN)) {
                context.getSource().sendError(Text.literal("Block is not a lectern!"));
                return 1;
            }

            Worker.setBlock(blockPos);
            context.getSource().sendFeedback(Text.literal("Block selected"));

        } else if (hitType == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if (!(entity instanceof VillagerEntity)) {
                context.getSource().sendError(Text.literal("Entity is not a villager!"));
                return 1;
            }
            VillagerEntity villager = (VillagerEntity) entity;
            if (villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN) {
                context.getSource().sendError(Text.literal("Villager is not a librarian!"));
                return 1;
            }
            context.getSource().sendFeedback(Text.literal("Villager selected"));
            Worker.setVillager(villager);

        }

        return 0;
    }
}

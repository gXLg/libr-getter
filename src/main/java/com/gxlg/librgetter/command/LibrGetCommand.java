package com.gxlg.librgetter.command;

import com.gxlg.librgetter.Worker;
import com.mojang.brigadier.CommandDispatcher;
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
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
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
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess){
        dispatcher.register(ClientCommandManager
                .literal("librget")
                .then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("enchantment", RegistryEntryPredicateArgumentType.registryEntryPredicate(registryAccess, Registries.ENCHANTMENT.getKey()))
                                .then(ClientCommandManager.argument("level", IntegerArgumentType.integer(1))
                                        .executes(LibrGetCommand::runAdd))))
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
                .executes(LibrGetCommand::runSelector)
        );
    }

    private static int runRemove(CommandContext<FabricClientCommandSource> context){
        return enchanter(context, true);
    }

    private static int runAdd(CommandContext<FabricClientCommandSource> context){
        return enchanter(context, false);
    }

    private static int runList(CommandContext<FabricClientCommandSource> context){
        Worker.setSource(context.getSource());
        Worker.list();
        return 0;
    }


    private static int enchanter(CommandContext<FabricClientCommandSource> context, boolean remove){

        RegistryEntryPredicateArgumentType.EntryPredicate<?> argument = context.getArgument("enchantment", RegistryEntryPredicateArgumentType.EntryPredicate.class);
        Optional<RegistryEntryPredicateArgumentType.EntryPredicate<Enchantment>> opt = argument.tryCast(Registries.ENCHANTMENT.getKey());
        if(!opt.isPresent()){
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("This argument type is not supported!")).formatted(Formatting.RED));
            return 1;
        }

        RegistryEntryPredicateArgumentType.EntryPredicate<Enchantment> predicate = opt.get();
        Optional<RegistryEntry.Reference<Enchantment>> optrefl = predicate.getEntry().left();
        Optional<RegistryEntryList.Named<Enchantment>> optrefr = predicate.getEntry().right();

        List<Enchantment> list = new ArrayList<>();

        if(!optrefl.isPresent()){
            if(!optrefr.isPresent()){
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Wrong enchantment provided!")).formatted(Formatting.RED));
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
        int level = context.getArgument("level", Integer.class);

        for(Enchantment enchantment: list){
            Identifier id = Registries.ENCHANTMENT.getId(enchantment);
            if (id == null) {
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("InternalError: id == null")).formatted(Formatting.RED));
                return 1;
            }

            if (level > enchantment.getMaxLevel()) {
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Level over the max for " + id + "! Max level: " + enchantment.getMaxLevel())).formatted(Formatting.RED));
                return 1;
            }

            if (!enchantment.isAvailableForEnchantedBookOffer()) {
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent(id + " can not be traded by villagers!")).formatted(Formatting.RED));
                return 1;
            }

            Worker.setSource(context.getSource());
            if (remove)
                Worker.remove(id.toString(), level);
            else
                Worker.add(id.toString(), level);

        }
        return 0;
    }
    private  static int runClear(CommandContext<FabricClientCommandSource> context){
        Worker.setSource(context.getSource());
        Worker.clear();
        return 0;
    }
    private static int runStop(CommandContext<FabricClientCommandSource> context){
        Worker.setSource(context.getSource());
        Worker.stop();
        return 0;
    }
    private static int runStart(CommandContext<FabricClientCommandSource> context){
        Worker.setSource(context.getSource());
        Worker.begin();
        return 0;
    }
    private static int runSelector(CommandContext<FabricClientCommandSource> context){

        Worker.setSource(context.getSource());
        if(Worker.getState() != Worker.State.STANDBY){
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("LibrGetter is running!")).formatted(Formatting.RED));
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if(world == null){
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("InternalError: world == null")).formatted(Formatting.RED));
            return 1;
        }
        ClientPlayerEntity player = client.player;
        if(player == null){
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("InternalError: player == null")).formatted(Formatting.RED));
            return 1;
        }
        HitResult hit = client.crosshairTarget;
        if(hit == null){
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("InternalError: hit == null")).formatted(Formatting.RED));
            return 1;
        }
        HitResult.Type hitType = hit.getType();
        if(hitType == HitResult.Type.MISS){
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("You are not targeting anything!")).formatted(Formatting.RED));
            return 1;
        }

        if(hitType == HitResult.Type.BLOCK){
            BlockPos blockPos = ((BlockHitResult)hit).getBlockPos();
            if(!world.getBlockState(blockPos).isOf(Blocks.LECTERN)){
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Block is not a lectern!")).formatted(Formatting.RED));
                return 1;
            }

            Worker.setBlock(blockPos);
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Block selected")));

        } else if(hitType == HitResult.Type.ENTITY){
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if(!(entity instanceof MerchantEntity)){
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Entity is not a villager!")).formatted(Formatting.RED));
                return 1;
            }
            VillagerEntity villager = (VillagerEntity) entity;
            if(villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN){
                context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Villager is not a librarian!")).formatted(Formatting.RED));
                return 1;
            }
            context.getSource().sendFeedback(MutableText.of(new LiteralTextContent("Villager selected")));

            Worker.setVillager(villager);

        }

        return 0;
    }
}

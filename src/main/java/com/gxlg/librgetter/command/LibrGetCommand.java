package com.gxlg.librgetter.command;

import com.gxlg.librgetter.Worker;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EnchantmentArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;

public class LibrGetCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(ClientCommandManager
                .literal("librget")
                .then(ClientCommandManager.literal("add")
                        .then(ClientCommandManager.argument("enchantment", EnchantmentArgumentType.enchantment())
                                .then(ClientCommandManager.argument("level", IntegerArgumentType.integer(1))
                                        .executes(LibrGetCommand::runAdd))))
                .then(ClientCommandManager.literal("remove")
                        .then(ClientCommandManager.argument("enchantment", EnchantmentArgumentType.enchantment())
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
        Enchantment enchantment = context.getArgument("enchantment", Enchantment.class);
        int level = context.getArgument("level", Integer.class);
        if(level > enchantment.getMaxLevel()){
            context.getSource().sendFeedback(new LiteralText("Level over the max! Max level: " + enchantment.getMaxLevel()).formatted(Formatting.RED));
            return 1;
        }

        if(!enchantment.isAvailableForEnchantedBookOffer()){
            context.getSource().sendFeedback(new LiteralText("This enchantment can not be traded by villagers!").formatted(Formatting.RED));
            return 1;
        }

        Identifier id = Registry.ENCHANTMENT.getId(enchantment);
        if(id == null){
            context.getSource().sendFeedback(new LiteralText("InternalError: id == null").formatted(Formatting.RED));
            return 1;
        }

        Worker.setSource(context.getSource());
        if(remove)
            Worker.remove(id.toString(), level);
        else
            Worker.add(id.toString(), level);

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
            context.getSource().sendFeedback(new LiteralText("LibrGetter is running!").formatted(Formatting.RED));
            return 1;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;
        if(world == null){
            context.getSource().sendFeedback(new LiteralText("InternalError: world == null").formatted(Formatting.RED));
            return 1;
        }
        ClientPlayerEntity player = client.player;
        if(player == null){
            context.getSource().sendFeedback(new LiteralText("InternalError: player == null").formatted(Formatting.RED));
            return 1;
        }
        HitResult hit = client.crosshairTarget;
        if(hit == null){
            context.getSource().sendFeedback(new LiteralText("InternalError: hit == null").formatted(Formatting.RED));
            return 1;
        }
        HitResult.Type hitType = hit.getType();
        if(hitType == HitResult.Type.MISS){
            context.getSource().sendFeedback(new LiteralText("You are not targeting anything!").formatted(Formatting.RED));
            return 1;
        }

        if(hitType == HitResult.Type.BLOCK){
            BlockPos blockPos = ((BlockHitResult)hit).getBlockPos();
            if(!world.getBlockState(blockPos).isOf(Blocks.LECTERN)){
                context.getSource().sendFeedback(new LiteralText("Block is not a lectern!").formatted(Formatting.RED));
                return 1;
            }

            Worker.setBlock(blockPos);
            context.getSource().sendFeedback(new LiteralText("Block selected"));

        } else if(hitType == HitResult.Type.ENTITY){
            EntityHitResult entityHitResult = (EntityHitResult) hit;
            Entity entity = entityHitResult.getEntity();
            if(!(entity instanceof MerchantEntity)){
                context.getSource().sendFeedback(new LiteralText("Entity is not a villager!").formatted(Formatting.RED));
                return 1;
            }
            VillagerEntity villager = (VillagerEntity) entity;
            if(villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN){
                context.getSource().sendFeedback(new LiteralText("Villager is not a librarian!").formatted(Formatting.RED));
                return 1;
            }
            context.getSource().sendFeedback(new LiteralText("Villager selected"));

            Worker.setVillager(villager);

        }

        return 0;
    }
}

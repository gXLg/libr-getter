package com.gxlg.librgetter;

import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Worker {

    @Nullable
    private static BlockPos block;

    @Nullable
    private static TradeOfferList trades;

    @Nullable
    private static VillagerEntity villager;

    private static State state = State.STANDBY;
    public static State getState(){ return state; }

    private static FabricClientCommandSource source;

    private static final ArrayList<String> looking = new ArrayList<>();

    private static int counter;

    public static void tick(){

        if(state == State.STANDBY) return;
        if(block == null || villager == null){
            state = State.STANDBY;
            return;
        }

        if(state == State.START){
            counter ++;

            MinecraftClient client = MinecraftClient.getInstance();

            ClientPlayerEntity player = client.player;
            if(player == null){
                source.sendFeedback(new LiteralText("InternalError: player == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            PlayerInventory inventory = player.inventory;
            if(inventory == null){
                source.sendFeedback(new LiteralText("InternalError: inventory == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            int slot = -1;
            float max = -1;
            for(int i = 0; i < inventory.main.size(); i ++){
                ItemStack stack = inventory.getStack(i);
                float f = stack.getMiningSpeedMultiplier(Blocks.LECTERN.getDefaultState());
                int ef = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
                f += (float)(ef * ef + 1);
                if(f > max){
                    max = f;
                    slot = i;
                }
            }
            ClientPlayerInteractionManager manager = client.interactionManager;
            if(manager == null){
                source.sendFeedback(new LiteralText("InternalError: manager == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if(handler == null){
                source.sendFeedback(new LiteralText("InternalError: handler == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            if(slot != -1) {
                if (PlayerInventory.isValidHotbarIndex(slot))
                    inventory.selectedSlot = slot;
                else
                    manager.pickFromInventory(slot);
                UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(inventory.selectedSlot);
                handler.sendPacket(packetSelect);
            }
            state = State.BREAK;
        } else if(state == State.BREAK){
            MinecraftClient client = MinecraftClient.getInstance();

            ClientWorld world = client.world;
            if(world == null){
                source.sendFeedback(new LiteralText("InternalError: world == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            BlockState targetBlock = world.getBlockState(block);
            if(targetBlock.isAir()){
                state = State.PLACE;
                return;
            }
            ClientPlayerInteractionManager manager = client.interactionManager;
            if(manager == null){
                source.sendFeedback(new LiteralText("InternalError: manager == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            manager.updateBlockBreakingProgress(block, Direction.UP);
        } else if(state == State.PLACE){
            MinecraftClient client = MinecraftClient.getInstance();

            ClientPlayerEntity player = client.player;
            if(player == null){
                source.sendFeedback(new LiteralText("InternalError: player == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            PlayerInventory inventory = player.inventory;
            if(inventory == null){
                source.sendFeedback(new LiteralText("InternalError: inventory == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            int slot = inventory.getSlotWithStack(new ItemStack(Items.LECTERN));
            if(slot == -1) return;

            ClientPlayerInteractionManager manager = client.interactionManager;
            if(manager == null){
                source.sendFeedback(new LiteralText("InternalError: manager == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if(handler == null){
                source.sendFeedback(new LiteralText("InternalError: handler == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            if(PlayerInventory.isValidHotbarIndex(slot))
                inventory.selectedSlot = slot;
            else
                manager.pickFromInventory(slot);
            UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(inventory.selectedSlot);
            handler.sendPacket(packetSelect);

            Vec3d lowBlockPos = new Vec3d(block.getX(), block.getY() - 1, block.getZ());
            BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, block, false);
            PlayerInteractBlockC2SPacket packetSet = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, lowBlock);

            handler.sendPacket(packetSet);
            state = State.GET;
        } else if(state == State.GET){
            if(villager.getVillagerData().getProfession() == VillagerProfession.NONE) return;

            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if(handler == null){
                source.sendFeedback(new LiteralText("InternalError: handler == null").formatted(Formatting.RED));
                state = State.STANDBY;
                return;
            }
            PlayerInteractEntityC2SPacket packet = new PlayerInteractEntityC2SPacket(villager, Hand.MAIN_HAND, false);
            handler.sendPacket(packet);
            trades = null;
            state = State.GETTING;
        } else if(state == State.GETTING){
            if(trades == null) return;

            int trade;
            if(trades.get(0).getSellItem().getItem() == Items.ENCHANTED_BOOK)
                trade = 0;
            else if(trades.get(1).getSellItem().getItem() == Items.ENCHANTED_BOOK)
                trade = 1;
            else
                trade = -1;

            String enchant;
            if(trade != -1){
                NbtCompound tag = trades.get(trade).getSellItem().getTag();
                if(tag == null){
                    source.sendFeedback(new LiteralText("InternalError: tag == null").formatted(Formatting.RED));
                    state = State.STANDBY;
                    return;
                }
                NbtCompound element = (NbtCompound)tag.getList("StoredEnchantments", 10).get(0);

                NbtElement id = element.get("id");
                NbtElement lvl = element.get("lvl");
                if(id == null || lvl == null){
                    source.sendFeedback(new LiteralText("InternalError: tag == null").formatted(Formatting.RED));
                    state = State.STANDBY;
                    return;
                }
                enchant = id.asString() + "_" + lvl.asString();
            } else
                enchant = "none";

            source.sendFeedback(new LiteralText("Enchantment offered: " + enchant));

            for(String l: looking) {
                if (l.equals(enchant)) {
                    source.sendFeedback(new LiteralText("Successfully found after: " + counter + " tries").formatted(Formatting.GREEN));
                    state = State.STANDBY;
                    break;
                }
            }
            if(state != State.STANDBY)
                state = State.START;
        }
    }

    public static void begin(){
        if(state != State.STANDBY) {
            source.sendFeedback(new LiteralText("LibrGetter is already running!").formatted(Formatting.RED));
            return;
        }
        if(block == null){
            source.sendFeedback(new LiteralText("The lectern is not been set!").formatted(Formatting.RED));
            return;
        }
        if(villager == null){
            source.sendFeedback(new LiteralText("The villager is not been set!").formatted(Formatting.RED));
            return;
        }
        if(looking.isEmpty()){
            source.sendFeedback(new LiteralText("There are no entries in the goals list!").formatted(Formatting.RED));
            return;
        }
        source.sendFeedback(new LiteralText("LibrGetter process started").formatted(Formatting.GREEN));
        counter = 0;
        state = State.START;
    }
    public static void add(String newLooking){
        if(looking.contains(newLooking)){
            source.sendFeedback(new LiteralText(newLooking + " is already in the goals list!").formatted(Formatting.RED));
            return;
        }
        looking.add(newLooking);
        source.sendFeedback(new LiteralText("Added " + newLooking).formatted(Formatting.GREEN));
    }
    public static void remove(String newLooking){
        if(!looking.contains(newLooking)){
            source.sendFeedback(new LiteralText(newLooking + " is not in the goals list!").formatted(Formatting.RED));
            return;
        }
        looking.remove(newLooking);
        source.sendFeedback(new LiteralText("Removed " + newLooking).formatted(Formatting.YELLOW));
    }
    public static void clear(){
        looking.clear();
        source.sendFeedback(new LiteralText("Cleared the goals list").formatted(Formatting.YELLOW));
    }
    public static void stop(){
        if(state == State.STANDBY){
            source.sendFeedback(new LiteralText("LibrGetter isn't running").formatted(Formatting.RED));
            return;
        }
        source.sendFeedback(new LiteralText("Successfully stopped the process").formatted(Formatting.YELLOW));
        state = State.STANDBY;
    }

    public static void setBlock(@Nullable BlockPos newBlock){
        block = newBlock;
    }
    public static void setTrades(@Nullable TradeOfferList newTrades){
        trades = newTrades;
    }

    public static void setVillager(@Nullable VillagerEntity newVillager){
        villager = newVillager;
    }

    public static void setSource(FabricClientCommandSource newSource){
        source = newSource;
    }

    public static void noRefresh(){
        source.sendFeedback(new LiteralText("The villager trades can not be updated!").formatted(Formatting.RED));
        state = State.STANDBY;
    }

    public enum State {
        STANDBY,

        START,
        BREAK,
        PLACE,
        GET,
        GETTING
    }
}

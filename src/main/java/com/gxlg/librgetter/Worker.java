package com.gxlg.librgetter;

import com.mojang.datafixers.util.Either;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Worker {
    @Nullable
    private static BlockPos block;
    @Nullable
    private static ItemStack defaultAxe;
    @Nullable
    private static TradeOfferList trades;
    @Nullable
    private static VillagerEntity villager;
    private static State state = State.STANDBY;
    private static Object source;
    private static int counter;
    @Nullable
    private static Config.Enchantment enchant;
    private static int otherTrade = 0;
    private static int lockType;

    public static State getState() {
        return state;
    }

    public static void tick() {

        if (state == State.STANDBY) return;
        if (block == null || villager == null) {
            LibrGetter.MULTI.sendError(source, "librgetter.specify");
            state = State.STANDBY;
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            LibrGetter.MULTI.sendError(source, "librgetter.internal", "player");
            state = State.STANDBY;
            return;
        }
        if (!block.isWithinDistance(player.getPos(), 3.4f) || villager.distanceTo(player) > 3.4f) {
            LibrGetter.MULTI.sendError(source, "librgetter.far");
            state = State.STANDBY;
            return;
        }

        if (state == State.START) {
            counter++;

            PlayerInventory inventory = LibrGetter.MULTI.getInventory(player);
            if (inventory == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "inventory");
                state = State.STANDBY;
                return;
            }
            int slot = -1;

            if (LibrGetter.config.autoTool) {
                float max = -1;
                for (int i = 0; i < inventory.main.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack.isDamageable() && stack.getMaxDamage() - stack.getDamage() < 10) continue;
                    float f = stack.getMiningSpeedMultiplier(Blocks.LECTERN.getDefaultState());
                    int ef = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
                    if (stack.getItem() instanceof AxeItem) f += (float) (ef * ef + 1);
                    if (f > max) {
                        max = f;
                        slot = i;
                    }
                }
            } else {
                if (defaultAxe == null || !defaultAxe.isDamageable()) {
                    state = State.BREAK;
                    return;
                }
                for (int i = 0; i < inventory.main.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (ItemStack.areEqual(stack, defaultAxe)) {
                        slot = i;
                        break;
                    }
                }
            }
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "manager");
                state = State.STANDBY;
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "handler");
                state = State.STANDBY;
                return;
            }
            if (slot != -1) {
                if (PlayerInventory.isValidHotbarIndex(slot)) inventory.selectedSlot = slot;
                else manager.pickFromInventory(slot);
                UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(inventory.selectedSlot);
                LibrGetter.MULTI.getConnection(handler).send(packetSelect);
            }
            state = State.BREAK;
        } else if (state == State.BREAK) {

            ClientWorld world = client.world;
            if (world == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "world");
                state = State.STANDBY;
                return;
            }
            BlockState targetBlock = world.getBlockState(block);
            if (targetBlock.isAir()) {
                state = State.LOSE;
                return;
            }
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "manager");
                state = State.STANDBY;
                return;
            }
            manager.updateBlockBreakingProgress(block, Direction.UP);
        } else if (state == State.LOSE) {
            if (villager.getVillagerData().getProfession() != VillagerProfession.NONE) return;
            state = State.SELECT;
        } else if (state == State.SELECT) {
            PlayerInventory inventory = LibrGetter.MULTI.getInventory(player);
            if (inventory == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "inventory");
                state = State.STANDBY;
                return;
            }
            int slot = inventory.getSlotWithStack(new ItemStack(Items.LECTERN));
            if (slot == -1) return;

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "manager");
                state = State.STANDBY;
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "handler");
                state = State.STANDBY;
                return;
            }
            if (PlayerInventory.isValidHotbarIndex(slot)) inventory.selectedSlot = slot;
            else manager.pickFromInventory(slot);
            UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(inventory.selectedSlot);
            LibrGetter.MULTI.getConnection(handler).send(packetSelect);


            state = State.PLACE;

        } else if (state == State.PLACE) {

            ClientWorld world = LibrGetter.MULTI.getWorld(player);
            if (world.getBlockState(block).isOf(Blocks.LECTERN)) state = State.GET;

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "manager");
                state = State.STANDBY;
                return;
            }
            Vec3d lowBlockPos = new Vec3d(block.getX(), block.getY() - 1, block.getZ());
            player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, lowBlockPos.add(0.5, 1, 0.5));
            BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, block.down(), false);
            LibrGetter.MULTI.interactBlock(manager, player, lowBlock);

        } else if (state == State.GET) {
            if (villager.getVillagerData().getProfession() == VillagerProfession.NONE) return;
            if (villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN) {
                LibrGetter.MULTI.sendError(source, "librgetter.pick");
                state = State.STANDBY;
                return;
            }

            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "handler");
                state = State.STANDBY;
                return;
            }
            trades = null;
            state = State.GETTING;
            PlayerInteractEntityC2SPacket packet = LibrGetter.MULTI.interactPacket(villager);


            LibrGetter.MULTI.getConnection(handler).send(packet);
        } else if (state == State.GETTING) {
            if (trades == null) return;
            getEnchant();

            LibrGetter.MULTI.sendMessage(player, "librgetter.offer", LibrGetter.config.actionBar, enchant);
            if (enchant != null) {
                for (Config.Enchantment l : LibrGetter.config.goals) {
                    if (l.meets(enchant)) {
                        LibrGetter.MULTI.sendFeedback(source, "librgetter.found", Formatting.GREEN, enchant, counter, enchant.price);
                        if (LibrGetter.config.lock) {
                            ClientPlayNetworkHandler handler = client.getNetworkHandler();
                            if (handler == null) {
                                LibrGetter.MULTI.sendError(source, "librgetter.internal", "handler");
                                state = State.STANDBY;
                                return;
                            }
                            lockType = getLockType(player);
                            state = State.LOCK;
                            trades = null;
                            PlayerInteractEntityC2SPacket packet = LibrGetter.MULTI.interactPacket(villager);
                            LibrGetter.MULTI.getConnection(handler).send(packet);
                        } else {
                            state = State.STANDBY;
                        }
                        if (LibrGetter.config.notify) {
                            if (client.world == null) {
                                LibrGetter.MULTI.sendError(source, "librgetter.internal", "world");
                            } else {
                                client.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 10.0F, 0.7F);
                            }
                        }
                        if (LibrGetter.config.removeGoal) remove(enchant.id, enchant.lvl);
                        break;
                    }
                }
            }
            if (state == State.GETTING) state = State.START;
        } else if (state == State.LOCK) {
            if (trades == null) return;
            if (enchant == null) return;
            if (lockType == -1) {
                LibrGetter.MULTI.sendError(source, "librgetter.lock");
                state = State.STANDBY;
                return;
            }

            MerchantScreen screen = (MerchantScreen) client.currentScreen;
            if (screen == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "screen");
                state = State.STANDBY;
                return;
            }

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                LibrGetter.MULTI.sendError(source, "librgetter.internal", "manager");
                state = State.STANDBY;
                return;
            }

            if (lockType == 0) {
                if (screen.getScreenHandler().getSlot(0).inventory.getStack(0).getCount() < 1) {
                    int slot = LibrGetter.MULTI.getInventory(player).getSlotWithStack(Items.BOOK.getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(screen.getScreenHandler().syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(screen.getScreenHandler().syncId, 0, 0, SlotActionType.PICKUP, player);
                    return;
                }
                if (screen.getScreenHandler().getSlot(0).inventory.getStack(1).getCount() < enchant.price) {
                    int slot = LibrGetter.MULTI.getInventory(player).getSlotWithStack(Items.EMERALD.getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(screen.getScreenHandler().syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(screen.getScreenHandler().syncId, 1, 0, SlotActionType.PICKUP, player);
                    return;
                }
            } else if (lockType == 1) {
                ItemStack item = trades.get(otherTrade).getAdjustedFirstBuyItem();
                if (screen.getScreenHandler().getSlot(0).inventory.getStack(0).getCount() < item.getCount()) {
                    int slot = LibrGetter.MULTI.getInventory(player).getSlotWithStack(item.getItem().getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(screen.getScreenHandler().syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(screen.getScreenHandler().syncId, 0, 0, SlotActionType.PICKUP, player);
                    return;
                }
            }
            manager.clickSlot(screen.getScreenHandler().syncId, 2, 0, SlotActionType.PICKUP, player);
            state = State.STANDBY;
        }
    }

    private static int getLockType(ClientPlayerEntity player) {
        int emerald = 0;
        int book = 0;
        int paper = 0;
        for (int i = 0; i < LibrGetter.MULTI.getInventory(player).size(); i++) {
            ItemStack stack = LibrGetter.MULTI.getInventory(player).getStack(i);
            if (stack.getItem() == Items.EMERALD) emerald += stack.getCount();
            if (stack.getItem() == Items.BOOK) book += stack.getCount();
            if (stack.getItem() == Items.PAPER) paper += stack.getCount();
        }
        int max;
        if (enchant == null) {
            max = 0;
            for (Config.Enchantment enchantment : LibrGetter.config.goals)
                if (enchantment.price > max) max = enchantment.price;
        } else max = enchant.price;

        if (book == 0 || emerald < max) {
            if (emerald < 9 || paper < 24) {
                return -1;
            }
            return 1;
        }
        return 0;
    }

    private static void getEnchant() {
        if (trades == null) return;

        int trade;
        Item f = trades.get(0).getSellItem().getItem();
        Item s = trades.get(1).getSellItem().getItem();
        if (f == Items.BOOK || f == Items.ENCHANTED_BOOK) {
            trade = 0;
            otherTrade = 1;
        } else if (s == Items.BOOK || s == Items.ENCHANTED_BOOK) {
            trade = 1;
            otherTrade = 0;
        } else trade = -1;

        if (trade != -1) {
            Either<Config.Enchantment, String[]> either = LibrGetter.MULTI.parseTrade(trades, trade);
            Optional<Config.Enchantment> en = either.left();
            Optional<String[]> err = either.right();
            if (err.isPresent()) {
                String[] ret = err.get();
                Object[] args = new String[ret.length - 1];
                System.arraycopy(ret, 1, args, 0, ret.length - 1);

                LibrGetter.MULTI.sendError(source, ret[0], args);
                state = State.STANDBY;
                return;
            }
            en.ifPresent(enc -> enchant = enc);

        } else {
            enchant = null;
        }
    }

    public static void begin() {
        if (state != State.STANDBY) {
            LibrGetter.MULTI.sendError(source, "librgetter.running");
            return;
        }
        if (block == null) {
            LibrGetter.MULTI.sendError(source, "librgetter.no_lectern");
            return;
        }
        if (villager == null) {
            LibrGetter.MULTI.sendError(source, "librgetter.no_librarian");
            return;
        }
        if (LibrGetter.config.goals.isEmpty()) {
            LibrGetter.MULTI.sendError(source, "librgetter.goals");
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            LibrGetter.MULTI.sendError(source, "librgetter.internal", "player");
            return;
        }

        if (!LibrGetter.config.autoTool) defaultAxe = player.getMainHandStack();

        if (LibrGetter.config.lock) {
            if (getLockType(player) == -1) {
                LibrGetter.MULTI.sendError(source, "librgetter.lock");
                return;
            }
        }

        LibrGetter.MULTI.sendFeedback(source, "librgetter.start", Formatting.GREEN);
        counter = 0;
        state = State.GET;
    }

    public static void add(String name, int level, int price, boolean custom) {
        Config.Enchantment newLooking = new Config.Enchantment(name, level, price);
        Config.Enchantment already = null;
        for (Config.Enchantment l : LibrGetter.config.goals) {
            if (l.same(newLooking)) {
                already = l;
                break;
            }
        }
        if (already != null) {
            LibrGetter.MULTI.sendFeedback(source, "librgetter.price", Formatting.GREEN, already, price);
            already.price = price;
        } else {
            LibrGetter.config.goals.add(newLooking);
            LibrGetter.MULTI.sendFeedback(source, custom ? "libgetter.add_custom" : "libgetter.add", Formatting.GREEN, newLooking, newLooking.price);
        }
        LibrGetter.saveConfigs();
    }

    public static void remove(String name, int level) {
        Config.Enchantment newLooking = new Config.Enchantment(name, level, 64);
        Config.Enchantment already = null;
        for (Config.Enchantment l : LibrGetter.config.goals) {
            if (l.same(newLooking)) {
                already = l;
                break;
            }
        }
        if (already == null) {
            LibrGetter.MULTI.sendError(source, "librgetter.not", newLooking);
            return;
        }
        LibrGetter.config.goals.remove(already);
        LibrGetter.saveConfigs();
        LibrGetter.MULTI.sendFeedback(source, "librgetter.removed", Formatting.YELLOW, newLooking);
    }

    public static void list() {
        LibrGetter.MULTI.list(source);
    }

    public static void clear() {
        LibrGetter.config.goals.clear();
        LibrGetter.saveConfigs();
        LibrGetter.MULTI.sendFeedback(source, "librgetter.cleared", Formatting.YELLOW);
    }

    public static void stop() {
        if (state == State.STANDBY) {
            LibrGetter.MULTI.sendError(source, "librgetter.not_running");
            return;
        }
        LibrGetter.MULTI.sendFeedback(source, "librgetter.stop", Formatting.YELLOW);
        state = State.STANDBY;
    }

    public static void setBlock(@Nullable BlockPos newBlock) {
        block = newBlock;
    }

    public static void setTrades(@Nullable TradeOfferList newTrades) {
        trades = newTrades;
    }

    public static void setVillager(@Nullable VillagerEntity newVillager) {
        villager = newVillager;
    }

    public static void setSource(Object newSource) {
        source = newSource;
    }

    public static void noRefresh() {
        LibrGetter.MULTI.sendError(source, "librgetter.update");
        state = State.STANDBY;
    }

    public enum State {
        STANDBY, START, BREAK, LOSE, SELECT, PLACE, GET, GETTING, LOCK
    }
}
package com.gxlg.librgetter;

import com.gxlg.librgetter.utils.PathFinding;
import com.gxlg.librgetter.utils.reflection.Minecraft;
import com.gxlg.librgetter.utils.reflection.Support;
import com.gxlg.librgetter.utils.reflection.Texts;
import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    private static LockType lockType;
    private static int timeout = 0;

    // head rotation
    private static Vec3d goalPos = null;
    private static State nextState = null;
    private static final Random rng = new Random();

    static {
        ClientPlayConnectionEvents.JOIN.register((h, s, c) -> {
            state = State.STANDBY;
            block = null;
            villager = null;
        });

        ClientPlayConnectionEvents.DISCONNECT.register((h, c) -> {
            state = State.STANDBY;
            block = null;
            villager = null;
        });
    }

    public static State getState() {
        return state;
    }

    private static void error(String msg, String... args) {
        Texts.sendError(source, msg, (Object[]) args);
        state = State.STANDBY;
    }

    public static void tick() {
        if (state == State.STANDBY) return;
        if (block == null || villager == null) {
            error("librgetter.specify");
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            error("librgetter.internal", "player");
            return;
        }

        if (!block.isWithinDistance(player.getBlockPos(), 3.4f) || villager.distanceTo(player) > 3.4f) {
            error("librgetter.far");
            return;
        }
        if (state == State.MANUAL_WAIT_FINISH) return;

        if (state == State.ROTATION) {
            double d = goalPos.getX();
            double e = goalPos.getY();
            double f = goalPos.getZ();
            double g = Math.sqrt(d * d + f * f);
            float goalPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(e, g) * 180.0D / Math.PI)));
            float goalYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(f, d) * 180.0D / Math.PI) - 90.0F);

            float currentYaw = player.getYaw();
            float currentPitch = player.getPitch();

            float yawDelta = (goalYaw - currentYaw) % 360.0F;
            if (yawDelta < -180.0F) yawDelta += 360.0F;
            if (yawDelta >= 180.0F) yawDelta -= 360.0F;

            float pitchDelta = goalPitch - currentPitch;

            // random lerping
            float newPitch = currentPitch + 0.35F * pitchDelta + (rng.nextFloat() - 0.5F) * 0.2F;
            float newYaw =  currentYaw + 0.35F * yawDelta + (rng.nextFloat() - 0.5F) * 0.2F;

            player.setPitch(newPitch);
            player.setYaw(newYaw);
            player.setHeadYaw(player.getYaw());
            player.lastPitch = player.getPitch();
            player.lastYaw = player.getYaw();
            player.lastHeadYaw = player.headYaw;
            player.bodyYaw = player.headYaw;
            player.lastBodyYaw = player.bodyYaw;

            if (Math.abs(pitchDelta) < 0.8F && Math.abs(yawDelta) < 0.8F) {
                state = nextState;
            } else return;
        }

        if (state == State.SELECT_AXE) {
            counter++;

            PlayerInventory inventory = player.getInventory();
            if (inventory == null) {
                error("librgetter.internal", "inventory" );
                return;
            }
            int slot = -1;

            if (LibrGetter.config.autoTool) {
                float max = -1;
                for (int i = 0; i < PlayerInventory.MAIN_SIZE; i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack.isDamageable() && stack.getMaxDamage() - stack.getDamage() < 10) continue;
                    float f = stack.getMiningSpeedMultiplier(Blocks.LECTERN.getDefaultState());
                    int ef = Minecraft.getEfficiencyLevel(stack);
                    if (stack.getItem() instanceof AxeItem) f += (float) (ef * ef + 1);
                    if (f > max) {
                        max = f;
                        slot = i;
                    }
                }
            } else {
                if (defaultAxe == null || !defaultAxe.isDamageable()) {
                    state = State.BREAK_LECTERN;
                    return;
                }
                for (int i = 0; i < PlayerInventory.MAIN_SIZE; i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (ItemStack.areEqual(stack, defaultAxe)) {
                        slot = i;
                        break;
                    }
                }
            }
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                error("librgetter.internal", "manager0");
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                error("librgetter.internal", "handler0");
                return;
            }
            if (slot != -1) {
                if (!PlayerInventory.isValidHotbarIndex(slot)) {
                    int syncId = player.playerScreenHandler.syncId;
                    int swap = inventory.getSwappableHotbarSlot();
                    manager.clickSlot(syncId, slot, swap, SlotActionType.SWAP, player);
                    slot = swap;
                }
                Minecraft.setSelectedSlot(inventory, slot);
                UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(slot);
                Minecraft.getConnection(handler).send(packetSelect);
            }

            prepareRotation(player, new Vec3d(block.getX() + 0.5, block.getY(), block.getZ() + 0.5), State.BREAK_LECTERN);
        }

        if (state == State.BREAK_LECTERN) {
            ClientWorld world = client.world;
            if (world == null) {
                error("librgetter.internal", "world");
                return;
            }

            BlockState targetBlock = world.getBlockState(block);
            if (!targetBlock.isAir()) {
                if (LibrGetter.config.manual) return;

                ClientPlayerInteractionManager manager = client.interactionManager;
                if (manager == null) {
                    error("librgetter.internal", "manager1");
                    return;
                }
                manager.updateBlockBreakingProgress(block, Direction.UP);
                return;
            }

            state = LibrGetter.config.waitLose ? State.WAIT_VILLAGER_LOSE_PROFESSION : State.SELECT_LECTERN_AND_PLACE;
        }

        if (state == State.WAIT_VILLAGER_LOSE_PROFESSION) {
            // If the villager doesn't update his profession because of lag,
            // we wait until the profession is lost based on the config.
            if (!Minecraft.isVillagerUnemployed(villager)) return;
            state = State.SELECT_LECTERN_AND_PLACE;
        }

        if (state == State.SELECT_LECTERN_AND_PLACE) {
            ClientWorld world = Minecraft.getWorld(player);
            if (!world.getBlockState(block).isOf(Blocks.LECTERN)) {
                if (LibrGetter.config.manual) return;

                // select
                PlayerInventory inventory = player.getInventory();
                if (inventory == null) {
                    error("librgetter.internal", "inventory");
                    return;
                }

                int slot;
                boolean mainhand = true;
                if (ItemStack.areItemsEqual(inventory.getStack(PlayerInventory.OFF_HAND_SLOT), Items.LECTERN.getDefaultStack())) {
                    slot = PlayerInventory.OFF_HAND_SLOT;
                } else {
                    slot = inventory.getSlotWithStack(Items.LECTERN.getDefaultStack());
                }
                if (slot == -1) return;

                ClientPlayerInteractionManager manager = client.interactionManager;
                if (manager == null) {
                    error("librgetter.internal", "manager2");
                    return;
                }
                ClientPlayNetworkHandler handler = client.getNetworkHandler();
                if (handler == null) {
                    error("librgetter.internal", "handler1");
                    return;
                }
                if (slot != PlayerInventory.OFF_HAND_SLOT) {
                    if (LibrGetter.config.offhand) {
                        if (PlayerInventory.isValidHotbarIndex(slot)) slot += 36;
                        manager.clickSlot(player.currentScreenHandler.syncId, slot, PlayerInventory.OFF_HAND_SLOT, SlotActionType.SWAP, player);
                        mainhand = false;
                    } else {
                        if (!PlayerInventory.isValidHotbarIndex(slot)) {
                            int syncId = player.playerScreenHandler.syncId;
                            int swap = inventory.getSwappableHotbarSlot();
                            manager.clickSlot(syncId, slot, swap, SlotActionType.SWAP, player);
                            slot = swap;
                        }
                        Minecraft.setSelectedSlot(inventory, slot);
                        UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(slot);
                        Minecraft.getConnection(handler).send(packetSelect);
                    }
                } else {
                    mainhand = false;
                }

                // place
                Vec3d lowBlockPos = new Vec3d(block.getX(), block.getY() - 1, block.getZ());
                BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, block.down(), false);
                Minecraft.interactBlock(manager, player, lowBlock, mainhand);
                return;
            }
            prepareRotation(player, EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(villager), State.WAIT_VILLAGER_ACCEPT_PROFESSION);
        }

        if (state == State.WAIT_VILLAGER_ACCEPT_PROFESSION) {
            if (Minecraft.isVillagerUnemployed(villager)) {
                if (LibrGetter.config.timeout != 0) {
                    timeout++;
                    if (timeout >= LibrGetter.config.timeout * 20) {
                        timeout = 0;
                        state = LibrGetter.config.manual ? State.BREAK_LECTERN : State.SELECT_AXE;
                    }
                }
                return;
            }
            if (!Minecraft.isVillagerLibrarian(villager)) {
                error("librgetter.pick");
                return;
            }

            timeout = 0;
            trades = null;
            state = State.GET_TRADES;
        }

        if (state == State.GET_TRADES) {
            // wait until the villager accepts profession and click him as long as trades == null
            if (trades == null) {
                if (LibrGetter.config.manual) return;

                ClientPlayNetworkHandler handler = client.getNetworkHandler();
                if (handler == null) {
                    error("librgetter.internal", "handler2");
                    return;
                }

                ClientPlayerInteractionManager manager = client.interactionManager;
                if (manager == null) {
                    error("librgetter.internal", "manager3");
                    return;
                }
                manager.interactEntity(player, villager, Hand.MAIN_HAND);
                return;
            }
            state = State.PARSE_TRADES;
        }

        if (state == State.PARSE_TRADES) {
            // parsing the trades
            getEnchant();

            Texts.sendMessage(player, "librgetter.offer", LibrGetter.config.actionBar, enchant);
            if (enchant != null) {
                for (Config.Enchantment l : LibrGetter.config.goals) {
                    if (l.meets(enchant)) {
                        Texts.sendFound(source, enchant, counter);
                        if (LibrGetter.config.notify) {
                            if (client.world == null) {
                                Texts.sendError(source, "librgetter.internal", "world");
                            } else {
                                Minecraft.playSound(client.world, player);
                            }
                        }

                        if (LibrGetter.config.manual) {
                            state = State.MANUAL_WAIT_FINISH;
                            return;
                        }

                        if (LibrGetter.config.lock) {
                            ClientPlayNetworkHandler handler = client.getNetworkHandler();
                            if (handler == null) {
                                error("librgetter.internal", "handler3");
                                return;
                            }
                            lockType = getLockType(player);
                            state = State.LOCK_TRADES;
                            trades = null;

                            if (!Support.useTradeCycling()) {
                                // TradeCycling process keeps the screen open
                                ClientPlayerInteractionManager manager = client.interactionManager;
                                if (manager == null) {
                                    error("librgetter.internal", "manager4");
                                    return;
                                }
                                manager.interactEntity(player, villager, Hand.MAIN_HAND);
                            }

                        } else {
                            state = State.STANDBY;
                        }

                        if (LibrGetter.config.removeGoal) remove(enchant.id, enchant.lvl);
                        break;
                    }
                }
            }
            if (state == State.PARSE_TRADES) {
                if (!Support.useTradeCycling()) {
                    state = LibrGetter.config.manual ? State.BREAK_LECTERN : State.SELECT_AXE;
                } else {
                    state = State.TRADECYCLING_CLICK;
                }
            }
        }

        if (state == State.LOCK_TRADES) {
            if (trades == null) return;
            if (enchant == null) return;
            if (lockType == LockType.CANNOT) {
                error("librgetter.lock");
                return;
            }

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                error("librgetter.internal", "manager5");
                return;
            }

            if (lockType == LockType.BOOK) {
                if (player.currentScreenHandler.getSlot(0).inventory.getStack(0).getCount() < 1) {
                    int slot = player.getInventory().getSlotWithStack(Items.BOOK.getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(player.currentScreenHandler.syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(player.currentScreenHandler.syncId, 0, 0, SlotActionType.PICKUP, player);
                    return;
                }
                if (player.currentScreenHandler.getSlot(0).inventory.getStack(1).getCount() < enchant.price) {
                    int slot = player.getInventory().getSlotWithStack(Items.EMERALD.getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(player.currentScreenHandler.syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(player.currentScreenHandler.syncId, 1, 0, SlotActionType.PICKUP, player);
                    return;
                }
            } else if (lockType == LockType.TRADE) {
                ItemStack item = Minecraft.getFirstBuyItem(trades.get(otherTrade));
                if (player.currentScreenHandler.getSlot(0).inventory.getStack(0).getCount() < item.getCount()) {
                    int slot = player.getInventory().getSlotWithStack(item.getItem().getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(player.currentScreenHandler.syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(player.currentScreenHandler.syncId, 0, 0, SlotActionType.PICKUP, player);
                    return;
                }
            }
            manager.clickSlot(player.currentScreenHandler.syncId, 2, 0, SlotActionType.PICKUP, player);
            state = State.STANDBY;
            return;
        }

        if (state == State.TRADECYCLING_CLICK) {
            // if the TradeCycling mod is present, send the cycle packet
            trades = null;
            Screen s = client.currentScreen;
            if (s == null) {
                // manually closing the screen means stopping
                stop();
                return;
            }
            Support.sendCycleTradesPacket();
            counter++;
            state = State.GET_TRADES;
        }
    }

    private static LockType getLockType(ClientPlayerEntity player) {
        int emerald = 0;
        int book = 0;
        int paper = 0;
        for (int i = 0; i < PlayerInventory.MAIN_SIZE; i++) {
            ItemStack stack = player.getInventory().getStack(i);
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
                return LockType.CANNOT;
            }
            return LockType.TRADE;
        }
        return LockType.BOOK;
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
            Either<Config.Enchantment, String[]> either = Minecraft.parseTrade(trades, trade);
            Optional<Config.Enchantment> en = either.left();
            Optional<String[]> err = either.right();
            if (err.isPresent()) {
                String[] ret = err.get();
                Object[] args = new String[ret.length - 1];
                System.arraycopy(ret, 1, args, 0, ret.length - 1);

                Texts.sendError(source, ret[0], args);
                state = State.STANDBY;
                return;
            }
            if (en.isPresent()) {
                Config.Enchantment e = en.get();
                if (e.same(Config.Enchantment.EMPTY)) enchant = null;
                else enchant = e;
            }

        } else {
            enchant = null;
        }
    }

    private static void prepareRotation(ClientPlayerEntity player, Vec3d target, State skip) {
        if (LibrGetter.config.look && !LibrGetter.config.manual) {
            if (LibrGetter.config.rotation) {
                Vec3d vec3d = EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(player);
                double d = target.getX() + (rng.nextFloat() - 0.5F) * 0.4F - vec3d.x;
                double e = target.getY() + (rng.nextFloat() - 0.5F) * 0.4F - vec3d.y;
                double f = target.getZ() + (rng.nextFloat() - 0.5F) * 0.4F - vec3d.z;
                goalPos = new Vec3d(d, e, f);
                state = State.ROTATION;
                nextState = skip;
            } else {
                player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target);
                state = skip;
            }
        } else {
            state = skip;
        }
    }

    public static void start() {
        if (state != State.STANDBY) {
            Texts.sendError(source, "librgetter.running");
            return;
        }
        if (block == null) {
            Texts.sendError(source, "librgetter.no_lectern");
            return;
        }
        if (villager == null) {
            Texts.sendError(source, "librgetter.no_librarian");
            return;
        }
        if (LibrGetter.config.goals.isEmpty()) {
            Texts.sendError(source, "librgetter.goals");
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            Texts.sendError(source, "librgetter.internal", "player");
            return;
        }

        if (LibrGetter.config.safeChecker) {
            ClientWorld world = client.world;
            if (world == null) {
                Texts.sendError(source, "librgetter.internal", "world");
                return;
            }
            // If the villager is sitting, assume it cannot move
            if (!villager.hasVehicle()) {
                List<BlockPos> path = PathFinding.findPath(villager.getBlockPos(), block, world, 2);
                if (path != null) {
                    Texts.sendError(source, "librgetter.unsafe");
                    return;
                }
            }
        }

        if (!LibrGetter.config.autoTool) defaultAxe = player.getMainHandStack();

        if (LibrGetter.config.lock) {
            if (getLockType(player) == LockType.CANNOT) {
                Texts.sendError(source, "librgetter.lock");
                return;
            }
        }

        Texts.sendFeedback(source, "librgetter.start", Formatting.GREEN);
        counter = 0;
        state = State.WAIT_VILLAGER_ACCEPT_PROFESSION;
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
            Texts.sendFeedback(source, "librgetter.price", Formatting.GREEN, already, price);
            already.price = price;
        } else {
            LibrGetter.config.goals.add(newLooking);
            Texts.sendFeedback(source, custom ? "libgetter.add_custom" : "libgetter.add", Formatting.GREEN, newLooking, newLooking.price);
        }
        LibrGetter.config.save();
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
            Texts.sendError(source, "librgetter.not", newLooking);
            return;
        }
        LibrGetter.config.goals.remove(already);
        LibrGetter.config.save();
        Texts.sendFeedback(source, "librgetter.removed", Formatting.YELLOW, newLooking);
    }

    public static void clear() {
        LibrGetter.config.goals.clear();
        LibrGetter.config.save();
        Texts.sendFeedback(source, "librgetter.cleared", Formatting.YELLOW);
    }

    public static void stop() {
        if (state == State.STANDBY) {
            Texts.sendError(source, "librgetter.not_running");
            return;
        }
        Texts.sendFeedback(source, "librgetter.stop", Formatting.YELLOW);
        state = State.STANDBY;
    }

    public static void setBlock(@Nullable BlockPos newBlock) {
        block = newBlock;
    }

    public static @Nullable BlockPos getBlock() {
        return block;
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

    public static Object getSource() {
        return source;
    }

    public static void noRefresh() {
        Texts.sendError(source, "librgetter.update");
        state = State.STANDBY;
    }

    public enum State {
        STANDBY, SELECT_AXE, BREAK_LECTERN, WAIT_VILLAGER_LOSE_PROFESSION, SELECT_LECTERN_AND_PLACE, WAIT_VILLAGER_ACCEPT_PROFESSION, GET_TRADES, PARSE_TRADES, LOCK_TRADES, MANUAL_WAIT_FINISH,

        ROTATION,
        TRADECYCLING_CLICK,
    }

    private enum LockType {
        CANNOT, BOOK, TRADE
    }
}
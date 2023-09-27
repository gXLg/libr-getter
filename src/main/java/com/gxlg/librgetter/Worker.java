package com.gxlg.librgetter;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtShort;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;

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

    public static State getState() {
        return state;
    }

    private static FabricClientCommandSource source;
    private static int counter;
    @Nullable
    private static Config.Enchantment enchant;
    private static int otherTrade = 0;
    private static int lockType;

    public static void tick() {

        if (state == State.STANDBY) return;
        if (block == null || villager == null) {
            source.sendError(Text.literal("Block or villager are not specified!"));
            state = State.STANDBY;
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            source.sendError(Text.literal("InternalError: player == null"));
            state = State.STANDBY;
            return;
        }
        if (!block.isWithinDistance(player.getPos(), 3.4f) || villager.distanceTo(player) > 3.4f) {
            source.sendError(Text.literal("Too far away!"));
            state = State.STANDBY;
            return;
        }

        if (state == State.START) {
            counter++;

            PlayerInventory inventory = player.getInventory();
            if (inventory == null) {
                source.sendError(Text.literal("InternalError: inventory == null"));
                state = State.STANDBY;
                return;
            }
            int slot = -1;

            if (LibrGetter.config.autoTool) {
                float max = -1;
                for (int i = 0; i < inventory.main.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack.isDamageable() && stack.getMaxDamage() - stack.getDamage() < 10)
                        continue;
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
                    if (stack.isItemEqualIgnoreDamage(defaultAxe)) {
                        slot = i;
                        break;
                    }
                }
            }
            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                source.sendError(Text.literal("InternalError: manager == null"));
                state = State.STANDBY;
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                source.sendError(Text.literal("InternalError: handler == null"));
                state = State.STANDBY;
                return;
            }
            if (slot != -1) {
                if (PlayerInventory.isValidHotbarIndex(slot))
                    inventory.selectedSlot = slot;
                else
                    manager.pickFromInventory(slot);
                UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(inventory.selectedSlot);
                handler.sendPacket(packetSelect);
            }
            state = State.BREAK;
        } else if (state == State.BREAK) {

            ClientWorld world = client.world;
            if (world == null) {
                source.sendError(Text.literal("InternalError: world == null"));
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
                source.sendError(Text.literal("InternalError: manager == null"));
                state = State.STANDBY;
                return;
            }
            manager.updateBlockBreakingProgress(block, Direction.UP);
        } else if (state == State.LOSE) {
            if (villager.getVillagerData().getProfession() != VillagerProfession.NONE) return;
            state = State.SELECT;
        } else if (state == State.SELECT) {
            PlayerInventory inventory = player.getInventory();
            if (inventory == null) {
                source.sendError(Text.literal("InternalError: inventory == null"));
                state = State.STANDBY;
                return;
            }
            int slot = inventory.getSlotWithStack(new ItemStack(Items.LECTERN));
            if (slot == -1) return;

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                source.sendError(Text.literal("InternalError: manager == null"));
                state = State.STANDBY;
                return;
            }
            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                source.sendError(Text.literal("InternalError: handler == null"));
                state = State.STANDBY;
                return;
            }
            if (PlayerInventory.isValidHotbarIndex(slot))
                inventory.selectedSlot = slot;
            else
                manager.pickFromInventory(slot);
            UpdateSelectedSlotC2SPacket packetSelect = new UpdateSelectedSlotC2SPacket(inventory.selectedSlot);
            handler.sendPacket(packetSelect);

            state = State.PLACE;

        } else if (state == State.PLACE) {

            if (player.world.getBlockState(block).isOf(Blocks.LECTERN)) state = State.GET;

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                source.sendError(Text.literal("InternalError: manager == null"));
                state = State.STANDBY;
                return;
            }
            Vec3d lowBlockPos = new Vec3d(block.getX(), block.getY() - 1, block.getZ());
            player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, lowBlockPos.add(0.5, 1, 0.5));
            BlockHitResult lowBlock = new BlockHitResult(lowBlockPos, Direction.UP, block, false);
            manager.interactBlock(player, Hand.MAIN_HAND, lowBlock);

        } else if (state == State.GET) {
            if (villager.getVillagerData().getProfession() == VillagerProfession.NONE) return;
            if (villager.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN) {
                source.sendError(Text.literal("Villager received other profession!"));
                state = State.STANDBY;
                return;
            }

            ClientPlayNetworkHandler handler = client.getNetworkHandler();
            if (handler == null) {
                source.sendError(Text.literal("InternalError: handler == null"));
                state = State.STANDBY;
                return;
            }
            state = State.GETTING;
            trades = null;
            PlayerInteractEntityC2SPacket packet = PlayerInteractEntityC2SPacket.interact(villager, false, Hand.MAIN_HAND);
            handler.sendPacket(packet);
        } else if (state == State.GETTING) {
            if (trades == null) return;
            getEnchant();

            Text msg = Text.literal("Enchantment offered: " + enchant);
            if (LibrGetter.config.actionBar)
                player.sendMessage(msg, true);
            else
                source.sendFeedback(msg);
            if (enchant != null) {
                for (Config.Enchantment l : LibrGetter.config.goals) {
                    if (l.meets(enchant)) {
                        source.sendFeedback(Text.literal("Successfully found " + enchant + " after " + counter + " tries for a price of " + enchant.price + " emeralds!").formatted(Formatting.GREEN));
                        if (LibrGetter.config.lock) {
                            ClientPlayNetworkHandler handler = client.getNetworkHandler();
                            if (handler == null) {
                                source.sendError(Text.literal("InternalError: handler == null"));
                                state = State.STANDBY;
                                return;
                            }
                            lockType = getLockType(player);
                            state = State.LOCK;
                            trades = null;
                            PlayerInteractEntityC2SPacket packet = PlayerInteractEntityC2SPacket.interact(villager, false, Hand.MAIN_HAND);
                            handler.sendPacket(packet);
                        } else {
                            state = State.STANDBY;
                        }
                        if (LibrGetter.config.notify) {
                            if (client.world == null) {
                                source.sendError(Text.literal("InternalError: world == null"));
                            } else {
                                client.world.playSound(
                                        player, player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 10.0F, 0.7F
                                );
                            }
                        }
                        if (LibrGetter.config.removeGoal) remove(enchant.id, enchant.lvl);
                        break;
                    }
                }
            }
            if (state == State.GETTING)
                state = State.START;
        } else if (state == State.LOCK) {
            if (trades == null) return;
            if (enchant == null) return;
            if (lockType == -1) {
                source.sendError(Text.literal("Not enough items to lock the trade!"));
                state = State.STANDBY;
                return;
            }

            MerchantScreen screen = (MerchantScreen) client.currentScreen;
            if (screen == null) {
                source.sendError(Text.literal("InternalError: screen == null"));
                state = State.STANDBY;
                return;
            }

            ClientPlayerInteractionManager manager = client.interactionManager;
            if (manager == null) {
                source.sendError(Text.literal("InternalError: manager == null"));
                state = State.STANDBY;
                return;
            }

            if (lockType == 0) {
                if (screen.getScreenHandler().getSlot(0).inventory.getStack(0).getCount() < 1) {
                    int slot = player.getInventory().getSlotWithStack(Items.BOOK.getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(screen.getScreenHandler().syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(screen.getScreenHandler().syncId, 0, 0, SlotActionType.PICKUP, player);
                    return;
                }
                if (screen.getScreenHandler().getSlot(0).inventory.getStack(1).getCount() < enchant.price) {
                    int slot = player.getInventory().getSlotWithStack(Items.EMERALD.getDefaultStack());
                    if (slot < 9) slot += 27;
                    else slot -= 9;
                    manager.clickSlot(screen.getScreenHandler().syncId, slot + 3, 0, SlotActionType.PICKUP, player);
                    manager.clickSlot(screen.getScreenHandler().syncId, 1, 0, SlotActionType.PICKUP, player);
                    return;
                }
            } else if (lockType == 1) {
                ItemStack item = trades.get(otherTrade).getAdjustedFirstBuyItem();
                if (screen.getScreenHandler().getSlot(0).inventory.getStack(0).getCount() < item.getCount()) {
                    int slot = player.getInventory().getSlotWithStack(item.getItem().getDefaultStack());
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
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isItemEqual(Items.EMERALD.getDefaultStack())) emerald += stack.getCount();
            if (stack.isItemEqual(Items.BOOK.getDefaultStack())) book += stack.getCount();
            if (stack.isItemEqual(Items.PAPER.getDefaultStack())) paper += stack.getCount();
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
        if (trades.get(0).getSellItem().getItem() == Items.ENCHANTED_BOOK) {
            trade = 0;
            otherTrade = 1;
        } else if (trades.get(1).getSellItem().getItem() == Items.ENCHANTED_BOOK) {
            trade = 1;
            otherTrade = 0;
        } else
            trade = -1;

        if (trade != -1) {
            NbtCompound tag = trades.get(trade).getSellItem().getNbt();
            if (tag == null) {
                source.sendError(Text.literal("InternalError: tag == null"));
                state = State.STANDBY;
                return;
            }
            NbtCompound element = (NbtCompound) tag.getList("StoredEnchantments", 10).get(0);

            NbtElement id = element.get("id");
            NbtElement lvl = element.get("lvl");

            ItemStack f = trades.get(trade).getAdjustedFirstBuyItem();
            ItemStack s = trades.get(trade).getSecondBuyItem();
            if (f.getItem() != Items.EMERALD) f = null;
            if (s.getItem() == Items.EMERALD) f = s;

            if (id == null || lvl == null || f == null) {
                source.sendError(Text.literal("InternalError: id == null or lvl == null or f == null"));
                state = State.STANDBY;
                return;
            }
            enchant = new Config.Enchantment(id.asString(), ((NbtShort) lvl).intValue(), f.getCount());
        }
    }

    public static void begin() {
        if (state != State.STANDBY) {
            source.sendError(Text.literal("LibrGetter is already running!"));
            return;
        }
        if (block == null) {
            source.sendError(Text.literal("The lectern is not been set!"));
            return;
        }
        if (villager == null) {
            source.sendError(Text.literal("The villager is not been set!"));
            return;
        }
        if (LibrGetter.config.goals.isEmpty()) {
            source.sendError(Text.literal("There are no entries in the goals list!"));
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null) {
            source.sendError(Text.literal("InternalError: player == null"));
            return;
        }

        if (!LibrGetter.config.autoTool)
            defaultAxe = player.getMainHandStack();

        if (LibrGetter.config.lock) {
            if (getLockType(player) == -1) {
                source.sendError(Text.literal("Not enough items to lock the trade!"));
                return;
            }
        }

        source.sendFeedback(Text.literal("LibrGetter process started").formatted(Formatting.GREEN));
        counter = 0;
        state = State.START;
    }

    public static void add(String name, int level, int price) {
        Config.Enchantment newLooking = new Config.Enchantment(name, level, price);
        Config.Enchantment already = null;
        for (Config.Enchantment l : LibrGetter.config.goals) {
            if (l.same(newLooking)) {
                already = l;
                break;
            }
        }
        if (already != null) {
            source.sendFeedback(Text.literal(already + " max price was changed to " + price).formatted(Formatting.GREEN));
            already.price = price;
        } else {
            LibrGetter.config.goals.add(newLooking);
            source.sendFeedback(Text.literal("Added " + newLooking + " with max price " + newLooking.price).formatted(Formatting.GREEN));
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
            source.sendError(Text.literal(newLooking + " is not in the goals list!"));
            return;
        }
        LibrGetter.config.goals.remove(already);
        LibrGetter.saveConfigs();
        source.sendFeedback(Text.literal("Removed " + newLooking).formatted(Formatting.YELLOW));
    }

    public static void list() {
        MutableText output = Text.literal("Goals list:");
        for (Config.Enchantment l : LibrGetter.config.goals) {
            output = output.append("\n- " + l + " (" + l.price + ") ").append(Text.literal("(remove)").setStyle(
                    Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/librget remove " + l))
            ));
        }
        source.sendFeedback(output);
    }

    public static void clear() {
        LibrGetter.config.goals.clear();
        LibrGetter.saveConfigs();
        source.sendFeedback(Text.literal("Cleared the goals list").formatted(Formatting.YELLOW));
    }

    public static void stop() {
        if (state == State.STANDBY) {
            source.sendError(Text.literal("LibrGetter isn't running!"));
            return;
        }
        source.sendFeedback(Text.literal("Successfully stopped the process").formatted(Formatting.YELLOW));
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

    public static void setSource(FabricClientCommandSource newSource) {
        source = newSource;
    }

    public static void noRefresh() {
        source.sendError(Text.literal("The villager trades can not be updated!"));
        state = State.STANDBY;
    }

    public enum State {
        STANDBY,
        START,
        BREAK,
        LOSE,
        SELECT,
        PLACE,
        GET,
        GETTING,
        LOCK
    }
}

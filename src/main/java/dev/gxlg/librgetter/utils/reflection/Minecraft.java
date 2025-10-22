package dev.gxlg.librgetter.utils.reflection;

import com.mojang.datafixers.util.Either;
import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.Reflection;
import dev.gxlg.librgetter.utils.Plugins;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.Consumer;

public class Minecraft {
    public static ClientConnection getConnection(ClientPlayNetworkHandler handler) {
        return (ClientConnection) Reflection.wrap("ClientPlayNetworkHandler:handler method_48296/method_2872/getConnection", handler);
    }

    public static ClientWorld getWorld(ClientPlayerEntity player) {
        if (Reflection.version(">= 1.21.9")) {
            return (ClientWorld) Reflection.wrap("ClientPlayerEntity:player method_73183/getEntityWorld", player);
        } else if (Reflection.version(">= 1.19")) {
            return (ClientWorld) Reflection.wrap("ClientPlayerEntity:player method_37908/getWorld", player);
        } else {
            return (ClientWorld) Reflection.wrap("ClientPlayerEntity:player field_17892/clientWorld", player);
        }
    }

    public static void interactBlock(ClientPlayerInteractionManager manager, ClientPlayerEntity player, BlockHitResult lowBlock, boolean main) {
        Hand hand = main ? Hand.MAIN_HAND : Hand.OFF_HAND;
        if (Reflection.version(">= 1.19")) {
            Reflection.wrapi("ClientPlayerInteractionManager:manager method_2896/interactBlock player hand lowBlock", manager, player, hand, lowBlock);
        } else {
            Reflection.wrapi("ClientPlayerInteractionManager:manager method_2896/interactBlock player getWorld(player) hand lowBlock", manager, player, hand, lowBlock);
        }
    }

    public static Identifier enchantmentId(Enchantment enchantment) {
        if (Reflection.version(">= 1.21")) {
            ClientWorld w = MinecraftClient.getInstance().world;
            if (w == null) return null;
            DynamicRegistryManager rm = w.getRegistryManager();

            Object r = Reflection.wrap("DynamicRegistryManager:rm method_30530/get/getOrThrow [.class_5321/.registry.RegistryKey]:[[.class_7924/.registry.RegistryKeys] field_41265/ENCHANTMENT]", rm);
            Object e = Reflection.wrap("[.class_2378/.registry.Registry]:r method_47983/getEntry Object:enchantment", r);
            return (Identifier) Reflection.wrap("Identifier method_60654/of String:[[.class_6880/.registry.entry.RegistryEntry]:e method_55840/getIdAsString]", e);

        } else if (Reflection.version(">= 1.19.3")) {
            return (Identifier) Reflection.wrap("[.class_2378/.registry.Registry]:[[.class_7923/.registry.Registries] field_41176/ENCHANTMENT] method_10221/getId Object:enchantment", enchantment);
        } else {
            Class<?> reg = (Class<?>) Reflection.wrap(".class_2378/.registry.Registry");
            return (Identifier) Reflection.wrap("reg:[reg field_11160/ENCHANTMENT] method_10221/getId Object:enchantment", reg, enchantment);
        }
    }

    public static Either<Config.Enchantment, String[]> parseTrade(TradeOfferList trades, int trade) {
        ItemStack stack = trades.get(trade).getSellItem();

        Object tag;
        if (Reflection.version(">= 1.20.5")) {
            Object cmap = Reflection.wrap("[.class_9322/.component.ComponentHolder]:stack method_57353/getComponents");
            Object nbt = Reflection.wrap("[.class_9323/.component.ComponentMap]:cmap method_57829/method_58694/get [.class_9331/.component.ComponentType/.component.DataComponentType]:[[.class_9334/.component.DataComponentTypes] field_49628/CUSTOM_DATA]", cmap);

            Object[] t = new Object[1];
            if (nbt != null) {
                Consumer<?> c = (Consumer<Object>) o -> t[0] = o;
                Reflection.wrapi("[.class_9279/.component.type.NbtComponent]:nbt method_57451/apply Consumer:c", c);
            }
            tag = t[0];

        } else {
            tag = Reflection.wrap("ItemStack:stack method_7969/getNbt/getTag", stack);
            if (tag == null) return Either.left(Config.Enchantment.EMPTY);
        }

        String id = null;
        int lvl = -1;

        // Parse plugins
        Triple<String, Integer, String[]> parsed = Plugins.parse(tag);
        if (parsed != null) {
            if (parsed.getRight() != null) return Either.right(parsed.getRight());
            id = parsed.getLeft();
            lvl = parsed.getMiddle();

        } else {
            if (Reflection.version(">= 1.20.5")) {
                Class<?> cm = (Class<?>) Reflection.wrap(".class_9323/.component.ComponentMap");
                Class<?> dsc = (Class<?>) Reflection.wrap(".class_9334/.component.DataComponentTypes");
                Class<?> dc = (Class<?>) Reflection.wrap(".class_9331/.component.ComponentType/.component.DataComponentType");
                Class<?> ic = (Class<?>) Reflection.wrap(".class_9304/.component.type.ItemEnchantmentsComponent");
                Object cmap = Reflection.wrap("[.class_9322/.component.ComponentHolder]:stack method_57353/getComponents");

                // Legacy enchantment books
                Object ecom = Reflection.wrap("cm:cmap method_57829/method_58694/get dc:[dsc field_49633/ENCHANTMENTS]", cm, cmap, dc, dsc);
                Set<?> s = null;
                boolean tryNext = false;
                if (ecom != null) {
                    s = (Set<?>) Reflection.wrapn("ic:ecom method_57539/getEnchantmentsMap/getEnchantmentEntries", ic, ecom);
                    if (s.isEmpty()) tryNext = true;
                } else tryNext = true;

                // Vanilla
                if (tryNext) {
                    ecom = Reflection.wrap("cm:cmap method_57829/method_58694/get dc:[dsc field_49643/STORED_ENCHANTMENTS]", cm, cmap, dc, dsc);


                    tryNext = false;
                    if (ecom != null) {
                        s = (Set<?>) Reflection.wrapn("ic:ecom method_57539/getEnchantmentsMap/getEnchantmentEntries", ic, ecom);
                        if (s.isEmpty()) tryNext = true;
                    } else tryNext = true;
                }

                // Insert more methods to find an enchantment here
                // ...

                if (!tryNext) {
                    // found something
                    for (Object r : s) {
                        Object2IntMap.Entry<?> e = (Object2IntMap.Entry<?>) r;
                        id = (String) Reflection.wrap("[.class_6880/.registry.entry.RegistryEntry]:e.getKey() method_55840/getIdAsString", e);
                        lvl = e.getIntValue();
                        break;
                    }
                }

            } else {
                Object list = null;
                if (Nbt.contains(tag, "Enchantments")) {
                    // Legacy enchantment books
                    list = Nbt.getList(tag, "Enchantments", 10);
                } else if (Nbt.contains(tag, "StoredEnchantments")) {
                    // Vanilla minecraft
                    list = Nbt.getList(tag, "StoredEnchantments", 10);
                }
                if (list != null) {
                    Object element = Nbt.get(list, 0);
                    id = Nbt.getString(element, "id");
                    lvl = Nbt.getShort(element, "lvl");
                }
            }
        }

        if (id == null) {
            // Nothing was found, so try fallback or return empty
            Pair<String, Integer> fb = fallback(tag);
            if (fb == null) return Either.left(Config.Enchantment.EMPTY);

            id = fb.getLeft();
            lvl = fb.getRight();
        }

        ItemStack f = getFirstBuyItem(trades.get(trade));
        ItemStack s = getSecondBuyItem(trades.get(trade));
        if (f.getItem() != Items.EMERALD) f = null;
        if (s.getItem() == Items.EMERALD) f = s;

        if (f == null) {
            return Either.right(new String[]{"librgetter.internal", "f"});
        }

        return Either.left(new Config.Enchantment(id, lvl, f.getCount()));
    }

    public static Pair<String, Integer> fallback(Object tag) {
        if (!LibrGetter.config.fallback) return null;

        String string = tag.toString();
        Map<String, Set<Integer>> searching = new HashMap<>();
        for (Config.Enchantment search : LibrGetter.config.goals) {
            if (!searching.containsKey(search.id)) searching.put(search.id, new HashSet<>());
            searching.get(search.id).add(search.lvl);
        }

        Map<String, Integer> found = new HashMap<>();
        for (String id : searching.keySet()) {
            int i = string.indexOf("\"" + id + "\"");
            if (i != -1) found.put(id, i);
        }

        String cid = null;
        int clvl = -1;
        int distance = Integer.MAX_VALUE;

        for (String id : found.keySet()) {
            int i = found.get(id) + id.length();
            for (int lvl : searching.get(id)) {
                int j = string.indexOf(":\"" + lvl + "\"", i);
                if (j == -1) j = string.indexOf(":" + lvl, i);
                if (j == -1) continue;

                if (j < distance) {
                    distance = j;
                    cid = id;
                    clvl = lvl;
                }
            }
        }
        if (cid == null) return null;
        return new Pair<>(cid, clvl);
    }

    public static ItemStack getFirstBuyItem(TradeOffer offer) {
        return (ItemStack) Reflection.wrap("TradeOffer:offer method_19272/getAdjustedFirstBuyItem/getDisplayedFirstBuyItem", offer);
    }

    public static ItemStack getSecondBuyItem(TradeOffer offer) {
        if (Reflection.version(">= 1.20.5")) {
            Optional<?> optional = (Optional<?>) Reflection.wrapn("TradeOffer:offer method_57557/getSecondBuyItem", offer);
            if (optional.isEmpty()) return ItemStack.EMPTY;
            TradedItem item = (TradedItem) optional.get();
            return item.itemStack();
        } else {
            return (ItemStack) Reflection.wrap("TradeOffer:offer method_8247/getSecondBuyItem/getDisplayedSecondBuyItem", offer);
        }
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        if (Reflection.version(">= 1.21")) {
            Class<?> ic = (Class<?>) Reflection.wrap(".class_9304/.component.type.ItemEnchantmentsComponent");
            Class<?> rc = (Class<?>) Reflection.wrap(".class_6880/.registry.entry.RegistryEntry");

            Object com = Reflection.wrap("ItemStack:stack method_58657/getEnchantments", stack);
            Set<?> s = (Set<?>) Reflection.wrapn("ic:com method_57534/getEnchantments", ic, com);
            for (Object r : s) {
                String id = (String) Reflection.wrapn("rc:r method_55840/getIdAsString", rc, r);
                if (id.equals("minecraft:efficiency")) {
                    return (int) Reflection.wrapn("ic:com method_57536/getLevel rc:r", ic, com, rc, r);
                }
            }
            return 0;

        } else {
            Object eff = Reflection.wrap("[.class_1893/.enchantment.Enchantments] field_9131/EFFICIENCY");
            return (int) Reflection.wrapn("EnchantmentHelper method_8225/getLevel Enchantment:eff ItemStack:stack", EnchantmentHelper.class, eff, stack);
        }
    }

    public static boolean canBeTraded(Enchantment enchantment) {
        if (Reflection.version(">= 1.21")) {
            ClientWorld w = MinecraftClient.getInstance().world;
            if (w == null) return false;
            DynamicRegistryManager rm = w.getRegistryManager();
            Object r = Reflection.wrap("DynamicRegistryManager:rm method_30530/get/getOrThrow [.class_5321/.registry.RegistryKey]:[[.class_7924/.registry.RegistryKeys] field_41265/ENCHANTMENT]", rm);
            Object e = Reflection.wrap("[.class_2378/.registry.Registry]:r method_47983/getEntry Object:enchantment", r, enchantment);
            return (boolean) Reflection.wrapn("[.class_6880/.registry.entry.RegistryEntry]:e method_40220/isIn [.class_6862/.registry.tag.TagKey]:[[.class_9636/.registry.tag.EnchantmentTags] field_51545/TRADEABLE]", e);
        } else {
            return (boolean) Reflection.wrapn("Enchantment:enchantment method_25949/isAvailableForEnchantedBookOffer", enchantment);
        }
    }

    public static void playSound(ClientWorld world, ClientPlayerEntity player) {
        Reflection.wrapi("World:world method_8486/playSound/playSoundClient double:player.getX() double:player.getY() double:player.getZ() SoundEvent:SoundEvents.ENTITY_PLAYER_LEVELUP SoundCategory:SoundCategory.NEUTRAL float:10.0F float:0.7F boolean:false", World.class, world, player, SoundEvent.class, SoundEvents.class, SoundCategory.class);
    }

    public static void setActionResultFail(CallbackInfoReturnable<ActionResult> info) {
        info.setReturnValue((ActionResult) Reflection.wrap("ActionResult field_5814/FAIL"));
    }

    public static boolean isVillagerLibrarian(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Object lib = Reflection.wrap("[.class_3852/.village.VillagerProfession] field_17060/LIBRARIAN");
        if (Reflection.version(">= 1.21.5")) {
            return (boolean) Reflection.wrapn("[.class_6880/.registry.entry.RegistryEntry]:[VillagerData:villagerData comp_3521/profession] method_40225/matchesKey [.class_5321/.registry.RegistryKey]:lib", villagerData, lib);
        } else {
            return Reflection.wrapn("VillagerData:villagerData method_16924/getProfession", villagerData).equals(lib);
        }
    }

    public static boolean isVillagerUnemployed(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Object lib = Reflection.wrap("[.class_3852/.village.VillagerProfession] field_17051/NONE");
        if (Reflection.version(">= 1.21.5")) {
            return (boolean) Reflection.wrapn("[.class_6880/.registry.entry.RegistryEntry]:[VillagerData:villagerData comp_3521/profession] method_40225/matchesKey [.class_5321/.registry.RegistryKey]:lib", villagerData, lib);
        } else {
            return Reflection.wrapn("VillagerData:villagerData method_16924/getProfession", villagerData).equals(lib);
        }
    }

    public static void setSelectedSlot(PlayerInventory inventory, int slot) {
        if (Reflection.version(">= 1.21.5")) {
            Reflection.wrapi("PlayerInventory:inventory method_61496/setSelectedSlot int:slot", inventory, slot);
        } else {
            Reflection.wrapi("PlayerInventory:inventory field_7545/selectedSlot slot", inventory, slot);
        }
    }

    public static void setScreen(MinecraftClient client, Screen screen) {
        Reflection.wrapi("MinecraftClient:client method_1507/setScreen/openScreen Screen:screen", client, screen);
    }
}

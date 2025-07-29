package com.gxlg.librgetter.utils.reflection;

import com.gxlg.librgetter.Config;
import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.utils.MultiVersion;
import com.gxlg.librgetter.utils.Plugins;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
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
        return (ClientConnection) Reflection.invokeMethod(ClientPlayNetworkHandler.class, handler, null, "method_48296", "method_2872", "getConnection");
    }

    public static ClientWorld getWorld(ClientPlayerEntity player) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            return (ClientWorld) Reflection.invokeMethod(ClientPlayerEntity.class, player, null, "method_37908", "getWorld");
        } else {
            return (ClientWorld) Reflection.field(ClientPlayerEntity.class, player, "field_17892", "clientWorld");
        }
    }

    public static void interactBlock(ClientPlayerInteractionManager manager, ClientPlayerEntity player, BlockHitResult lowBlock, boolean main) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            Reflection.invokeMethod(ClientPlayerInteractionManager.class, manager, new Object[]{player, main ? Hand.MAIN_HAND : Hand.OFF_HAND, lowBlock}, "method_2896", "interactBlock");
        } else {
            Reflection.invokeMethod(ClientPlayerInteractionManager.class, manager, new Object[]{player, getWorld(player), main ? Hand.MAIN_HAND : Hand.OFF_HAND, lowBlock}, "method_2896", "interactBlock");
        }
    }

    public static Identifier enchantmentId(Enchantment enchantment) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.EFFECTS)) {
            ClientWorld w = MinecraftClient.getInstance().world;
            if (w == null) return null;
            DynamicRegistryManager rm = w.getRegistryManager();

            Class<?> reg = Reflection.clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
            Class<?> re = Reflection.clazz("net.minecraft.class_6880", "net.minecraft.registry.entry.RegistryEntry");
            Class<?> rkc = Reflection.clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            Class<?> rks = Reflection.clazz("net.minecraft.class_7924", "net.minecraft.registry.RegistryKeys");
            Object ENCH = Reflection.field(rks, null, "field_41265", "ENCHANTMENT");

            Object r = Reflection.invokeMethod(DynamicRegistryManager.class, rm, new Object[]{ENCH}, new Class[]{rkc}, "method_30530", "get", "getOrThrow");
            Object e = Reflection.invokeMethod(reg, r, new Object[]{enchantment}, new Class[]{Object.class}, "method_47983", "getEntry");
            String id = (String) Reflection.invokeMethod(re, e, null, "method_55840", "getIdAsString");
            return (Identifier) Reflection.invokeMethod(Identifier.class, null, new Object[]{id}, "method_60654", "of");

        } else if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.TAGS)) {
            Class<?> registries = Reflection.clazz("net.minecraft.class_7923", "net.minecraft.registry.Registries");
            Object ench = Reflection.field(registries, null, "field_41176", "ENCHANTMENT");
            Class<?> registry = Reflection.clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
            return (Identifier) Reflection.invokeMethod(registry, ench, new Object[]{enchantment}, new Class[]{Object.class}, "method_10221", "getId");
        } else {
            Class<?> registry = Reflection.clazz("net.minecraft.class_2378", "net.minecraft.util.registry.Registry");
            Object ench = Reflection.field(registry, null, "field_11160", "ENCHANTMENT");
            return (Identifier) Reflection.invokeMethod(registry, ench, new Object[]{enchantment}, new Class[]{Object.class}, "method_10221", "getId");
        }
    }

    public static PlayerInteractEntityC2SPacket interactPacket(VillagerEntity villager) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.VILLAGER_PACKET)) {
            return (PlayerInteractEntityC2SPacket) Reflection.invokeMethod(PlayerInteractEntityC2SPacket.class, null, new Object[]{villager, false, Hand.MAIN_HAND}, new Class[]{Entity.class, boolean.class, Hand.class}, "method_34207", "interact");
        } else {
            return (PlayerInteractEntityC2SPacket) Reflection.construct(PlayerInteractEntityC2SPacket.class, new Object[]{villager, Hand.MAIN_HAND, false}, Entity.class, Hand.class, boolean.class);
        }
    }

    public static Either<Config.Enchantment, String[]> parseTrade(TradeOfferList trades, int trade) {
        ItemStack stack = trades.get(trade).getSellItem();

        Object tag;
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.COMPONENTS)) {
            Class<?> ch = Reflection.clazz("net.minecraft.class_9322", "net.minecraft.component.ComponentHolder");
            Class<?> dsc = Reflection.clazz("net.minecraft.class_9334", "net.minecraft.component.DataComponentTypes");
            Class<?> dc = Reflection.clazz("net.minecraft.class_9331", "net.minecraft.component.ComponentType", "net.minecraft.component.DataComponentType");
            Object CUST = Reflection.field(dsc, null, "field_49628", "CUSTOM_DATA");
            Class<?> nc = Reflection.clazz("net.minecraft.class_9279", "net.minecraft.component.type.NbtComponent");
            Class<?> cm = Reflection.clazz("net.minecraft.class_9323", "net.minecraft.component.ComponentMap");

            Object cmap = Reflection.invokeMethod(ch, stack, null, "method_57353", "getComponents");
            Object nbt = Reflection.invokeMethod(cm, cmap, new Object[]{CUST}, new Class[]{dc}, "method_57829", "method_58694", "get");

            Object[] t = new Object[1];
            if (nbt != null) {
                Consumer<?> c = (Consumer<Object>) o -> t[0] = o;
                Reflection.invokeMethod(nc, nbt, new Object[]{c}, new Class[]{Consumer.class}, "method_57451", "apply");
            }
            tag = t[0];

        } else {
            tag = Reflection.invokeMethod(ItemStack.class, stack, null, "method_7969", "getNbt", "getTag");
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
            if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.COMPONENTS)) {
                Class<?> cm = Reflection.clazz("net.minecraft.class_9323", "net.minecraft.component.ComponentMap");
                Class<?> ch = Reflection.clazz("net.minecraft.class_9322", "net.minecraft.component.ComponentHolder");
                Class<?> dsc = Reflection.clazz("net.minecraft.class_9334", "net.minecraft.component.DataComponentTypes");
                Class<?> dc = Reflection.clazz("net.minecraft.class_9331", "net.minecraft.component.ComponentType", "net.minecraft.component.DataComponentType");
                Object ENCH = Reflection.field(dsc, null, "field_49633", "ENCHANTMENTS");
                Object STOR = Reflection.field(dsc, null, "field_49643", "STORED_ENCHANTMENTS");
                Class<?> ic = Reflection.clazz("net.minecraft.class_9304", "net.minecraft.component.type.ItemEnchantmentsComponent");
                Class<?> rc = Reflection.clazz("net.minecraft.class_6880", "net.minecraft.registry.entry.RegistryEntry");

                Object cmap = Reflection.invokeMethod(ch, stack, null, "method_57353", "getComponents");

                // Legacy enchantment books
                Object ecom = Reflection.invokeMethod(cm, cmap, new Object[]{ENCH}, new Class[]{dc}, "method_57829", "method_58694", "get");
                Set<?> s = null;
                boolean tryNext = false;
                if (ecom != null) {
                    s = (Set<?>) Reflection.invokeMethod(ic, ecom, null, "method_57539", "getEnchantmentsMap", "getEnchantmentEntries");
                    if (s.isEmpty()) tryNext = true;
                } else tryNext = true;

                // Vanilla
                if (tryNext) {
                    ecom = Reflection.invokeMethod(cm, cmap, new Object[]{STOR}, new Class[]{dc}, "method_57829", "method_58694", "get");
                    tryNext = false;
                    if (ecom != null) {
                        s = (Set<?>) Reflection.invokeMethod(ic, ecom, null, "method_57539", "getEnchantmentsMap", "getEnchantmentEntries");
                        if (s.isEmpty()) tryNext = true;
                    } else tryNext = true;
                }

                // Insert more methods to find an enchantment here
                // ...

                if (!tryNext) {
                    // found something
                    for (Object r : s) {
                        Object2IntMap.Entry<?> e = (Object2IntMap.Entry<?>) r;
                        id = (String) Reflection.invokeMethod(rc, e.getKey(), null, "method_55840", "getIdAsString");
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
                    Object element = Nbt.compound.cast(Reflection.invokeMethod(Nbt.list, list, new Object[]{0}, new Class[]{int.class}, "method_10534", "get"));
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
        return (ItemStack) Reflection.invokeMethod(TradeOffer.class, offer, new Object[]{}, "method_19272", "getAdjustedFirstBuyItem", "getDisplayedFirstBuyItem");
    }

    public static ItemStack getSecondBuyItem(TradeOffer offer) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.COMPONENTS)) {
            Optional<?> optional = (Optional<?>) Reflection.invokeMethod(TradeOffer.class, offer, new Object[]{}, "method_57557", "getSecondBuyItem");
            if (optional.isEmpty()) return ItemStack.EMPTY;
            TradedItem item = (TradedItem) optional.get();
            return item.itemStack();
        } else {
            return (ItemStack) Reflection.invokeMethod(TradeOffer.class, offer, new Object[]{}, "method_8247", "getSecondBuyItem", "getDisplayedSecondBuyItem");
        }
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.EFFECTS)) {
            Class<?> ic = Reflection.clazz("net.minecraft.class_9304", "net.minecraft.component.type.ItemEnchantmentsComponent");
            Class<?> rc = Reflection.clazz("net.minecraft.class_6880", "net.minecraft.registry.entry.RegistryEntry");

            Object com = Reflection.invokeMethod(ItemStack.class, stack, null, "method_58657", "getEnchantments");
            Set<?> s = (Set<?>) Reflection.invokeMethod(ic, com, null, "method_57534", "getEnchantments");
            for (Object r : s) {
                String id = (String) Reflection.invokeMethod(rc, r, null, "method_55840", "getIdAsString");
                if (id.equals("minecraft:efficiency")) {
                    return (int) Reflection.invokeMethod(ic, com, new Object[]{r}, new Class[]{rc}, "method_57536", "getLevel");
                }
            }
            return 0;

        } else {
            Class<?> enc = Reflection.clazz("net.minecraft.class_1893", "net.minecraft.enchantment.Enchantments");
            Object eff = Reflection.field(enc, null, "field_9131", "EFFICIENCY");
            return (int) Reflection.invokeMethod(EnchantmentHelper.class, null, new Object[]{eff, stack}, new Class[]{Enchantment.class, ItemStack.class}, "method_8225", "getLevel");
        }
    }

    public static boolean canBeTraded(Enchantment enchantment) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.EFFECTS)) {
            ClientWorld w = MinecraftClient.getInstance().world;
            if (w == null) return false;
            DynamicRegistryManager rm = w.getRegistryManager();

            Class<?> reg = Reflection.clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
            Class<?> re = Reflection.clazz("net.minecraft.class_6880", "net.minecraft.registry.entry.RegistryEntry");
            Class<?> rkc = Reflection.clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            Class<?> rks = Reflection.clazz("net.minecraft.class_7924", "net.minecraft.registry.RegistryKeys");
            Object ENCH = Reflection.field(rks, null, "field_41265", "ENCHANTMENT");
            Class<?> tgc = Reflection.clazz("net.minecraft.class_6862", "net.minecraft.registry.tag.TagKey");
            Class<?> tgs = Reflection.clazz("net.minecraft.class_9636", "net.minecraft.registry.tag.EnchantmentTags");
            Object TRD = Reflection.field(tgs, null, "field_51545", "TRADEABLE");

            Object r = Reflection.invokeMethod(DynamicRegistryManager.class, rm, new Object[]{ENCH}, new Class[]{rkc}, "method_30530", "get", "getOrThrow");
            Object e = Reflection.invokeMethod(reg, r, new Object[]{enchantment}, new Class[]{Object.class}, "method_47983", "getEntry");
            return (boolean) Reflection.invokeMethod(re, e, new Object[]{TRD}, new Class[]{tgc}, "method_40220", "isIn");

        } else {
            return (boolean) Reflection.invokeMethod(Enchantment.class, enchantment, null, "method_25949", "isAvailableForEnchantedBookOffer");
        }
    }

    public static void playSound(ClientWorld world, ClientPlayerEntity player) {
        Reflection.invokeMethod(World.class, world, new Object[]{player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 10.0F, 0.7F, false}, new Class[]{double.class, double.class, double.class, SoundEvent.class, SoundCategory.class, float.class, float.class, boolean.class}, "method_8486", "playSound", "playSoundClient");
    }

    public static void setActionResultFail(CallbackInfoReturnable<ActionResult> info) {
        Object field = Reflection.field(ActionResult.class, null, "field_5814", "FAIL");
        info.setReturnValue((ActionResult) field);
    }

    public static boolean isVillagerLibrarian(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Class<?> vpc = Reflection.clazz("net.minecraft.class_3852", "net.minecraft.village.VillagerProfession");
        Object lib = Reflection.field(vpc, null, "field_17060", "LIBRARIAN");
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.MORE_ABSTRACTION)) {
            Class<?> re = Reflection.clazz("net.minecraft.class_6880", "net.minecraft.registry.entry.RegistryEntry");
            Object reg = Reflection.invokeMethod(VillagerData.class, villagerData, null, "comp_3521", "profession");
            Class<?> rkc = Reflection.clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            return (boolean) Reflection.invokeMethod(re, reg, new Object[]{lib}, new Class[]{rkc}, "method_40225", "matchesKey");
        } else {
            Object pro = Reflection.invokeMethod(VillagerData.class, villagerData, null, "method_16924", "getProfession");
            return pro.equals(lib);
        }
    }

    public static boolean isVillagerLost(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Class<?> vpc = Reflection.clazz("net.minecraft.class_3852", "net.minecraft.village.VillagerProfession");
        Object lib = Reflection.field(vpc, null, "field_17051", "NONE");
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.MORE_ABSTRACTION)) {
            Class<?> re = Reflection.clazz("net.minecraft.class_6880", "net.minecraft.registry.entry.RegistryEntry");
            Object reg = Reflection.invokeMethod(VillagerData.class, villagerData, null, "comp_3521", "profession");
            Class<?> rkc = Reflection.clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            return (boolean) Reflection.invokeMethod(re, reg, new Object[]{lib}, new Class[]{rkc}, "method_40225", "matchesKey");
        } else {
            Object pro = Reflection.invokeMethod(VillagerData.class, villagerData, null, "method_16924", "getProfession");
            return pro.equals(lib);
        }
    }

    public static void setSelectedSlot(PlayerInventory inventory, int slot) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.MORE_ABSTRACTION)) {
            Reflection.invokeMethod(PlayerInventory.class, inventory, new Object[]{slot}, new Class[]{int.class}, "method_61496", "setSelectedSlot");
        } else {
            Reflection.setField(PlayerInventory.class, inventory, slot, "field_7545", "selectedSlot");
        }
    }

    public static void setScreen(MinecraftClient client, Screen screen) {
        Reflection.invokeMethod(MinecraftClient.class, client, new Object[]{screen}, new Class[]{Screen.class}, "method_1507", "setScreen", "openScreen");
    }

    public static String getVersion() {
        Class<?> clazzGameVersion = Reflection.clazz("com.mojang.bridge.game.GameVersion", "net.minecraft.class_6489", "net.minecraft.GameVersion");
        Class<?> clazzConstants = SharedConstants.class;
        Object gameVersion = Reflection.invokeMethod(clazzConstants, null, null, "method_16673", "getGameVersion");
        return (String) Reflection.invokeMethod(clazzGameVersion, gameVersion, null, "method_48019", "getName", "comp_4025", "name");
    }
}

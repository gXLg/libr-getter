package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.multiversion.C;
import dev.gxlg.librgetter.multiversion.R;
import dev.gxlg.librgetter.multiversion.V;
import dev.gxlg.librgetter.utils.Plugins;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.ParsedEnchantmentTrade;
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
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class Minecraft {
    public static ClientConnection getConnection(ClientPlayNetworkHandler handler) {
        return (ClientConnection) R.clz(ClientPlayNetworkHandler.class).inst(handler).mthd("method_48296/method_2872/getConnection").invk();
    }

    public static ClientWorld getWorld(ClientPlayerEntity player) {
        if (!V.lower("1.21.9")) {
            return (ClientWorld) R.clz(ClientPlayerEntity.class).inst(player).mthd("method_73183/getEntityWorld").invk();
        } else if (!V.lower("1.19")) {
            return (ClientWorld) R.clz(ClientPlayerEntity.class).inst(player).mthd("method_37908/getWorld").invk();
        } else {
            return (ClientWorld) R.clz(ClientPlayerEntity.class).inst(player).fld("field_17892/clientWorld").get();
        }
    }

    public static void interactBlock(ClientPlayerInteractionManager manager, ClientPlayerEntity player, BlockHitResult lowBlock, boolean main) {
        Hand hand = main ? Hand.MAIN_HAND : Hand.OFF_HAND;
        if (!V.lower("1.19")) {
            R.clz(ClientPlayerInteractionManager.class).inst(manager).mthd("method_2896/interactBlock", ClientPlayerEntity.class, Hand.class, BlockHitResult.class).invk(player, hand, lowBlock);
        } else {
            R.clz(ClientPlayerInteractionManager.class).inst(manager).mthd("method_2896/interactBlock", ClientPlayerEntity.class, ClientWorld.class, Hand.class, BlockHitResult.class)
             .invk(player, getWorld(player), hand, lowBlock);
        }
    }

    public static Identifier enchantmentId(Enchantment enchantment) {
        if (!V.lower("1.21")) {
            ClientWorld w = MinecraftClient.getInstance().world;
            if (w == null) {
                return null;
            }
            Object rm = w.getRegistryManager();

            Object r = C.DynamicRegistryManager.inst(rm).mthd("method_30530/get/getOrThrow", C.RegistryKey).invk(C.RegistryKeys.fld("field_41265/ENCHANTMENT").get());
            Object e = C.Registry.inst(r).mthd("method_47983/getEntry", Object.class).invk(enchantment);
            return (Identifier) R.clz(Identifier.class).mthd("method_60654/of", String.class).invk(C.RegistryEntry.inst(e).mthd("method_55840/getIdAsString").invk());

        } else if (!V.lower("1.19.3")) {
            return (Identifier) C.Registry.inst(C.Registries.fld("field_41176/ENCHANTMENT").get()).mthd("method_10221/getId", Object.class).invk(enchantment);
        } else {
            return (Identifier) C.Registry.inst(C.Registry.fld("field_11160/ENCHANTMENT").get()).mthd("method_10221/getId", Object.class).invk(enchantment);
        }
    }

    public static ParsedEnchantmentTrade parseTrade(TradeOfferList trades, int trade) {
        ItemStack stack = trades.get(trade).getSellItem();

        Object tag;
        if (!V.lower("1.20.5")) {
            Object cmap = C.ComponentHolder.inst(stack).mthd("method_57353/getComponents").invk();
            Object nbt = C.ComponentMap.inst(cmap).mthd("method_57829/method_58694/get", C.DataComponentType).invk(C.DataComponentTypes.fld("field_49628/CUSTOM_DATA").get());

            Object[] t = new Object[1];
            if (nbt != null) {
                Consumer<?> c = (Consumer<Object>) o -> t[0] = o;
                R.clz("net.minecraft.class_9279/net.minecraft.component.type.NbtComponent").inst(nbt).mthd("method_57451/apply", Consumer.class).invk(c);
            }
            tag = t[0];

        } else {
            tag = R.clz(ItemStack.class).inst(stack).mthd("method_7969/getNbt/getTag").invk();
            if (tag == null) {
                return ParsedEnchantmentTrade.success(EnchantmentTrade.EMPTY);
            }
        }

        String id = null;
        int lvl = -1;

        // Parse plugins
        Triple<String, Integer, String[]> parsed = Plugins.parse(tag);
        if (parsed != null) {
            if (parsed.getRight() != null) {
                return ParsedEnchantmentTrade.error(parsed.getRight());
            }
            id = parsed.getLeft();
            lvl = parsed.getMiddle();

        } else {
            if (!V.lower("1.20.5")) {

                R.RClass cm = C.ComponentMap;
                R.RClass dsc = C.DataComponentTypes;
                R.RClass dc = C.DataComponentType;
                R.RClass ic = C.ItemEnchantmentsComponent;
                Object cmap = C.ComponentHolder.inst(stack).mthd("method_57353/getComponents").invk();

                // Legacy enchantment books
                Object ecom = cm.inst(cmap).mthd("method_57829/method_58694/get", dc).invk(dsc.fld("field_49633/ENCHANTMENTS").get());
                Set<?> s = null;
                boolean tryNext = false;
                if (ecom != null) {
                    s = (Set<?>) ic.inst(ecom).mthd("method_57539/getEnchantmentsMap/getEnchantmentEntries").invk();
                    if (s.isEmpty()) {
                        tryNext = true;
                    }
                } else {
                    tryNext = true;
                }

                // Vanilla
                if (tryNext) {
                    ecom = cm.inst(cmap).mthd("method_57829/method_58694/get", dc).invk(dsc.fld("field_49643/STORED_ENCHANTMENTS").get());


                    tryNext = false;
                    if (ecom != null) {
                        s = (Set<?>) ic.inst(ecom).mthd("method_57539/getEnchantmentsMap/getEnchantmentEntries").invk();
                        if (s.isEmpty()) {
                            tryNext = true;
                        }
                    } else {
                        tryNext = true;
                    }
                }

                // Insert more methods to find an enchantment here
                // ...

                if (!tryNext) {
                    // found something
                    for (Object r : s) {
                        Object2IntMap.Entry<?> e = (Object2IntMap.Entry<?>) r;
                        id = (String) C.RegistryEntry.inst(e.getKey()).mthd("method_55840/getIdAsString").invk();
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
            if (fb == null) {
                return ParsedEnchantmentTrade.success(EnchantmentTrade.EMPTY);
            }

            id = fb.getLeft();
            lvl = fb.getRight();
        }

        ItemStack f = getFirstBuyItem(trades.get(trade));
        ItemStack s = getSecondBuyItem(trades.get(trade));
        if (f.getItem() != Items.EMERALD) {
            f = null;
        }
        if (s.getItem() == Items.EMERALD) {
            f = s;
        }

        if (f == null) {
            return ParsedEnchantmentTrade.error("librgetter.internal", "f", "Minecraft#parseTrade");
        }

        return ParsedEnchantmentTrade.success(new EnchantmentTrade(id, lvl, f.getCount()));
    }

    public static Pair<String, Integer> fallback(Object tag) {
        if (!LibrGetter.config.fallback) {
            return null;
        }

        String string = tag.toString();
        Map<String, Set<Integer>> searching = new HashMap<>();
        for (EnchantmentTrade search : LibrGetter.config.goals) {
            if (!searching.containsKey(search.id())) {
                searching.put(search.id(), new HashSet<>());
            }
            searching.get(search.id()).add(search.lvl());
        }

        Map<String, Integer> found = new HashMap<>();
        for (String id : searching.keySet()) {
            int i = string.indexOf("\"" + id + "\"");
            if (i != -1) {
                found.put(id, i);
            }
        }

        String cid = null;
        int clvl = -1;
        int distance = Integer.MAX_VALUE;

        for (String id : found.keySet()) {
            int i = found.get(id) + id.length();
            for (int lvl : searching.get(id)) {
                int j = string.indexOf(":\"" + lvl + "\"", i);
                if (j == -1) {
                    j = string.indexOf(":" + lvl, i);
                }
                if (j == -1) {
                    continue;
                }

                if (j < distance) {
                    distance = j;
                    cid = id;
                    clvl = lvl;
                }
            }
        }
        if (cid == null) {
            return null;
        }
        return new Pair<>(cid, clvl);
    }

    public static ItemStack getFirstBuyItem(TradeOffer offer) {
        return (ItemStack) R.clz(TradeOffer.class).inst(offer).mthd("method_19272/getAdjustedFirstBuyItem/getDisplayedFirstBuyItem").invk();
    }

    public static ItemStack getSecondBuyItem(TradeOffer offer) {
        if (!V.lower("1.20.5")) {
            Optional<?> optional = (Optional<?>) R.clz(TradeOffer.class).inst(offer).mthd("method_57557/getSecondBuyItem").invk();
            if (optional.isEmpty()) {
                return ItemStack.EMPTY;
            }
            Object item = optional.get();
            return (ItemStack) R.clz("net.minecraft.class_9306/net.minecraft.village.TradedItem").inst(item).mthd("comp_2427/itemStack").invk();
        } else {
            return (ItemStack) R.clz(TradeOffer.class).inst(offer).mthd("method_8247/getSecondBuyItem/getDisplayedSecondBuyItem").invk();
        }
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        if (!V.lower("1.21")) {

            R.RClass ic = C.ItemEnchantmentsComponent;
            R.RClass rc = C.RegistryEntry;

            Object com = R.clz(ItemStack.class).inst(stack).mthd("method_58657/getEnchantments").invk();
            Set<?> s = (Set<?>) ic.inst(com).mthd("method_57534/getEnchantments").invk();
            for (Object r : s) {
                String id = (String) rc.inst(r).mthd("method_55840/getIdAsString").invk();
                if (id.equals("minecraft:efficiency")) {
                    return (int) ic.inst(com).mthd("method_57536/getLevel", rc).invk(r);
                }
            }
            return 0;

        } else {
            Object eff = R.clz("net.minecraft.class_1893/net.minecraft.enchantment.Enchantments").fld("field_9131/EFFICIENCY").get();
            return (int) R.clz(EnchantmentHelper.class).mthd("method_8225/getLevel", Enchantment.class, ItemStack.class).invk(eff, stack);
        }
    }

    public static boolean canBeTraded(Enchantment enchantment) {
        if (!V.lower("1.21")) {
            ClientWorld w = MinecraftClient.getInstance().world;
            if (w == null) {
                return false;
            }
            Object rm = w.getRegistryManager();
            Object r = C.DynamicRegistryManager.inst(rm).mthd("method_30530/get/getOrThrow", C.RegistryKey).invk(C.RegistryKeys.fld("field_41265/ENCHANTMENT").get());
            Object e = C.Registry.inst(r).mthd("method_47983/getEntry", Object.class).invk(enchantment);
            return (boolean) C.RegistryEntry.inst(e).mthd("method_40220/isIn", R.clz("net.minecraft.class_6862/net.minecraft.registry.tag.TagKey"))
                                            .invk(R.clz("net.minecraft.class_9636/net.minecraft.registry.tag.EnchantmentTags").fld("field_51545/TRADEABLE").get());
        } else {
            return (boolean) R.clz(Enchantment.class).inst(enchantment).mthd("method_25949/isAvailableForEnchantedBookOffer").invk();
        }
    }

    public static void playNotification(ClientWorld world, ClientPlayerEntity player) {
        if (!LibrGetter.config.notify) {
            return;
        }
        R.clz(World.class).inst(world)
         .mthd("method_8486/playSound/playSoundClient", double.class, double.class, double.class, SoundEvent.class, SoundCategory.class, float.class, float.class, boolean.class)
         .invk(player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 10F, 0.7F, false);
    }

    public static void setActionResultFail(CallbackInfoReturnable<ActionResult> info) {
        info.setReturnValue((ActionResult) R.clz(ActionResult.class).fld("field_5814/FAIL").get());
    }

    public static boolean isVillagerLibrarian(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Object prof = C.VillagerProfession.fld("field_17060/LIBRARIAN").get();
        if (!V.lower("1.21.5")) {
            return (boolean) C.RegistryEntry.inst(R.clz(VillagerData.class).inst(villagerData).mthd("comp_3521/profession").invk()).mthd("method_40225/matchesKey", C.RegistryKey).invk(prof);
        } else {
            return R.clz(VillagerData.class).inst(villagerData).mthd("method_16924/getProfession").invk().equals(prof);
        }
    }

    public static boolean isVillagerUnemployed(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Object prof = C.VillagerProfession.fld("field_17051/NONE").get();
        if (!V.lower("1.21.5")) {
            return ((boolean) C.RegistryEntry.inst(R.clz(VillagerData.class).inst(villagerData).mthd("comp_3521/profession").invk()).mthd("method_40225/matchesKey", C.RegistryKey).invk(prof));
        } else {
            return R.clz(VillagerData.class).inst(villagerData).mthd("method_16924/getProfession").invk().equals(prof);
        }
    }

    public static void setSelectedSlot(PlayerInventory inventory, int slot) {
        if (!V.lower("1.21.5")) {
            R.clz(PlayerInventory.class).inst(inventory).mthd("method_61496/setSelectedSlot", int.class).invk(slot);
        } else {
            R.clz(PlayerInventory.class).inst(inventory).fld("field_7545/selectedSlot").set(slot);
        }
    }

    public static void setScreen(MinecraftClient client, Screen screen) {
        R.clz(MinecraftClient.class).inst(client).mthd("method_1507/setScreen/openScreen", Screen.class).invk(screen);
    }
}

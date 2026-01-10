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
            ClientWorld world = MinecraftClient.getInstance().world;
            if (world == null) {
                return null;
            }
            Object registryManager = world.getRegistryManager();

            Object enchantmentRegistry = C.DynamicRegistryManager.inst(registryManager).mthd("method_30530/get/getOrThrow", C.RegistryKey).invk(C.RegistryKeys.fld("field_41265/ENCHANTMENT").get());
            Object enchantmentRegistryEntry = C.Registry.inst(enchantmentRegistry).mthd("method_47983/getEntry", Object.class).invk(enchantment);
            return (Identifier) R.clz(Identifier.class).mthd("method_60654/of", String.class).invk(C.RegistryEntry.inst(enchantmentRegistryEntry).mthd("method_55840/getIdAsString").invk());

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
            Object componentsMap = C.ComponentHolder.inst(stack).mthd("method_57353/getComponents").invk();
            Object nbt = C.ComponentMap.inst(componentsMap).mthd("method_57829/method_58694/get", C.DataComponentType).invk(C.DataComponentTypes.fld("field_49628/CUSTOM_DATA").get());

            Object[] tagContainer = new Object[1];
            if (nbt != null) {
                Consumer<?> consumer = (Consumer<Object>) o -> tagContainer[0] = o;
                R.clz("net.minecraft.class_9279/net.minecraft.component.type.NbtComponent").inst(nbt).mthd("method_57451/apply", Consumer.class).invk(consumer);
            }
            tag = tagContainer[0];

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

                R.RClass componentMapClass = C.ComponentMap;
                R.RClass dataComponentTypes = C.DataComponentTypes;
                R.RClass dataComponentTypeClass = C.DataComponentType;
                R.RClass itemEnchantmentsComponentClass = C.ItemEnchantmentsComponent;
                Object componentsMap = C.ComponentHolder.inst(stack).mthd("method_57353/getComponents").invk();

                // Legacy enchantment books
                Object enchantmentComponent = componentMapClass.inst(componentsMap).mthd("method_57829/method_58694/get", dataComponentTypeClass)
                                                               .invk(dataComponentTypes.fld("field_49633/ENCHANTMENTS").get());
                Set<?> enchantmentSet = null;
                boolean tryNext = false;
                if (enchantmentComponent != null) {
                    enchantmentSet = (Set<?>) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57539/getEnchantmentsMap/getEnchantmentEntries").invk();
                    if (enchantmentSet.isEmpty()) {
                        tryNext = true;
                    }
                } else {
                    tryNext = true;
                }

                // Vanilla
                if (tryNext) {
                    enchantmentComponent = componentMapClass.inst(componentsMap).mthd("method_57829/method_58694/get", dataComponentTypeClass)
                                                            .invk(dataComponentTypes.fld("field_49643/STORED_ENCHANTMENTS").get());


                    tryNext = false;
                    if (enchantmentComponent != null) {
                        enchantmentSet = (Set<?>) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57539/getEnchantmentsMap/getEnchantmentEntries").invk();
                        if (enchantmentSet.isEmpty()) {
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
                    for (Object enchantmentRegistryEntry : enchantmentSet) {
                        Object2IntMap.Entry<?> e = (Object2IntMap.Entry<?>) enchantmentRegistryEntry;
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
            Pair<String, Integer> fallbackData = fallbackParse(tag);
            if (fallbackData == null) {
                return ParsedEnchantmentTrade.success(EnchantmentTrade.EMPTY);
            }

            id = fallbackData.getLeft();
            lvl = fallbackData.getRight();
        }

        ItemStack firstBuyItem = getFirstBuyItem(trades.get(trade));
        ItemStack secondBuyItem = getSecondBuyItem(trades.get(trade));
        if (firstBuyItem.getItem() != Items.EMERALD) {
            firstBuyItem = null;
        }
        if (secondBuyItem.getItem() == Items.EMERALD) {
            firstBuyItem = secondBuyItem;
        }

        if (firstBuyItem == null) {
            return ParsedEnchantmentTrade.error("librgetter.internal", "firstBuyItem", "Minecraft#parseTrade");
        }

        return ParsedEnchantmentTrade.success(new EnchantmentTrade(id, lvl, firstBuyItem.getCount()));
    }

    public static Pair<String, Integer> fallbackParse(Object tag) {
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

        String finalId = null;
        int finalLvl = -1;
        int indexDistance = Integer.MAX_VALUE;

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

                if (j < indexDistance) {
                    indexDistance = j;
                    finalId = id;
                    finalLvl = lvl;
                }
            }
        }
        if (finalId == null) {
            return null;
        }
        return new Pair<>(finalId, finalLvl);
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
            R.RClass itemEnchantmentsComponentClass = C.ItemEnchantmentsComponent;
            R.RClass registryEntryClass = C.RegistryEntry;

            Object enchantmentComponent = R.clz(ItemStack.class).inst(stack).mthd("method_58657/getEnchantments").invk();
            Set<?> enchantmentSet = (Set<?>) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57534/getEnchantments").invk();
            for (Object enchantmentRegistryEntry : enchantmentSet) {
                String id = (String) registryEntryClass.inst(enchantmentRegistryEntry).mthd("method_55840/getIdAsString").invk();
                if (id.equals("minecraft:efficiency")) {
                    return (int) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57536/getLevel", registryEntryClass).invk(enchantmentRegistryEntry);
                }
            }
            return 0;

        } else {
            Object efficiencyEnchantment = R.clz("net.minecraft.class_1893/net.minecraft.enchantment.Enchantments").fld("field_9131/EFFICIENCY").get();
            return (int) R.clz(EnchantmentHelper.class).mthd("method_8225/getLevel", Enchantment.class, ItemStack.class).invk(efficiencyEnchantment, stack);
        }
    }

    public static boolean canBeTraded(Enchantment enchantment) {
        if (!V.lower("1.21")) {
            ClientWorld world = MinecraftClient.getInstance().world;
            if (world == null) {
                return false;
            }

            Object registryManager = world.getRegistryManager();
            Object enchantmentRegistry = C.DynamicRegistryManager.inst(registryManager).mthd("method_30530/get/getOrThrow", C.RegistryKey).invk(C.RegistryKeys.fld("field_41265/ENCHANTMENT").get());
            Object enchantmentRegistryEntry = C.Registry.inst(enchantmentRegistry).mthd("method_47983/getEntry", Object.class).invk(enchantment);
            return (boolean) C.RegistryEntry.inst(enchantmentRegistryEntry).mthd("method_40220/isIn", R.clz("net.minecraft.class_6862/net.minecraft.registry.tag.TagKey"))
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
        Object librarianProfession = C.VillagerProfession.fld("field_17060/LIBRARIAN").get();
        if (!V.lower("1.21.5")) {
            return (boolean) C.RegistryEntry.inst(R.clz(VillagerData.class).inst(villagerData).mthd("comp_3521/profession").invk()).mthd("method_40225/matchesKey", C.RegistryKey)
                                            .invk(librarianProfession);
        } else {
            return R.clz(VillagerData.class).inst(villagerData).mthd("method_16924/getProfession").invk().equals(librarianProfession);
        }
    }

    public static boolean isVillagerUnemployed(VillagerEntity villager) {
        VillagerData villagerData = villager.getVillagerData();
        Object noneProfession = C.VillagerProfession.fld("field_17051/NONE").get();
        if (!V.lower("1.21.5")) {
            return (
                (boolean) C.RegistryEntry.inst(R.clz(VillagerData.class).inst(villagerData).mthd("comp_3521/profession").invk()).mthd("method_40225/matchesKey", C.RegistryKey).invk(noneProfession)
            );
        } else {
            return R.clz(VillagerData.class).inst(villagerData).mthd("method_16924/getProfession").invk().equals(noneProfession);
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

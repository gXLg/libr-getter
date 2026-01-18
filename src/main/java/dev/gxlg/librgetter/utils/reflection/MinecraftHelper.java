package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.plugins.Plugins;
import dev.gxlg.librgetter.utils.reflection.chaining.tags.Tags;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.multiversion.R;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryAccessWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentHolderWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.component.DataComponentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.RegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.component.CustomDataWrapper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.locale.Language;
import net.minecraft.network.Connection;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MinecraftHelper {

    // TODO: split into multiple helper classes?

    public static Connection getConnection(ClientPacketListener handler) {
        return (Connection) R.clz(ClientPacketListener.class).inst(handler).mthd("method_48296/method_2872/getConnection").invk();
    }

    public static ClientLevel getWorld(LocalPlayer player) {
        LocalPlayerWrapper playerWrapper = LocalPlayerWrapper.inst(player);
        if (!V.lower("1.21.9")) {
            return playerWrapper.level();
        } else if (!V.lower("1.19")) {
            return playerWrapper.getLevel();
        } else {
            return playerWrapper.getClientLevel();
        }
    }

    public static void interactBlock(MultiPlayerGameMode manager, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand) {
        InteractionHand hand = useMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        MultiPlayerGameModeWrapper managerWrapper = MultiPlayerGameModeWrapper.inst(manager);
        if (!V.lower("1.19")) {
            managerWrapper.useItemOn(player, hand, lowBlock);
        } else {
            managerWrapper.useItemOn(player, getWorld(player), hand, lowBlock);
        }
    }

    public static Identifier enchantmentId(Enchantment enchantment) {
        if (!V.lower("1.21")) {
            ClientLevel world = Minecraft.getInstance().level;
            if (world == null) {
                return null;
            }
            return RegistryAccessWrapper.inst(world.registryAccess()).lookupOrThrow(RegistriesWrapper.ENCHANTMENT()).getKey(enchantment);

        } else if (!V.lower("1.19.3")) {
            return BuiltInRegistriesWrapper.ENCHANTMENT().getKey(enchantment);
        } else {
            return RegistryWrapper.ENCHANTMENT().getKey(enchantment);
        }
    }

    public static EnchantmentTrade parseTrade(MerchantOffers trades, int trade) throws LibrGetterException {
        ItemStack stack = trades.get(trade).getResult();

        CompoundTagWrapper tag;
        if (!V.lower("1.20.5")) {
            CustomDataWrapper customData = CustomDataWrapper.inst(DataComponentHolderWrapper.inst(stack).get(DataComponentsWrapper.CUSTOM_DATA()));
            if (customData.isNull()) {
                return EnchantmentTrade.EMPTY;
            }
            tag = customData.copyTag();
        } else {
            tag = ItemStackWrapper.inst(stack).getTag();
            if (tag.isNull()) {
                return EnchantmentTrade.EMPTY;
            }
        }

        String id = null;
        int lvl = -1;

        // Parse plugins
        EnchantmentTrade.EnchantmentOnly parsed = Plugins.parse(tag);
        if (parsed != null) {
            id = parsed.id();
            lvl = parsed.lvl();

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
                    enchantmentSet = (Set<?>) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57539/entrySet").invk();
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
                        enchantmentSet = (Set<?>) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57539/entrySet").invk();
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
                        id = (String) C.RegistryEntry.inst(e.getKey()).mthd("method_55840/getRegisteredName").invk();
                        lvl = e.getIntValue();
                        break;
                    }
                }

            } else {
                List<TagWrapper> list = null;
                if (tag.contains("Enchantments")) {
                    // Legacy enchantment books
                    list = Tags.getImpl().getList(tag, "Enchantments", 10);
                } else if (tag.contains("StoredEnchantments")) {
                    // Vanilla minecraft
                    list = Tags.getImpl().getList(tag, "StoredEnchantments", 10);
                }
                if (list != null) {
                    CompoundTagWrapper element = list.get(0).downcast(CompoundTagWrapper.class);
                    id = Tags.getImpl().getString(element, "id");
                    lvl = Tags.getImpl().getShort(element, "lvl");
                }
            }
        }

        if (id == null) {
            // Nothing was found, so try fallback or return empty
            Tuple<String, Integer> fallbackData = fallbackParse(tag);
            if (fallbackData == null) {
                return EnchantmentTrade.EMPTY;
            }

            id = fallbackData.getA();
            lvl = fallbackData.getB();
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
            throw new InternalErrorException("firstBuyItem");
        }

        return new EnchantmentTrade(id, lvl, firstBuyItem.getCount());
    }

    public static Tuple<String, Integer> fallbackParse(Object tag) {
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
        return new Tuple<>(finalId, finalLvl);
    }

    public static ItemStack getFirstBuyItem(MerchantOffer offer) {
        return (ItemStack) R.clz(MerchantOffer.class).inst(offer).mthd("method_19272/getCostA").invk();
    }

    public static ItemStack getSecondBuyItem(MerchantOffer offer) {
        return (ItemStack) R.clz(MerchantOffer.class).inst(offer).mthd("method_8247/getCostB").invk();
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        if (!V.lower("1.21")) {
            R.RClass itemEnchantmentsComponentClass = C.ItemEnchantmentsComponent;
            R.RClass registryEntryClass = C.RegistryEntry;

            Object enchantmentComponent = R.clz(ItemStack.class).inst(stack).mthd("method_58657/getEnchantments").invk();
            Set<?> enchantmentSet = (Set<?>) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57534/keySet").invk();
            for (Object enchantmentRegistryEntry : enchantmentSet) {
                String id = (String) registryEntryClass.inst(enchantmentRegistryEntry).mthd("method_55840/getRegisteredName").invk();
                if (id.equals("minecraft:efficiency")) {
                    return (int) itemEnchantmentsComponentClass.inst(enchantmentComponent).mthd("method_57536/getLevel", registryEntryClass).invk(enchantmentRegistryEntry);
                }
            }
            return 0;

        } else {
            Object efficiencyEnchantment = R.clz("net.minecraft.class_1893/net.minecraft.world.item.enchantment.Enchantments").fld("field_9131/EFFICIENCY").get();
            return (int) R.clz(EnchantmentHelper.class).mthd("method_8225/getItemEnchantmentLevel", Enchantment.class, ItemStack.class).invk(efficiencyEnchantment, stack);
        }
    }

    public static boolean canBeTraded(Enchantment enchantment) {
        if (!V.lower("1.21")) {
            ClientLevel world = Minecraft.getInstance().level;
            if (world == null) {
                return false;
            }

            Object registryManager = world.registryAccess();
            Object enchantmentRegistry = C.DynamicRegistryManager.inst(registryManager).mthd("method_30530/registryOrThrow/lookupOrThrow", C.RegistryKey)
                                                                 .invk(C.RegistryKeys.fld("field_41265/ENCHANTMENT").get());
            Object enchantmentRegistryEntry = C.Registry.inst(enchantmentRegistry).mthd("method_47983/wrapAsHolder", Object.class).invk(enchantment);
            return (boolean) C.RegistryEntry.inst(enchantmentRegistryEntry).mthd("method_40220/is", R.clz("net.minecraft.class_6862/net.minecraft.tags.TagKey"))
                                            .invk(R.clz("net.minecraft.class_9636/net.minecraft.tags.EnchantmentTags").fld("field_51545/TRADEABLE").get());
        } else {
            return (boolean) R.clz(Enchantment.class).inst(enchantment).mthd("method_25949/isTradeable").invk();
        }
    }

    public static void playFoundNotification(ClientLevel world, LocalPlayer player) {
        if (!LibrGetter.config.notify) {
            return;
        }
        R.clz(Level.class).inst(world).mthd("method_8486/playLocalSound", double.class, double.class, double.class, SoundEvent.class, SoundSource.class, float.class, float.class, boolean.class)
         .invk(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 10F, 0.7F, false);
    }

    public static void setActionResultFail(CallbackInfoReturnable<InteractionResult> info) {
        info.setReturnValue((InteractionResult) R.clz(InteractionResult.class).fld("field_5814/FAIL").get());
    }

    public static boolean isVillagerLibrarian(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        Object librarianProfession = C.VillagerProfession.fld("field_17060/LIBRARIAN").get();
        if (!V.lower("1.21.5")) {
            return (boolean) C.RegistryEntry.inst(R.clz(VillagerData.class).inst(villagerData).mthd("comp_3521/profession").invk()).mthd("method_40225/matchesKey", C.RegistryKey)
                                            .invk(librarianProfession);
        } else {
            return R.clz(VillagerData.class).inst(villagerData).mthd("method_16924/getProfession").invk().equals(librarianProfession);
        }
    }

    public static boolean isVillagerUnemployed(Villager villager) {
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

    public static void setSelectedSlot(Inventory inventory, int slot) {
        if (!V.lower("1.21.5")) {
            R.clz(Inventory.class).inst(inventory).mthd("method_61496/setSelectedHotbarSlot", int.class).invk(slot);
        } else {
            R.clz(Inventory.class).inst(inventory).fld("field_7545/selected").set(slot);
        }
    }

    public static void setScreen(Minecraft client, Screen screen) {
        R.clz(Minecraft.class).inst(client).mthd("method_1507/setScreen", Screen.class).invk(screen);
    }

    public static String translateEnchantmentId(String id) {
        Identifier iid = Identifier.tryParse(id);
        if (iid == null) {
            return id;
        }
        return translateEnchantmentId(iid);
    }

    public static String translateEnchantmentId(Identifier id) {
        Language lang = Language.getInstance();
        String full = "enchantment." + id.getNamespace() + "." + id.getPath();
        return (String) R.clz(Language.class).inst(lang).mthd(V.lower("1.19.4") ? "method_4679/get" : "method_48307/get", String.class).invk(full);
    }
}

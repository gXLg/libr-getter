package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.utils.chaining.tags.Tags;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.plugins.Plugins;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.versiont.gen.net.minecraft.nbt.CompoundTag;
import dev.gxlg.versiont.gen.net.minecraft.nbt.Tag;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser_1_17_0 extends Parser.Base {
    @Override
    public EnchantmentTrade parseTrade(MerchantOffer offer, ConfigManager configManager) throws LibrGetterException {
        ItemStack stack = offer.getResult();

        CompoundTag tag = getCustomData(stack);
        EnchantmentTrade.EnchantmentOnly finalEnchantment = Plugins.parse(tag);


        if (finalEnchantment == null) {
            finalEnchantment = parseStored(stack);
        }

        if (finalEnchantment == null) {
            // Nothing was found, so try fallback or return empty
            finalEnchantment = fallbackParse(tag, configManager);
        }

        if (finalEnchantment == null) {
            return null;
        }

        int price;
        ItemStack firstBuyItem = offer.getCostA();
        ItemStack secondBuyItem = offer.getCostB();

        if (firstBuyItem.getItem().equals(Items.EMERALD())) {
            price = firstBuyItem.getCount();
        } else if (secondBuyItem.getItem().equals(Items.EMERALD())) {
            price = secondBuyItem.getCount();
        } else {
            throw new InternalErrorException("buyItem");
        }

        return finalEnchantment.tradeWithPrice(price);
    }

    protected CompoundTag getCustomData(ItemStack stack) {
        return stack.getTag();
    }

    protected EnchantmentTrade.EnchantmentOnly parseStored(ItemStack stack) {
        CompoundTag tag = getCustomData(stack);
        if (tag == null) {
            return null;
        }

        List<Tag> list = null;
        // Legacy enchantment books
        if (tag.contains("Enchantments")) {
            list = Tags.getList(tag, "Enchantments", 10);
        }
        // Vanilla minecraft
        if (list == null && tag.contains("StoredEnchantments")) {
            list = Tags.getList(tag, "StoredEnchantments", 10);
        }

        // Insert more methods to find an enchantment here
        // ...

        if (list == null) {
            return null;
        }

        CompoundTag element = (CompoundTag) list.get(0);
        return new EnchantmentTrade.EnchantmentOnly(Tags.getString(element, "id"), Tags.getShort(element, "lvl"));
    }

    private EnchantmentTrade.EnchantmentOnly fallbackParse(CompoundTag tag, ConfigManager configManager) {
        if (!configManager.getBoolean(Config.FALLBACK)) {
            return null;
        }

        String string = tag.toString();
        Map<String, Set<Integer>> searching = new HashMap<>();
        for (EnchantmentTrade search : configManager.getData().getGoals()) {
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
            int foundIdEnd = found.get(id) + id.length();
            for (int lvl : searching.get(id)) {
                // search string
                int foundLvlIndex = string.indexOf(":\"" + lvl + "\"", foundIdEnd);
                if (foundLvlIndex == -1) {
                    // search number
                    foundLvlIndex = string.indexOf(":" + lvl, foundIdEnd);
                }
                if (foundLvlIndex == -1) {
                    continue;
                }

                if (foundLvlIndex < indexDistance) {
                    indexDistance = foundLvlIndex;
                    finalId = id;
                    finalLvl = lvl;
                }
            }
        }
        if (finalId == null) {
            return null;
        }
        return new EnchantmentTrade.EnchantmentOnly(finalId, finalLvl);
    }
}

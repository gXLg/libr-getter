package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.tags.Tags;
import dev.gxlg.librgetter.utils.plugins.Plugins;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser_1_17_0 extends Parser {
    @Override
    public EnchantmentTrade parseTrade(MerchantOfferWrapper offer) throws LibrGetterException {
        ItemStackWrapper stack = offer.getResult();

        CompoundTagWrapper tag = getCustomData(stack);
        EnchantmentTrade.EnchantmentOnly finalEnchantment = Plugins.parse(tag);


        if (finalEnchantment == null) {
            finalEnchantment = parseStored(stack);
        }

        if (finalEnchantment == null) {
            // Nothing was found, so try fallback or return empty
            finalEnchantment = fallbackParse(tag);
        }

        if (finalEnchantment == null) {
            return null;
        }

        int price;
        ItemStackWrapper firstBuyItem = offer.getCostA();
        ItemStackWrapper secondBuyItem = offer.getCostB();
        if (firstBuyItem.is(ItemsWrapper.EMERALD())) {
            price = firstBuyItem.getCount();
        } else if (secondBuyItem.is(ItemsWrapper.EMERALD())) {
            price = secondBuyItem.getCount();
        } else {
            throw new InternalErrorException("buyItem");
        }

        return finalEnchantment.tradeWithPrice(price);
    }

    protected CompoundTagWrapper getCustomData(ItemStackWrapper stack) {
        return stack.getTag();
    }

    protected EnchantmentTrade.EnchantmentOnly parseStored(ItemStackWrapper stack) {
        CompoundTagWrapper tag = getCustomData(stack);
        if (tag == null) {
            return null;
        }

        List<TagWrapper> list = null;
        // Legacy enchantment books
        if (tag.contains("Enchantments")) {
            list = Tags.getImpl().getList(tag, "Enchantments", 10);
        }
        // Vanilla minecraft
        if (list == null && tag.contains("StoredEnchantments")) {
            list = Tags.getImpl().getList(tag, "StoredEnchantments", 10);
        }

        // Insert more methods to find an enchantment here
        // ...

        if (list == null) {
            return null;
        }

        CompoundTagWrapper element = list.get(0).downcast(CompoundTagWrapper.class);
        return new EnchantmentTrade.EnchantmentOnly(Tags.getImpl().getString(element, "id"), Tags.getImpl().getShort(element, "lvl"));
    }

    private EnchantmentTrade.EnchantmentOnly fallbackParse(CompoundTagWrapper tag) {
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
        return new EnchantmentTrade.EnchantmentOnly(finalId, finalLvl);
    }
}

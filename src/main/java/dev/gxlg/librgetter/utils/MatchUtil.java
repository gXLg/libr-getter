package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.savefiles.config.Config;
import dev.gxlg.librgetter.savefiles.config.ConfigManager;
import dev.gxlg.librgetter.savefiles.config.types.enums.MatchMode;
import dev.gxlg.librgetter.savefiles.goals.GoalListManager;
import dev.gxlg.librgetter.utils.chaining.parser.Parser;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchUtil {
    public static List<EnchantmentTrade> parseTrades(List<MerchantOffer> offers, ConfigManager configManager, GoalListManager goalListManager) throws LibrGetterException {
        List<EnchantmentTrade> offeredEnchantments = new ArrayList<>();
        for (int i = 0; i < offers.size(); i++) {
            if (i >= 2 && configManager.getOptions(Config.MATCH_MODE) == MatchMode.VANILLA) {
                break;
            }
            MerchantOffer offer = offers.get(i);
            if (!isEnchantmentTrade(offer)) {
                continue;
            }
            EnchantmentTrade trade = Parser.parseTrade(offer, configManager, goalListManager);
            if (trade == null) {
                continue;
            }
            offeredEnchantments.add(trade);
            if (configManager.getOptions(Config.MATCH_MODE) == MatchMode.VANILLA) {
                break;
            }
        }
        return offeredEnchantments;
    }

    public static Optional<List<EnchantmentTrade>> matchTrades(List<EnchantmentTrade> offeredEnchantments, ConfigManager configManager, GoalListManager goalListManager) {
        return configManager.<MatchMode>getOptions(Config.MATCH_MODE).match(offeredEnchantments, configManager, goalListManager);
    }

    private static boolean isEnchantmentTrade(MerchantOffer offer) {
        return offer.getResult().getItem().equals(Items.ENCHANTED_BOOK()) || offer.getResult().getItem().equals(Items.BOOK());
    }
}

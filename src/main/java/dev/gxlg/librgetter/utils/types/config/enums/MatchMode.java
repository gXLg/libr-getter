package dev.gxlg.librgetter.utils.types.config.enums;

import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public enum MatchMode implements OptionsConfig<MatchMode> {

    VANILLA {
        @Override
        public Optional<List<EnchantmentTrade>> match(List<EnchantmentTrade> offeredEnchantments, ConfigManager configManager) {
            if (offeredEnchantments.isEmpty()) {
                return Optional.empty();
            }
            EnchantmentTrade firstOffer = offeredEnchantments.get(0);
            for (EnchantmentTrade goal : configManager.getData().getGoals()) {
                if (goal.meets(firstOffer)) {
                    return Optional.of(List.of(firstOffer));
                }
            }
            return Optional.empty();
        }
    },

    PERFECT {
        @Override
        public Optional<List<EnchantmentTrade>> match(List<EnchantmentTrade> offeredEnchantments, ConfigManager configManager) {
            if (offeredEnchantments.isEmpty()) {
                return Optional.empty();
            }

            List<EnchantmentTrade> matchedOffers = new ArrayList<>();
            for (EnchantmentTrade offer : offeredEnchantments) {
                boolean offerMatches = false;
                for (EnchantmentTrade goal : configManager.getData().getGoals()) {
                    if (goal.meets(offer)) {
                        offerMatches = true;
                        matchedOffers.add(offer);
                        break;
                    }
                }
                if (!offerMatches) {
                    return Optional.empty();
                }
            }
            return Optional.of(matchedOffers);
        }
    },

    ATLEAST {
        @Override
        public Optional<List<EnchantmentTrade>> match(List<EnchantmentTrade> offeredEnchantments, ConfigManager configManager) {
            List<EnchantmentTrade> matchedOffers = new ArrayList<>();
            Set<EnchantmentTrade> foundGoals = new HashSet<>();
            for (EnchantmentTrade offer : offeredEnchantments) {
                for (EnchantmentTrade goal : configManager.getData().getGoals()) {
                    if (goal.meets(offer)) {
                        matchedOffers.add(offer);
                        foundGoals.add(goal);
                    }
                }
            }
            int matchSize = Math.min(configManager.getInteger(Config.MATCH_AT_LEAST), configManager.getData().getGoals().size());
            if (foundGoals.size() < matchSize) {
                return Optional.empty();
            }
            return Optional.of(matchedOffers);
        }
    };

    public abstract Optional<List<EnchantmentTrade>> match(List<EnchantmentTrade> offeredEnchantments, ConfigManager configManager);

    @Override
    public MatchMode[] getValues() {
        return MatchMode.values();
    }
}
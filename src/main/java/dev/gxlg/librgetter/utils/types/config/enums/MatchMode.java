package dev.gxlg.librgetter.utils.types.config.enums;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum MatchMode implements OptionsConfig<MatchMode> {

    // only match the first found enchantment
    VANILLA {
        @Override
        public GoalMatching match(List<EnchantmentTrade> offeredEnchantments) {
            if (offeredEnchantments.isEmpty()) {
                return GoalMatching.noMatch();
            }
            EnchantmentTrade firstOffer = offeredEnchantments.get(0);
            for (EnchantmentTrade goal : LibrGetter.config.goals) {
                if (goal.meets(firstOffer)) {
                    return GoalMatching.match(List.of(firstOffer));
                }
            }
            return GoalMatching.noMatch();
        }
    },

    // only match if all offered are in goals list
    PERFECT {
        @Override
        public GoalMatching match(List<EnchantmentTrade> offeredEnchantments) {
            if (offeredEnchantments.isEmpty()) {
                return GoalMatching.noMatch();
            }

            List<EnchantmentTrade> matchedOffers = new ArrayList<>();
            for (EnchantmentTrade offer : offeredEnchantments) {
                boolean offerMatches = false;
                for (EnchantmentTrade goal : LibrGetter.config.goals) {
                    if (goal.meets(offer)) {
                        offerMatches = true;
                        matchedOffers.add(offer);
                        break;
                    }
                }
                if (!offerMatches) {
                    return GoalMatching.noMatch();
                }
            }
            return GoalMatching.match(matchedOffers);
        }
    },

    // match if at least N unique offers match
    ATLEAST {
        @Override
        public GoalMatching match(List<EnchantmentTrade> offeredEnchantments) {
            List<EnchantmentTrade> matchedOffers = new ArrayList<>();
            Set<EnchantmentTrade> foundGoals = new HashSet<>();
            for (EnchantmentTrade offer : offeredEnchantments) {
                for (EnchantmentTrade goal : LibrGetter.config.goals) {
                    if (goal.meets(offer)) {
                        matchedOffers.add(offer);
                        foundGoals.add(goal);
                    }
                }
            }
            int matchSize = Math.min(LibrGetter.config.matchAtLeast, LibrGetter.config.goals.size());
            if (foundGoals.size() < matchSize) {
                return GoalMatching.noMatch();
            }
            return GoalMatching.match(matchedOffers);
        }
    };

    public abstract GoalMatching match(List<EnchantmentTrade> offeredEnchantments);

    @Override
    public MatchMode[] getValues() {
        return MatchMode.values();
    }

    public static class GoalMatching {
        private final boolean match;

        private final List<EnchantmentTrade> matchedEnchantments;

        private GoalMatching(boolean matches, List<EnchantmentTrade> matchedEnchantments) {
            this.match = matches;
            this.matchedEnchantments = matchedEnchantments;
        }

        public boolean isMatch() {
            return match;
        }

        public List<EnchantmentTrade> getMatchedEnchantments() {
            // TODO: centralized exceptions
            if (!match) {
                throw new UnsupportedOperationException("Can't get matched enchantments, since it's not a match");
            }
            return matchedEnchantments;
        }

        public static GoalMatching match(List<EnchantmentTrade> matchedEnchantments) {
            return new GoalMatching(true, matchedEnchantments);
        }

        public static GoalMatching noMatch() {
            return new GoalMatching(false, null);
        }
    }
}

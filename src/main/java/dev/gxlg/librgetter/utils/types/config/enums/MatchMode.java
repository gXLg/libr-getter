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
            if (offeredEnchantments.isEmpty()) return GoalMatching.noMatch();

            for (EnchantmentTrade l : LibrGetter.config.goals) {
                if (l.meets(offeredEnchantments.get(0))) {
                    return GoalMatching.match(List.of(offeredEnchantments.get(0)));
                }
            }
            return GoalMatching.noMatch();
        }
    },

    // only match if all offered are in goals list
    PERFECT {
        @Override
        public GoalMatching match(List<EnchantmentTrade> offeredEnchantments) {
            if (offeredEnchantments.isEmpty()) return GoalMatching.noMatch();

            List<EnchantmentTrade> matched = new ArrayList<>();
            for (EnchantmentTrade offer : offeredEnchantments) {
                boolean thisMatch = false;
                for (EnchantmentTrade l : LibrGetter.config.goals) {
                    if (l.meets(offer)) {
                        thisMatch = true;
                        matched.add(offer);
                        break;
                    }
                }
                if (!thisMatch) {
                    return GoalMatching.noMatch();
                }
            }
            return GoalMatching.match(matched);
        }
    },

    // match if at least N unique offers match
    ATLEAST {
        @Override
        public GoalMatching match(List<EnchantmentTrade> offeredEnchantments) {
            List<EnchantmentTrade> matched = new ArrayList<>();
            Set<EnchantmentTrade> found = new HashSet<>();
            for (EnchantmentTrade offer : offeredEnchantments) {
                for (EnchantmentTrade l : LibrGetter.config.goals) {
                    if (l.meets(offer)) {
                        found.add(l);
                        matched.add(offer);
                    }
                }
            }
            if (found.size() < Math.min(LibrGetter.config.matchAtLeast, LibrGetter.config.goals.size())) {
                return GoalMatching.noMatch();
            }
            return GoalMatching.match(matched);
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
            if (!match) throw new UnsupportedOperationException("Can't get matched enchantments, since it's not a match");
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
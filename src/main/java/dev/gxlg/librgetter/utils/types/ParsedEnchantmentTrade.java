package dev.gxlg.librgetter.utils.types;

public class ParsedEnchantmentTrade {
    private final boolean isError;

    private final EnchantmentTrade trade;

    private final String[] errorMessage;

    private ParsedEnchantmentTrade(boolean isError, EnchantmentTrade trade, String[] errorMessage) {
        this.isError = isError;
        this.trade = trade;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return isError;
    }

    public EnchantmentTrade getTrade() {
        // TODO: centralized exceptions
        if (isError) {
            throw new IllegalStateException("Can't get the trade from an errored parse");
        }
        return trade;
    }

    public String[] getError() {
        // TODO: centralized exceptions
        if (!isError) {
            throw new IllegalStateException("Can't get the error from a successful parse");
        }
        return errorMessage;
    }

    public static ParsedEnchantmentTrade success(EnchantmentTrade trade) {
        return new ParsedEnchantmentTrade(false, trade, null);
    }

    public static ParsedEnchantmentTrade error(String... errorMessage) {
        return new ParsedEnchantmentTrade(true, null, errorMessage);
    }

    public static class Raw {
        private final boolean isError;

        private final String id;

        private final int level;

        private final String[] errorMessage;

        private Raw(boolean isError, String id, int level, String[] errorMessage) {
            this.isError = isError;
            this.id = id;
            this.level = level;
            this.errorMessage = errorMessage;
        }

        public EnchantmentTrade getTradeWithPrice(int price) {
            // TODO: centralized exceptions
            if (isError) {
                throw new IllegalStateException("Can't get the trade from an errored parse");
            }
            return new EnchantmentTrade(id, level, price);
        }


        public String[] getError() {
            // TODO: centralized exceptions
            if (!isError) {
                throw new IllegalStateException("Can't get the error from a successful parse");
            }
            return errorMessage;
        }

        public static Raw success(String id, int level) {
            return new Raw(false, id, level, null);
        }

        public static Raw error(String... errorMessage) {
            return new Raw(true, null, 0, errorMessage);
        }
    }
}

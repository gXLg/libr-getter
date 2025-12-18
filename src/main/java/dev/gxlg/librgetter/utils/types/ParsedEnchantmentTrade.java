package dev.gxlg.librgetter.utils.types;

import com.mojang.datafixers.util.Either;

public class ParsedEnchantmentTrade {
    private final boolean isError;
    private final Either<EnchantmentTrade, String[]> contents;

    private ParsedEnchantmentTrade(boolean isError, Either<EnchantmentTrade, String[]> contents) {
        this.isError = isError;
        this.contents = contents;
    }

    public boolean isError() {
        return isError;
    }

    public EnchantmentTrade getTrade() {
        if (isError) throw new UnsupportedOperationException("Can't get the trade from an errored parse");
        return contents.left().orElseThrow();
    }

    public String[] getError() {
        if (!isError) throw new UnsupportedOperationException("Can't get the error from a successfull parse");
        return contents.right().orElseThrow();
    }

    public static ParsedEnchantmentTrade success(EnchantmentTrade trade) {
        return new ParsedEnchantmentTrade(false, Either.left(trade));
    }

    public static ParsedEnchantmentTrade error(String... errorMessage) {
        return new ParsedEnchantmentTrade(true, Either.right(errorMessage));
    }
}

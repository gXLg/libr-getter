package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;

public class Parser {
    private static Base implementation = null;

    public static EnchantmentTrade parseTrade(MerchantOffer offer) throws LibrGetterException {
        return getImpl().parseTrade(offer);
    }

    private static Base getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.21.5")) {
            implementation = new Parser_1_17_0();
        } else {
            implementation = new Parser_1_20_5();
        }
        return implementation;
    }

    public abstract static class Base {
        public abstract EnchantmentTrade parseTrade(MerchantOffer offer) throws LibrGetterException;
    }
}

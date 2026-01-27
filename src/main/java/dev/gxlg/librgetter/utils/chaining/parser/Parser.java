package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper;

public abstract class Parser {
    public abstract EnchantmentTrade parseTrade(MerchantOfferWrapper offer) throws LibrGetterException;

    private static Parser implementation = null;

    public static Parser getImpl() {
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
}

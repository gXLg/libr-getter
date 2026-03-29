package dev.gxlg.librgetter.utils.chaining.parser;

import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;

public class Parser {
    private static final Base implementation;

    static {
        if (V.lower("1.20.5")) {
            implementation = new Parser_1_17_0();
        } else {
            implementation = new Parser_1_20_5();
        }
    }

    public static EnchantmentTrade parseTrade(MerchantOffer offer, ConfigManager configManager) throws LibrGetterException {
        return implementation.parseTrade(offer, configManager);
    }

    public abstract static class Base {
        public abstract EnchantmentTrade parseTrade(MerchantOffer offer, ConfigManager configManager) throws LibrGetterException;
    }
}

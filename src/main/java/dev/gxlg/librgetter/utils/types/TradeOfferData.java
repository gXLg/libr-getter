package dev.gxlg.librgetter.utils.types;

import dev.gxlg.multiversion.gen.net.minecraft.world.item.trading.MerchantOfferWrapper;

import java.util.List;

public class TradeOfferData {
    private final boolean canRefresh;

    private final List<MerchantOfferWrapper> tradeOfferList;

    private TradeOfferData(boolean canRefresh, List<MerchantOfferWrapper> list) {
        this.canRefresh = canRefresh;
        tradeOfferList = list;
    }

    public boolean canRefresh() {
        return canRefresh;
    }

    public List<MerchantOfferWrapper> getTradeOfferList() {
        return tradeOfferList;
    }

    public static TradeOfferData noRefresh() {
        return new TradeOfferData(false, null);
    }

    public static TradeOfferData offers(List<MerchantOfferWrapper> tradeOfferList) {
        return new TradeOfferData(true, tradeOfferList);
    }
}

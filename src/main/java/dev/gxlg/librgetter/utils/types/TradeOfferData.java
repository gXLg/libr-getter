package dev.gxlg.librgetter.utils.types;

import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffers;

public class TradeOfferData {
    private final boolean canRefresh;

    private final MerchantOffers tradeOfferList;

    private TradeOfferData(boolean canRefresh, MerchantOffers list) {
        this.canRefresh = canRefresh;
        tradeOfferList = list;
    }

    public boolean canRefresh() {
        return canRefresh;
    }

    public MerchantOffers getTradeOfferList() {
        return tradeOfferList;
    }

    public static TradeOfferData noRefresh() {
        return new TradeOfferData(false, null);
    }

    public static TradeOfferData offers(MerchantOffers tradeOfferList) {
        return new TradeOfferData(true, tradeOfferList);
    }
}

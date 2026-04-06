package dev.gxlg.librgetter.utils.types;

import dev.gxlg.versiont.gen.net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class TradeOfferData {
    private final boolean canRefresh;

    private final List<MerchantOffer> tradeOfferList;

    private TradeOfferData(boolean canRefresh, List<MerchantOffer> list) {
        this.canRefresh = canRefresh;
        tradeOfferList = list;
    }

    public boolean canRefresh() {
        return canRefresh;
    }

    public List<MerchantOffer> getTradeOfferList() {
        return tradeOfferList;
    }

    public static TradeOfferData noRefresh() {
        return new TradeOfferData(false, null);
    }

    public static TradeOfferData offers(List<MerchantOffer> tradeOfferList) {
        return new TradeOfferData(true, tradeOfferList);
    }
}

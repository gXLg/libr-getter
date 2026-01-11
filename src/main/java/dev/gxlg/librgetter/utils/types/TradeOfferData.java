package dev.gxlg.librgetter.utils.types;

import net.minecraft.village.TradeOfferList;

public class TradeOfferData {
    private final boolean canRefresh;

    private final TradeOfferList tradeOfferList;

    private TradeOfferData(boolean canRefresh, TradeOfferList list) {
        this.canRefresh = canRefresh;
        tradeOfferList = list;
    }

    public boolean canRefresh() {
        return canRefresh;
    }

    public TradeOfferList getTradeOfferList() {
        // TODO: centralized exceptions
        if (!canRefresh) {
            throw new IllegalStateException("Can't get the trades from non-refreshable offer data");
        }
        return tradeOfferList;
    }

    public static TradeOfferData noRefresh() {
        return new TradeOfferData(false, null);
    }

    public static TradeOfferData offers(TradeOfferList tradeOfferList) {
        return new TradeOfferData(true, tradeOfferList);
    }
}

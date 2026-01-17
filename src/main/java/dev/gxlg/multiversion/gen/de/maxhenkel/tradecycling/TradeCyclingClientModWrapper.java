package dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling;

import dev.gxlg.multiversion.R;

public class TradeCyclingClientModWrapper extends R.RWrapper<TradeCyclingClientModWrapper> {
    public static final R.RClass clazz = R.clz("de.maxhenkel.tradecycling.TradeCyclingClientMod");

    protected TradeCyclingClientModWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static TradeCyclingClientModWrapper inst(Object instance) {
        return new TradeCyclingClientModWrapper(instance);
    }

    public static void sendCycleTradesPacket(){
        clazz.mthd("sendCycleTradesPacket").invk();
    }
}
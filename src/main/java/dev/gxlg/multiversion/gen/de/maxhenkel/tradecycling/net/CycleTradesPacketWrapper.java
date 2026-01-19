package dev.gxlg.multiversion.gen.de.maxhenkel.tradecycling.net;

import dev.gxlg.multiversion.R;

public class CycleTradesPacketWrapper extends dev.gxlg.multiversion.gen.net.minecraft.network.protocol.common.custom.CustomPacketPayloadWrapper {
    public static final R.RClass clazz = R.clz("de.maxhenkel.tradecycling.net.CycleTradesPacket");

    public CycleTradesPacketWrapper(){
        this(clazz.constr().newInst().self());
    }

    protected CycleTradesPacketWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static CycleTradesPacketWrapper inst(Object instance) {
        return new CycleTradesPacketWrapper(instance);
    }
}
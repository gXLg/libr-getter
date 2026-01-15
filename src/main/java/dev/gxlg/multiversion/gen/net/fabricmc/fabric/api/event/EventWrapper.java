package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.event;

import dev.gxlg.multiversion.R;

public class EventWrapper extends R.RWrapper<EventWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.event.Event");

    protected EventWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public undefined register(undefined listener){
        clazz.mthd("register", undefined.class).invk(listener);
    }

    public static EventWrapper inst(Object instance) {
        return new EventWrapper(instance);
    }
}
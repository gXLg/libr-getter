package dev.gxlg.multiversion.gen.net.fabricmc.fabric.api.event;

import dev.gxlg.multiversion.R;

public class EventWrapper extends R.RWrapper<EventWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.event.Event");

    protected EventWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public void register(Object listener){
        clazz.inst(this.instance).mthd("register", Object.class).invk(listener);
    }

    public static EventWrapper inst(Object instance) {
        return new EventWrapper(instance);
    }
}
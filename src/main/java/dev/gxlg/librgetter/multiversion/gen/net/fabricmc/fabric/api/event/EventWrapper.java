package dev.gxlg.librgetter.multiversion.gen.net.fabricmc.fabric.api.event;

import dev.gxlg.librgetter.multiversion.R;

public class EventWrapper extends R.RWrapper<EventWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.event.Event");

    protected EventWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public void register(Object listener) {
         instance.mthd("register", Object.class).invk(listener);
    }

    public static EventWrapper inst(Object instance) {
        return new EventWrapper(instance);
    }
}

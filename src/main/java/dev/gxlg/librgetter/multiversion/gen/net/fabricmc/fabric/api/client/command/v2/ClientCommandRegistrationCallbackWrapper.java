package dev.gxlg.librgetter.multiversion.gen.net.fabricmc.fabric.api.client.command.v2;

import dev.gxlg.librgetter.multiversion.R;

public class ClientCommandRegistrationCallbackWrapper extends R.RWrapper<ClientCommandRegistrationCallbackWrapper> {
    public static final R.RClass clazz = R.clz("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");

    protected ClientCommandRegistrationCallbackWrapper(Object instance) {
        super(clazz.inst(instance));
    }

    public static ClientCommandRegistrationCallbackWrapper inst(Object instance) {
        return new ClientCommandRegistrationCallbackWrapper(instance);
    }

    public static dev.gxlg.librgetter.multiversion.gen.net.fabricmc.fabric.api.event.EventWrapper EVENT() {
        return dev.gxlg.librgetter.multiversion.gen.net.fabricmc.fabric.api.event.EventWrapper.inst(clazz.fld("EVENT").get());
    }
}

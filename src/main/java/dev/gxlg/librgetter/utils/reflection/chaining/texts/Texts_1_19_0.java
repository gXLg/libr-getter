package dev.gxlg.librgetter.utils.reflection.chaining.texts;

import dev.gxlg.librgetter.multiversion.C;
import dev.gxlg.librgetter.multiversion.R;

public class Texts_1_19_0 extends Texts_1_17_0 {
    @Override
    protected R.RClass getCmdSrcClass() {
        return R.clz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
    }

    @Override
    protected Object translatable(String message, Object... args) {
        return C.Text.mthd("method_43469/translatable", String.class, Object[].class).invk(message, args);
    }
}

package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.controller.SharedController;
import dev.gxlg.librgetter.utils.exceptions.LibrGetterException;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;

public class SelectKeybind extends Keybind {
    private final SharedController sharedController;

    public SelectKeybind(SharedController sharedController) {
        super("librgetter.keys.select", InputConstants$Type.KEYBOARD(), InputConstants.KEY_H());
        this.sharedController = sharedController;
    }

    @Override
    public void execute(Minecraft client) throws LibrGetterException {
        sharedController.selector();
    }
}

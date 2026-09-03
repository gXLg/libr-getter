package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.gui.impl.tradehall.TradehallScreen;
import dev.gxlg.librgetter.savefiles.tradehalls.TradehallAccessor;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public class TradehallScreenKeybind extends Keybind {
    private final TradehallAccessor tradehallAccessor;

    public TradehallScreenKeybind(TradehallAccessor tradehallAccessor) {
        super("librgetter.keys.tradehall", InputConstants$Type.KEYSYM(), GLFW.GLFW_KEY_PERIOD);
        this.tradehallAccessor = tradehallAccessor;
    }

    @Override
    public void execute(Minecraft client) {
        Screen lastScreen = Gui.getScreen(client);
        TradehallScreen newScreen = new TradehallScreen(lastScreen, tradehallAccessor);
        Gui.setScreen(client, newScreen);
    }
}

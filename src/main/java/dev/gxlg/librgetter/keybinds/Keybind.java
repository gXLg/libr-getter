package dev.gxlg.librgetter.keybinds;

import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.platform.InputConstants$Type;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;

public abstract class Keybind {
    private final String id;

    private final InputConstants$Type type;

    private final int key;

    public Keybind(String id, InputConstants$Type type, int key) {
        this.id = id;
        this.type = type;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public InputConstants$Type getType() {
        return type;
    }

    public int getKey() {
        return key;
    }

    public abstract void execute(Minecraft client) throws LibrGetterException;
}

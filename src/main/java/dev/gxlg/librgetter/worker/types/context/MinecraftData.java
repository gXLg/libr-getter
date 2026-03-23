package dev.gxlg.librgetter.worker.types.context;

import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.MultiPlayerGameMode;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

public class MinecraftData {
    @NotNull
    public final Minecraft client;

    @NotNull
    public final ClientLevel clientLevel;

    @NotNull
    public final LocalPlayer localPlayer;

    @NotNull
    public final MultiPlayerGameMode gameMode;

    @NotNull
    public final ClientPacketListener clientNetwork;

    public MinecraftData() throws InternalErrorException {
        client = Minecraft.getInstance();

        ClientLevel clientLevel = client.getLevelField();
        if (clientLevel == null) {
            throw new InternalErrorException("clientLevel");
        }
        this.clientLevel = clientLevel;

        LocalPlayer localPlayer = client.getPlayerField();
        if (localPlayer == null) {
            throw new InternalErrorException("localPlayer");
        }
        this.localPlayer = localPlayer;

        MultiPlayerGameMode gameMode = client.getGameModeField();
        if (gameMode == null) {
            throw new InternalErrorException("gameMode");
        }
        this.gameMode = gameMode;

        ClientPacketListener clientNetwork = client.getConnection();
        if (clientNetwork == null) {
            throw new InternalErrorException("clientNetwork");
        }
        this.clientNetwork = clientNetwork;
    }
}

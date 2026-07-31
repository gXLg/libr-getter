package dev.gxlg.librgetter.savefiles;

import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$DisconnectI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$JoinI;
import dev.gxlg.versiont.gen.net.minecraft.world.level.storage.LevelResource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.nio.file.Path;

public class WorldNameManager {
    private String worldName = null;

    public WorldNameManager() {
        ClientPlayConnectionEvents$JoinI join = (l, p, client) -> {
            if (client.isLocalServer()) {
                Path worldRoot = client.getSingleplayerServer().getWorldPath(LevelResource.ROOT());
                worldName = "local:" + worldRoot.getParent().getFileName().toString();
            } else {
                worldName = "server:" + client.getCurrentServer().getIpAddress();
            }
        };
        ClientPlayConnectionEvents.JOIN.register(join.unwrap(ClientPlayConnectionEvents.Join.class));

        ClientPlayConnectionEvents$DisconnectI disconnect = (l, c) -> worldName = null;
        ClientPlayConnectionEvents.DISCONNECT.register(disconnect.unwrap(ClientPlayConnectionEvents.Disconnect.class));
    }

    public String getWorldName() {
        return worldName;
    }
}

package dev.gxlg.librgetter.savefiles;

import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLevelEvents$AfterClientLevelChangeI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$DisconnectI;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$JoinI;
import dev.gxlg.versiont.gen.net.minecraft.world.level.storage.LevelResource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLevelEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.nio.file.Path;

public class WorldNameManager {
    private String saveFolder = null;

    private String dimensionName = null;

    public WorldNameManager() {
        ClientPlayConnectionEvents$JoinI join = (l, p, client) -> {
            if (client.isLocalServer()) {
                Path worldRoot = client.getSingleplayerServer().getWorldPath(LevelResource.ROOT());
                saveFolder = "local/" + worldRoot.getParent().getFileName().toString();
            } else {
                saveFolder = "server/" + client.getCurrentServer().getIpAddress();
            }
        };
        ClientPlayConnectionEvents.JOIN.register(join.unwrap(ClientPlayConnectionEvents.Join.class));

        ClientPlayConnectionEvents$DisconnectI disconnect = (l, c) -> {
            saveFolder = null;
            dimensionName = null;
        };
        ClientPlayConnectionEvents.DISCONNECT.register(disconnect.unwrap(ClientPlayConnectionEvents.Disconnect.class));

        ClientLevelEvents$AfterClientLevelChangeI afterLevelChange = (minecraft, clientLevel) -> dimensionName = clientLevel.dimension().identifier().toString();
        ClientLevelEvents.AFTER_CLIENT_LEVEL_CHANGE.register(afterLevelChange.unwrap(ClientLevelEvents.AfterClientLevelChange.class));
    }

    public String getSaveFolder() {
        return saveFolder;
    }

    public String getDimensionName() {
        return dimensionName;
    }
}

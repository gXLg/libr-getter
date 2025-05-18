package com.gxlg.librgetter.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.utils.reflection.Texts;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Updater {
    private static Pair<String, String> newVersion;

    public static void checkUpdates() {
        CompletableFuture.runAsync(() -> {
            try {
                URL url = URI.create("https://api.github.com/repos/gXLg/libr-getter/releases/latest").toURL();
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonObject data = new Gson().fromJson(reader, JsonObject.class);
                reader.close();

                String version = LibrGetter.getVersion();
                if (version == null) return;
                String newest = data.get("tag_name").getAsString();

                if (!newest.equals(version)) {
                    newVersion = new ImmutablePair<>(newest + " - " + data.get("name").getAsString(), data.get("body").getAsString().replace("\r", ""));
                }
            } catch (IOException ignored) {
            }
        });

        // notifying about update
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientPlayerEntity player = client.player;
            if (newVersion != null) {
                Texts.newVersion(player, newVersion.getLeft(), newVersion.getRight());
                newVersion = null;
            }
        });
    }
}

package dev.gxlg.librgetter.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$JoinI;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class Updater {
    private static final AtomicReference<NewVersion> newVersion = new AtomicReference<>();

    public static void checkUpdates() {
        if (!LibrGetter.config.checkUpdate) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                URL url = URI.create("https://api.github.com/repos/gXLg/libr-getter/releases/latest").toURL();
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonObject data = new Gson().fromJson(reader, JsonObject.class);
                reader.close();

                String version = LibrGetter.getVersion();
                if (version == null) {
                    return;
                }
                String newest = data.get("tag_name").getAsString();

                if (!newest.equals(version)) {
                    newVersion.set(new NewVersion(newest + " - " + data.get("name").getAsString(), data.get("body").getAsString().replace("\r", "")));
                }
            } catch (IOException ignored) {
            }
        });

        // notifying about update
        ClientPlayConnectionEvents$JoinI join = (h, s, c) -> {
            NewVersion version = Updater.newVersion.getAndSet(null);
            if (version != null) {
                Texts.getImpl().sendNewVersion(version.version(), version.changelog());
            }
        };
        ClientPlayConnectionEvents.JOIN.register(join.unwrap(ClientPlayConnectionEvents.Join.class));
    }

    private record NewVersion(String version, String changelog) { }
}

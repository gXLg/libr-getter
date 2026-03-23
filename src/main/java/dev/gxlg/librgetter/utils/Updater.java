package dev.gxlg.librgetter.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.NewVersionReleasedMessage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Updater {
    public static void checkUpdates(Notifier notifier, ConfigManager configManager, String modVersion) {
        if (!configManager.getBoolean(Config.CHECK_UPDATE)) {
            return;
        }
        if (modVersion == null) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                URL url = URI.create("https://api.github.com/repos/gXLg/libr-getter/releases/latest").toURL();
                InputStreamReader reader = new InputStreamReader(url.openStream());
                JsonObject data = new Gson().fromJson(reader, JsonObject.class);
                reader.close();

                String newest = data.get("tag_name").getAsString();

                if (newest.equals(modVersion)) {
                    return;
                }

                String versionName = newest + " - " + data.get("name").getAsString();
                String changelog = data.get("body").getAsString().replace("\r", "");
                notifier.addNotification(new NewVersionReleasedMessage(versionName, changelog));

            } catch (IOException ignored) {
            }
        });
    }
}

package dev.gxlg.librgetter.notifier;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.versiont.gen.net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents$JoinI;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Notifier {
    private final Queue<Message> notificationQueue = new ConcurrentLinkedQueue<>();

    public Notifier() {
        ClientPlayConnectionEvents$JoinI join = (h, s, c) -> {
            while (!notificationQueue.isEmpty()) {
                Message notification = notificationQueue.remove();
                Texts.sendMessage(notification);
            }
        };
        ClientPlayConnectionEvents.JOIN.register(join.unwrap(ClientPlayConnectionEvents.Join.class));
    }

    public void addNotification(Message notification) {
        Minecraft client = Minecraft.getInstance();
        if (client.getPlayerField() != null) {
            Texts.sendMessage(notification);
            return;
        }
        notificationQueue.add(notification);
    }
}

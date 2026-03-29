package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.types.exceptions.common.InternalErrorException;
import dev.gxlg.versiont.api.V;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientPacketListener_1_20_2;
import dev.gxlg.versiont.gen.net.minecraft.network.protocol.Packet;

import java.util.function.Consumer;

public class ClientNetwork {
    private final Consumer<Packet> sender;

    public ClientNetwork(Minecraft client) throws InternalErrorException {
        if (V.lower("1.20.2")) {
            ClientPacketListener clientNetwork = client.getConnection();
            if (clientNetwork == null) {
                throw new InternalErrorException("clientNetwork");
            }
            sender = clientNetwork::send;
        } else {
            ClientPacketListener_1_20_2 clientNetwork = client.getConnection2();
            if (clientNetwork == null) {
                throw new InternalErrorException("clientNetwork");
            }
            sender = clientNetwork::send;
        }
    }

    public void send(Packet packet) {
        sender.accept(packet);
    }
}

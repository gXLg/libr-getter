package com.gxlg.librgetter;

import com.gxlg.librgetter.command.LibrGetCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LibrGetter implements ClientModInitializer {
    public static final String MOD_ID = "librgetter";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient(){
        ClientCommandRegistrationCallback.EVENT.register(LibrGetCommand::register);
        LOGGER.info("Hello World from LibrGetter!");
    }
}

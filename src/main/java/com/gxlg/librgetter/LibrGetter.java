package com.gxlg.librgetter;

import com.gxlg.librgetter.command.LibrGetCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LibrGetter implements ModInitializer {
    public static final String MOD_ID = "librgetter";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize(){
        LibrGetCommand.register(ClientCommandManager.DISPATCHER);
        LOGGER.info("Hello World from LibrGetter!");
    }
}

package dev.gxlg.librgetter.command;

import dev.gxlg.librgetter.utils.chaining.commands.Commands;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.worker.Worker;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.versiont.gen.com.mojang.brigadier.CommandDispatcher;
import dev.gxlg.versiont.gen.net.minecraft.commands.CommandBuildContext;

import java.util.List;

public class CommandsManager {
    private final List<Command> commands;

    public CommandsManager(ConfigManager configManager, UserSchedulerController controller, Worker worker) {
        this.commands = List.of(new LibrGetCommand(configManager, controller, worker));
    }

    public void register() {
        Commands.registerCommands((dispatcher, context) -> commands.forEach(command -> command.register(dispatcher, context)));
    }

    public interface Command {
        void register(CommandDispatcher dispatcher, CommandBuildContext context);
    }
}

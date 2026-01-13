package dev.gxlg.librgetter.utils.types.exceptions.commands;

import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;

public class CommandException extends Exception {
    private final String error;

    public CommandException(String error) {
        this.error = error;
    }

    public void sendErrorToPlayer() {
        Texts.getImpl().sendTranslatableError(error);
    }
}

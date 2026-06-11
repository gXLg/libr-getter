package dev.gxlg.librgetter.utils.messages.translatable.error;

public class CouldNotReadSaveFileMessage extends TranslatableErrorMessage {
    public CouldNotReadSaveFileMessage(String fileName) {
        super("librgetter.error.saveFile.read", fileName);
    }
}

package dev.gxlg.librgetter.utils.messages.translatable.error;

public class CouldNotSaveSaveFileMessage extends TranslatableErrorMessage {
    public CouldNotSaveSaveFileMessage(String fileName) {
        super("librgetter.error.saveFile.save", fileName);
    }
}

package dev.gxlg.librgetter.utils.messages.translatable.error;

public class CouldNotInitSaveFileMessage extends TranslatableErrorMessage {
    public CouldNotInitSaveFileMessage(String fileName) {
        super("librgetter.error.saveFile.init", fileName);
    }
}

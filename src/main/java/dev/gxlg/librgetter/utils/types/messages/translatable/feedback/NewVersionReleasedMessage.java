package dev.gxlg.librgetter.utils.types.messages.translatable.feedback;

public class NewVersionReleasedMessage extends TranslatableFeedbackMessage {
    public NewVersionReleasedMessage(String releaseName) {
        super("librgetter.feedback.version", releaseName);
    }
}

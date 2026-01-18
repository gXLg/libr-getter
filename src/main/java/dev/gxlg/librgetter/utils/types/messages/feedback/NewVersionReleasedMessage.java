package dev.gxlg.librgetter.utils.types.messages.feedback;

public class NewVersionReleasedMessage extends TranslatableFeedbackMessage {
    public NewVersionReleasedMessage(String releaseName) {
        super("librgetter.feedback.version", releaseName);
    }
}

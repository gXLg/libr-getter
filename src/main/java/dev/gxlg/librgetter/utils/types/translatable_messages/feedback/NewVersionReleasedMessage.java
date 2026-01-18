package dev.gxlg.librgetter.utils.types.translatable_messages.feedback;

public class NewVersionReleasedMessage extends TranslatableFeedbackMessage {
    public NewVersionReleasedMessage(String releaseName) {
        super("librgetter.feedback.version", releaseName);
    }
}

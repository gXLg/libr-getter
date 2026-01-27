package dev.gxlg.librgetter.utils.types.messages.translatable.config;

import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;

public class TranslatableCategory extends TranslatableMessage {
    public TranslatableCategory(String category) {
        super("librgetter.category." + category);
    }
}

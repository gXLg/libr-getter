package dev.gxlg.librgetter.utils.messages.translatable.config;

import dev.gxlg.librgetter.config.ConfigManager;
import dev.gxlg.librgetter.utils.messages.translatable.TranslatableMessage;

public class TranslatableCategory extends TranslatableMessage {
    public TranslatableCategory(ConfigManager.Category category) {
        super("librgetter.category." + category.getId());
    }
}

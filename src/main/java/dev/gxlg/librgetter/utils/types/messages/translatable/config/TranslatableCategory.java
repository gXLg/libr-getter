package dev.gxlg.librgetter.utils.types.messages.translatable.config;

import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;

public class TranslatableCategory extends TranslatableMessage {
    public TranslatableCategory(ConfigManager.Category category) {
        super("librgetter.category." + category.getId());
    }
}

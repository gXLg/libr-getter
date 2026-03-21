package dev.gxlg.librgetter.utils.types.messages.objects.configScreen;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.messages.translatable.config.TranslatableCategory;
import dev.gxlg.librgetter.utils.types.messages.translatable.partial.TranslatableConfigMenuName;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;

import java.util.Map;

public class FirstPageContent extends PageContent {
    private final Map<ConfigManager.Category, Integer> categoryIndexMap;

    public FirstPageContent(Map<ConfigManager.Category, Integer> categoryIndexMap) {
        this.categoryIndexMap = Map.copyOf(categoryIndexMap);
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = title();
        text = text.append(new TranslatableConfigMenuName().getComponent());
        text = text.append("\n");
        for (ConfigManager.Category category : ConfigManager.Category.values()) {
            text = text.append("\n* ");
            text = text.append(new TranslatableCategory(category).getComponent().withStyle(Style.EMPTY().withClickEvent(Texts.paging(categoryIndexMap.get(category) + 1))));
        }
        return text;
    }
}

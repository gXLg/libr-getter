package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenu {
    public static final int CONFIGS_PER_PAGE = 4;

    public static final int pageCount;

    private static final List<Component> list = new ArrayList<>();

    private static final Map<String, Integer> categoryPageIndices = new HashMap<>();

    static {
        int pageIndex = 1;
        for (String cat : ConfigManager.CATEGORIES) {
            categoryPageIndices.put(cat, pageIndex);
            pageIndex += (int) Math.ceil(LibrGetter.configManager.getConfigurablesForCategory(cat).size() / ((float) CONFIGS_PER_PAGE));
        }
        pageCount = pageIndex;

        // pre-fill pages
        for (int i = 0; i < pageCount; i++) {
            list.add(Component.nullToEmpty(""));
        }
    }

    public static BookViewScreen$BookAccess createNewBookAccess() {
        for (int i = 0; i < pageCount; i++) {
            updatePageContent(i);
        }
        return Gui.createBookAccess(list);
    }

    public static BookViewScreen$BookAccess getUpdatedBookAccess(int updatePageIndex) {
        updatePageContent(updatePageIndex);
        return Gui.createBookAccess(list);
    }

    private static void updatePageContent(int index) {
        MutableComponent text;
        if (index == 0) {
            text = Texts.bookMainPage(categoryPageIndices);

        } else {
            List<String> reversed = new ArrayList<>(ConfigManager.CATEGORIES);
            Collections.reverse(reversed);
            String category = reversed.stream().filter(c -> categoryPageIndices.get(c) <= index).findFirst().orElseThrow(() -> new RuntimeException("Invalid index " + index));
            text = Texts.bookTitle(category);

            int j = index - categoryPageIndices.get(category);
            int finish = Math.min(j * CONFIGS_PER_PAGE + CONFIGS_PER_PAGE, LibrGetter.configManager.getConfigurablesForCategory(category).size());
            for (int i = j * CONFIGS_PER_PAGE; i < finish; i++) {
                Configurable<?> config = LibrGetter.configManager.getConfigurablesForCategory(category).get(i);
                text = Texts.bookEntry(text, config);
            }
        }
        list.set(index, text);
    }
}

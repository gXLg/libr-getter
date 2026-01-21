package dev.gxlg.librgetter.gui;

import dev.gxlg.librgetter.ConfigManager;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenu {
    public static final int CONFIGS_PER_PAGE = 4;

    public static final int pageCount;

    private static final List<ComponentWrapper> list = new ArrayList<>();

    private static final Map<String, Integer> categories = new HashMap<>();

    private static BookViewScreen.BookAccess cachedContent = null;

    static {
        int pages = 1;
        for (String cat : ConfigManager.CATEGORIES) {
            categories.put(cat, pages);
            pages += (int) Math.ceil(LibrGetter.configManager.getConfigurablesForCategory(cat).size() / ((float) CONFIGS_PER_PAGE));
        }
        pageCount = pages;

        // pre-fill pages
        for (int i = 0; i < pageCount; i++) {
            list.add(null);
            updatePage(i);
        }
    }

    public static BookViewScreen.BookAccess getCachedContent() {
        if (cachedContent == null) {
            updateAndReturnContent();
        }
        return cachedContent;
    }

    public static BookViewScreen.BookAccess updateAndReturnContent() {
        cachedContent = Gui.getImpl().getBookAccess(list);
        return cachedContent;
    }

    public static void updatePage(int index) {
        MutableComponentWrapper text;
        if (index == 0) {
            text = Texts.getImpl().bookMainPage(categories);

        } else {
            List<String> reversed = new ArrayList<>(ConfigManager.CATEGORIES);
            Collections.reverse(reversed);
            String category = reversed.stream().filter(c -> categories.get(c) <= index).findFirst().orElseThrow(() -> new RuntimeException("Invalid index " + index));
            text = Texts.getImpl().bookTitle(category);

            int j = index - categories.get(category);
            int finish = Math.min(j * CONFIGS_PER_PAGE + CONFIGS_PER_PAGE, LibrGetter.configManager.getConfigurablesForCategory(category).size());
            for (int i = j * CONFIGS_PER_PAGE; i < finish; i++) {
                Configurable<?> config = LibrGetter.configManager.getConfigurablesForCategory(category).get(i);
                text = Texts.getImpl().bookEntry(text, config);
            }
        }
        list.set(index, text);
    }
}

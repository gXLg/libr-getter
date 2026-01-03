package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.multiversion.R;
import dev.gxlg.librgetter.multiversion.V;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.text.StringVisitable;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMenu {
    public static final int CONFIGS_PER_PAGE = 4;

    public static final int pageCount;

    private static final List<Object> list = new ArrayList<>();

    private static final Map<String, Integer> categories = new HashMap<>();

    static {
        int pages = 1;
        for (String cat : Config.CATEGORIES) {
            categories.put(cat, pages);
            pages += (int) Math.ceil(LibrGetter.config.getConfigurablesForCategory(cat).size() / ((float) CONFIGS_PER_PAGE));
        }
        pageCount = pages;
    }

    public static BookScreen.Contents getContent() {
        if (list.isEmpty()) {
            for (int i = 0; i < pageCount; i++) {
                list.add(null);
                updatePage(i); // pre-fill pages
            }
        }

        if (!V.lower("1.20.5")) {
            return (BookScreen.Contents) R.clz(BookScreen.Contents.class).constr(List.class).newInst(list).self();
        }

        return (BookScreen.Contents) Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ BookScreen.Contents.class }, (proxy, method, args) -> {
                String name = method.getName();
                if (name.equals("method_17560") || name.equals("getPageCount")) {
                    return pageCount;

                } else if (name.equals("method_17563") || name.equals("getPage")) {
                    Integer index = (Integer) args[0];
                    if (index < 0 || index >= pageCount) {
                        return StringVisitable.EMPTY;
                    }
                    return list.get(index);
                }
                return method.invoke(proxy, args);
            }
        );
    }

    public static void updatePage(int index) {
        Object text;
        if (index == 0) {
            text = Texts.getImpl().bookMainPage(categories);

        } else {
            List<String> reversed = new ArrayList<>(Config.CATEGORIES);
            Collections.reverse(reversed);
            String category = reversed.stream().filter(c -> categories.get(c) <= index).findFirst().orElseThrow(() -> new RuntimeException("Invalid index " + index));
            text = Texts.getImpl().bookTitle(category);

            int j = index - categories.get(category);
            int finish = Math.min(j * CONFIGS_PER_PAGE + CONFIGS_PER_PAGE, LibrGetter.config.getConfigurablesForCategory(category).size());
            for (int i = j * CONFIGS_PER_PAGE; i < finish; i++) {
                Configurable<?> config = LibrGetter.config.getConfigurablesForCategory(category).get(i);
                text = Texts.getImpl().bookEntry(text, config);
            }
        }
        list.set(index, text);
    }
}

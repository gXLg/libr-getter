package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.Reflection;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.text.StringVisitable;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ConfigMenu {
    private static final List<Object> list = new ArrayList<>();
    public static int pageCount = (int) Math.ceil(Config.getConfigurables().size() / 4.0);

    public static BookScreen.Contents getContent() {
        if (list.isEmpty()) {
            for (int i = 0; i < pageCount; i++) {
                list.add(null);
                updatePage(i);
            }
        }

        if (Reflection.version(">= 1.20.5")) {
            return (BookScreen.Contents) Reflection.wrap("BookScreen.Contents List:list");
        }

        return (BookScreen.Contents) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{BookScreen.Contents.class}, (proxy, method, args) -> {
            String name = method.getName();
            if (name.equals("method_17560") || name.equals("getPageCount")) {
                return pageCount;

            } else if (name.equals("method_17563") || name.equals("getPage")) {
                Integer index = (Integer) args[0];
                if (index < 0 || index >= pageCount) return StringVisitable.EMPTY;
                return list.get(index);
            }
            return method.invoke(proxy, args);
        });
    }

    public static void updatePage(int index) {
        Object text = Texts.bookTitle();
        int finish = Math.min(index * 4 + 4, Config.getConfigurables().size());
        for (int i = index * 4; i < finish; i++) {
            Config.Configurable<?> config = Config.getConfigurables().get(i);
            text = Texts.bookEntry(text, config);
        }

        list.set(index, text);
    }
}

package dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory;

import dev.gxlg.multiversion.R;

import java.lang.reflect.Proxy;

public interface BookViewScreen$BookAccessWrapperInterface extends R.RWrapperInterface<BookViewScreen$BookAccessWrapper> {
    R.RClass clazz = R.clz("net.minecraft.class_3872$class_3931/net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess");

    int getPageCount();

    dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper getPage(int index);

    @Override
    default BookViewScreen$BookAccessWrapper wrapper() {
        return BookViewScreen$BookAccessWrapper.inst(Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(), new Class[]{ clazz.self() }, (proxy, method, args) -> {
                String methodName = method.getName();
                if (methodName.equals("method_17560") || methodName.equals("getPageCount")) {
                    return this.getPageCount();
                }

                if (methodName.equals("method_17563") || methodName.equals("getPage")) {
                    return this.getPage((int) args[0]).unwrap();
                }
                return method.invoke(proxy, args);
            }
        ));
    }
}
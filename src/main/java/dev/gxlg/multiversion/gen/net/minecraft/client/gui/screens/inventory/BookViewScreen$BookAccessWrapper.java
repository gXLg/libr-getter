package dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BookViewScreen$BookAccessWrapper extends R.RWrapper<BookViewScreen$BookAccessWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3872$class_3931/net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccess");

    private int superCall = 0;

    public BookViewScreen$BookAccessWrapper(java.util.List<dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper> pages) {
        this(clazz.constr(java.util.List.class).newInst(dev.gxlg.multiversion.adapters.java.util.ListAdapter.<dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper>unwrapper(dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper::unwrap).apply(pages)).self());
    }

    protected BookViewScreen$BookAccessWrapper(Object instance) {
        super(instance);
    }

    public static BookViewScreen$BookAccessWrapper inst(Object instance) {
        return instance == null ? null : new BookViewScreen$BookAccessWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BookViewScreen$BookAccessWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
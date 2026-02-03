package dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class BookViewScreenWrapper extends dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3872/net.minecraft.client.gui.screens.inventory.BookViewScreen");

    private int superCall = 0;

    protected BookViewScreenWrapper(R.RClass eClazz, dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapper bookAccess) {
        this(eClazz, eClazz.constr(dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapper.clazz.self()).newInst(bookAccess.unwrap()).self());
    }

    protected BookViewScreenWrapper(R.RClass eClazz, Object instance) {
        this(instance);
        eClazz.inst(this.instance).fld("__wrapper", BookViewScreenWrapper.class).set(this);
    }

    protected BookViewScreenWrapper(Object instance) {
        super(instance);
    }

    protected boolean forcePage(int page){
        if (this instanceof BookViewScreenWrapper && this.getClass() != BookViewScreenWrapper.class) superCall++;
        return (boolean) clazz.inst(this.instance).mthd("method_17789/forcePage", boolean.class, int.class).invk(page);
    }

    protected void init(){
        if (this instanceof BookViewScreenWrapper && this.getClass() != BookViewScreenWrapper.class) superCall++;
        clazz.inst(this.instance).mthd("method_25426/init", void.class).invk();
    }

    protected void pageBack(){
        if (this instanceof BookViewScreenWrapper && this.getClass() != BookViewScreenWrapper.class) superCall++;
        clazz.inst(this.instance).mthd("method_17057/pageBack", void.class).invk();
    }

    protected void pageForward(){
        if (this instanceof BookViewScreenWrapper && this.getClass() != BookViewScreenWrapper.class) superCall++;
        clazz.inst(this.instance).mthd("method_17058/pageForward", void.class).invk();
    }

    protected void closeScreen(){
        if (this instanceof BookViewScreenWrapper && this.getClass() != BookViewScreenWrapper.class) superCall++;
        clazz.inst(this.instance).mthd("method_34494/closeScreen", void.class).invk();
    }

    public void setBookAccess(dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapper bookAccess){
        clazz.inst(this.instance).mthd("method_17554/setBookAccess", void.class, dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.inventory.BookViewScreen$BookAccessWrapper.clazz.self()).invk(bookAccess.unwrap());
    }

    public static BookViewScreenWrapper inst(Object instance) {
        return instance == null ? null : new BookViewScreenWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") BookViewScreenWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();
            if ((methodName.equals("method_17789") || methodName.equals("forcePage")) && R.methodMatches(method, boolean.class, int.class)) {
                return wrapper.forcePage((int) args[0]);
            }

            if ((methodName.equals("method_25426") || methodName.equals("init")) && R.methodMatches(method, void.class)) {
                wrapper.init();
                return null;
            }

            if ((methodName.equals("method_17057") || methodName.equals("pageBack")) && R.methodMatches(method, void.class)) {
                wrapper.pageBack();
                return null;
            }

            if ((methodName.equals("method_17058") || methodName.equals("pageForward")) && R.methodMatches(method, void.class)) {
                wrapper.pageForward();
                return null;
            }

            if ((methodName.equals("method_34494") || methodName.equals("closeScreen")) && R.methodMatches(method, void.class)) {
                wrapper.closeScreen();
                return null;
            }
            return dev.gxlg.multiversion.gen.net.minecraft.client.gui.screens.ScreenWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
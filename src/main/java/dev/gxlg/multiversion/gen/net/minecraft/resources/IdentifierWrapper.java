package dev.gxlg.multiversion.gen.net.minecraft.resources;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class IdentifierWrapper extends R.RWrapper<IdentifierWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2960/net.minecraft.resources.ResourceLocation/net.minecraft.resources.Identifier");

    private int superCall = 0;

    protected IdentifierWrapper(Object instance) {
        super(instance);
    }

    public String getNamespace(){
        return (String) clazz.inst(this.instance).mthd("method_12836/getNamespace", String.class).invk();
    }

    public String getPath(){
        return (String) clazz.inst(this.instance).mthd("method_12832/getPath", String.class).invk();
    }

    public String toString(){
        return (String) clazz.inst(this.instance).mthd("toString", String.class).invk();
    }

    public static IdentifierWrapper inst(Object instance) {
        return instance == null ? null : new IdentifierWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper tryParse(String location){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.inst(clazz.mthd("method_12829/tryParse", dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.clazz.self(), String.class).invk(location));
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper tryBuild(String namespace, String path){
        return dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.inst(clazz.mthd("method_43902/tryBuild", dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper.clazz.self(), String.class, String.class).invk(namespace, path));
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") IdentifierWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
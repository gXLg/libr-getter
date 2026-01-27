package dev.gxlg.multiversion.gen.net.minecraft.nbt;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CompoundTagWrapper extends dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2487/net.minecraft.nbt.CompoundTag");

    private int superCall = 0;

    protected CompoundTagWrapper(Object instance) {
        super(instance);
    }

    public String getString(String name){
        return (String) clazz.inst(this.instance).mthd("method_10558/getString", String.class, String.class).invk(name);
    }

    public String getStringOr(String name, String or){
        return (String) clazz.inst(this.instance).mthd("method_68564/getStringOr", String.class, String.class, String.class).invk(name, or);
    }

    public boolean isEmpty(){
        return (boolean) clazz.inst(this.instance).mthd("method_33133/isEmpty", boolean.class).invk();
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper getCompound(String name){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(clazz.inst(this.instance).mthd("method_10562/getCompound", dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.clazz, String.class).invk(name));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper getCompoundOrEmpty(String name){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(clazz.inst(this.instance).mthd("method_68568/getCompoundOrEmpty", dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.clazz, String.class).invk(name));
    }

    public java.util.Set<String> keySet(){
        return dev.gxlg.multiversion.adapters.java.util.SetAdapter.wrapper(x -> (String) x).apply(clazz.inst(this.instance).mthd("method_10541/keySet", java.util.Set.class).invk());
    }

    public java.util.List<dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper> getList(String name, int type){
        return dev.gxlg.multiversion.adapters.java.util.ListAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper::inst).apply(clazz.inst(this.instance).mthd("method_10554/getList", java.util.List.class, String.class, int.class).invk(name, type));
    }

    public java.util.List<dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper> getListOrEmpty(String name){
        return dev.gxlg.multiversion.adapters.java.util.ListAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper::inst).apply(clazz.inst(this.instance).mthd("method_68569/getListOrEmpty", java.util.List.class, String.class).invk(name));
    }

    public short getShort(String name){
        return (short) clazz.inst(this.instance).mthd("method_10568/getShort", short.class, String.class).invk(name);
    }

    public short getShortOr(String name, short or){
        return (short) clazz.inst(this.instance).mthd("method_68565/getShortOr", short.class, String.class, short.class).invk(name, or);
    }

    public boolean contains(String name){
        return (boolean) clazz.inst(this.instance).mthd("method_10545/contains", boolean.class, String.class).invk(name);
    }

    public String toString(){
        return (String) clazz.inst(this.instance).mthd("toString", String.class).invk();
    }

    public static CompoundTagWrapper inst(Object instance) {
        return instance == null ? null : new CompoundTagWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") CompoundTagWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
package dev.gxlg.multiversion.gen.net.minecraft.nbt;

import dev.gxlg.multiversion.R;

public class CompoundTagWrapper extends dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2487/net.minecraft.nbt.CompoundTag");

    protected CompoundTagWrapper(Object instance) {
        super(instance);
    }

    public String getString(String name){
        return (String) this.instance.mthd("method_10558/getString", String.class).invk(name);
    }

    public String getStringOr(String name, String or){
        return (String) this.instance.mthd("method_68564/getStringOr", String.class, String.class).invk(name, or);
    }

    public boolean isEmpty(){
        return (boolean) this.instance.mthd("method_33133/isEmpty").invk();
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper getCompound(String name){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(this.instance.mthd("method_10562/getCompound", String.class).invk(name));
    }

    public dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper getCompoundOrEmpty(String name){
        return dev.gxlg.multiversion.gen.net.minecraft.nbt.CompoundTagWrapper.inst(this.instance.mthd("method_68568/getCompoundOrEmpty", String.class).invk(name));
    }

    public java.util.Set<String> keySet(){
        return dev.gxlg.multiversion.adapters.java.util.SetAdapter.wrapper(x -> (String) x).apply(this.instance.mthd("method_10541/keySet").invk());
    }

    public java.util.List<dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper> getList(String name, int type){
        return dev.gxlg.multiversion.adapters.java.util.ListAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper::inst).apply(this.instance.mthd("method_10554/getList", String.class, int.class).invk(name, type));
    }

    public java.util.List<dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper> getListOrEmpty(String name){
        return dev.gxlg.multiversion.adapters.java.util.ListAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.nbt.TagWrapper::inst).apply(this.instance.mthd("method_68569/getListOrEmpty", String.class).invk(name));
    }

    public short getShort(String name){
        return (short) this.instance.mthd("method_10568/getShort", String.class).invk(name);
    }

    public short getShortOr(String name, short or){
        return (short) this.instance.mthd("method_68565/getShortOr", String.class, short.class).invk(name, or);
    }

    public boolean contains(String name){
        return (boolean) this.instance.mthd("method_10545/contains", String.class).invk(name);
    }

    public static CompoundTagWrapper inst(Object instance) {
        return new CompoundTagWrapper(instance);
    }
}
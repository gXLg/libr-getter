package dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment;

import dev.gxlg.multiversion.R;

public class ItemEnchantmentsWrapper extends R.RWrapper<ItemEnchantmentsWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_9304/net.minecraft.world.item.enchantment.ItemEnchantments");

    protected ItemEnchantmentsWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public java.util.Set<it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper>> entrySet(){
        return dev.gxlg.multiversion.adapters.java.util.SetAdapter.wrapper(dev.gxlg.multiversion.adapters.it.unimi.dsi.fastutil.objects.Object2IntMap$EntryAdapter.wrapper(dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper::inst)).apply(clazz.inst(this.instance).mthd("method_57539/entrySet").invk());
    }

    public static ItemEnchantmentsWrapper inst(Object instance) {
        return new ItemEnchantmentsWrapper(instance);
    }
}
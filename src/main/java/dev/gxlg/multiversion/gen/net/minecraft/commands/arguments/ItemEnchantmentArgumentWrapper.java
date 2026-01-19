package dev.gxlg.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.multiversion.R;

public class ItemEnchantmentArgumentWrapper extends R.RWrapper<ItemEnchantmentArgumentWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2194/net.minecraft.commands.arguments.ItemEnchantmentArgument");

    protected ItemEnchantmentArgumentWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public static ItemEnchantmentArgumentWrapper inst(Object instance) {
        return new ItemEnchantmentArgumentWrapper(instance);
    }

    public static com.mojang.brigadier.arguments.ArgumentType enchantment(){
        return (com.mojang.brigadier.arguments.ArgumentType) clazz.mthd("method_9336/enchantment").invk();
    }
}
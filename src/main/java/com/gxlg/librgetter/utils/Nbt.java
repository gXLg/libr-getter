package com.gxlg.librgetter.utils;

import java.util.Set;

public class Nbt {
    public static Class<?> compound = Reflection.clazz("net.minecraft.class_2487", "net.minecraft.nbt.CompoundTag", "net.minecraft.nbt.NbtCompound");
    public static Class<?> list = Reflection.clazz("net.minecraft.class_2499", "net.minecraft.nbt.ListTag", "net.minecraft.nbt.NbtList");

    public static String getString(Object element, String name) {
        return (String) Reflection.invokeMethod(compound, element, new Object[]{name}, "method_10558", "getString");
    }

    public static Object getCompound(Object tag, String name) {
        return Reflection.invokeMethod(compound, tag, new Object[]{name}, "method_10562", "getCompound");
    }

    public static Set<?> getKeys(Object element) {
        return (Set<?>) Reflection.invokeMethod(compound, element, null, "method_10541", "getKeys");
    }

    public static Object getList(Object tag, String name, int type) {
        return Reflection.invokeMethod(compound, tag, new Object[]{name, type}, new Class[]{String.class, int.class}, "method_10554", "getList");
    }

    public static boolean contains(Object tag, String name) {
        return (boolean) Reflection.invokeMethod(compound, tag, new Object[]{name}, "method_10545", "contains");
    }

    public static short getShort(Object element, String name) {
        return (short) Reflection.invokeMethod(compound, element, new Object[]{name}, "method_10568", "getShort");
    }
}

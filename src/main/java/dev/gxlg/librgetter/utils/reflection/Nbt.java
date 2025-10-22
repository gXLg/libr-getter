package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.Reflection;

import java.util.Optional;
import java.util.Set;

public class Nbt {
    public static Class<?> compound = (Class<?>) Reflection.wrap(".class_2487/.nbt.CompoundTag/.nbt.NbtCompound");
    public static Class<?> list = (Class<?>) Reflection.wrap(".class_2499/.nbt.ListTag/.nbt.NbtList");

    public static String getString(Object element, String name) {
        if (Reflection.version(">= 1.21.5")) {
            return (String) ((Optional<?>) Reflection.wrapn("compound:element method_10558/getString name", compound, element, name)).orElse(null);
        } else {
            return (String) Reflection.wrap("compound:element method_10558/getString name", compound, element, name);
        }
    }

    public static Object getCompound(Object element, String name) {
        if (Reflection.version(">= 1.21.5")) {
            return ((Optional<?>) Reflection.wrapn("compound:element method_10562/getCompound name", compound, element, name)).orElse(null);
        } else {
            return Reflection.wrap("compound:element method_10562/getCompound name", compound, element, name);
        }
    }

    public static Set<?> getKeys(Object element) {
        return (Set<?>) Reflection.wrap("compound:element method_10541/getKeys", compound, element);
    }

    public static Object getList(Object element, String name, int type) {
        if (Reflection.version(">= 1.21.5")) {
            return ((Optional<?>) Reflection.wrapn("compound:element method_10554/getList String:name int:type", compound, element, name, type)).orElse(null);
        } else {
            return Reflection.wrap("compound:element method_10554/getList String:name int:type", compound, element, name, type);
        }
    }

    public static boolean contains(Object element, String name) {
        return (boolean) Reflection.wrapn("compound:element method_10545/contains String:name", compound, element, name);
    }

    public static short getShort(Object element, String name) {
        if (Reflection.version(">= 1.21.5")) {
            Object s = ((Optional<?>) Reflection.wrapn("compound:element method_10568/getShort String:name", compound, element, name)).orElse(null);
            if (s == null) return 0;
            return (short) s;
        } else {
            return (short) Reflection.wrapn("compound:element method_10568/getShort String:name", compound, element, name);
        }
    }

    public static Object get(Object element, int index) {
        return Reflection.wrap("list:element method_10534/get int:index", list, element, index);
    }
}

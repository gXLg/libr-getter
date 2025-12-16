package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.multiversion.C;
import dev.gxlg.librgetter.multiversion.V;

import java.util.Optional;
import java.util.Set;

public class Nbt {
    public static String getString(Object element, String name) {
        if (!V.lower("1.21.5")) {
            return (String) ((Optional<?>) C.NbtCompound.inst(element).mthd("method_10558/getString", String.class).invk(name)).orElse(null);
        } else {
            return (String) C.NbtCompound.inst(element).mthd("method_10558/getString", String.class).invk(name);
        }
    }

    public static Object getCompound(Object element, String name) {
        if (!V.lower("1.21.5")) {
            return ((Optional<?>) C.NbtCompound.inst(element).mthd("method_10562/getCompound", String.class).invk(name)).orElse(null);
        } else {
            return C.NbtCompound.inst(element).mthd("method_10562/getCompound", String.class).invk(name);
        }
    }

    public static Set<?> getKeys(Object element) {
        return (Set<?>) C.NbtCompound.inst(element).mthd("method_10541/getKeys").invk();
    }

    public static Object getList(Object element, String name, int type) {
        if (!V.lower("1.21.5")) {
            return ((Optional<?>) C.NbtCompound.inst(element).mthd("method_10554/getList", String.class, int.class).invk(name, type)).orElse(null);
        } else {
            return C.NbtCompound.inst(element).mthd("method_10554/getList", String.class, int.class).invk(name, type);
        }
    }

    public static boolean contains(Object element, String name) {
        return (boolean) C.NbtCompound.inst(element).mthd("method_10545/contains", String.class).invk(name);
    }

    public static short getShort(Object element, String name) {
        if (!V.lower("1.21.5")) {
            Object s = ((Optional<?>) C.NbtCompound.inst(element).mthd("method_10568/getShort", String.class).invk(name)).orElse(null);
            if (s == null) return 0;
            return (short) s;
        } else {
            return (short) C.NbtCompound.inst(element).mthd("method_10568/getShort", String.class).invk(name);
        }
    }

    public static Object get(Object element, int index) {
        return C.NbtList.inst(element).mthd("method_10534/get", int.class).invk(index);
    }
}

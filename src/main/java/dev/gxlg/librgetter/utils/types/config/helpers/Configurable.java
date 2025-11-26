package dev.gxlg.librgetter.utils.types.config.helpers;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.config.*;

import java.lang.reflect.Field;

public record Configurable<T>(String name, Class<T> type) {
    public T get() {
        try {
            Field f = Config.class.getField(name);
            T t = type.cast(f.get(LibrGetter.config));
            if (t != null) return t;
            return getDefault();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(T value) {
        try {
            Field f = Config.class.getField(name);
            f.set(LibrGetter.config, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T getDefault() {
        try {
            return type.cast(Config.class.getField(name).get(Config.DEFAULT));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public ArgumentType<?> argument() {
        Field f;
        try {
            f = Config.class.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        if (type == Boolean.class) {
            return BoolArgumentType.bool();

        } else if (type == Integer.class) {
            IntRange a = f.getDeclaredAnnotation(IntRange.class);
            if (a == null) return IntegerArgumentType.integer();
            return IntegerArgumentType.integer(a.min(), a.max());

        } else if (type == OptionsConfig.class) {
            return ((OptionsConfig<?>) get()).argumentType();

        } else throw new RuntimeException("This field does not support ArgumentTypes");
    }

    public boolean inRange(int i) {
        if (type != Integer.class)
            throw new RuntimeException("The field of type '" + type.getName() + "' does not support the inRange(int) method");
        Field f;
        try {
            f = Config.class.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        IntRange a = f.getDeclaredAnnotation(IntRange.class);
        if (a == null) return true;
        else return a.min() <= i && i <= a.max();
    }

    public boolean hasEffect() {
        Field f;
        try {
            f = Config.class.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        NoEffect a = f.getDeclaredAnnotation(NoEffect.class);
        if (a != null) {
            for (String ifTrue : a.ifTrue()) {
                Field i;
                try {
                    i = Config.class.getField(ifTrue);
                    if (i.getType() != boolean.class)
                        throw new RuntimeException("Effect dependency on a non-boolean field");
                    if (i.getBoolean(LibrGetter.config)) return false;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            for (String ifFalse : a.ifFalse()) {
                Field i;
                try {
                    i = Config.class.getField(ifFalse);
                    if (i.getType() != boolean.class)
                        throw new RuntimeException("Effect dependency on a non-boolean field");
                    if (!i.getBoolean(LibrGetter.config)) return false;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Compatibility c = f.getDeclaredAnnotation(Compatibility.class);
        if (c != null) return Support.isEffective(c.value());
        return true;
    }
}

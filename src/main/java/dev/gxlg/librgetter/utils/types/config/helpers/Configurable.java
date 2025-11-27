package dev.gxlg.librgetter.utils.types.config.helpers;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.utils.reflection.Support;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.OnlyEffective;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

import java.lang.reflect.Field;
import java.util.Arrays;

public record Configurable<T>(String name, Class<T> type, Config instance) {
    public T get() {
        try {
            Field f = Config.class.getField(name);
            T t = type.cast(f.get(instance));
            if (t != null) return t;
            return getDefault();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(T value) {
        try {
            Field f = Config.class.getField(name);
            f.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T getDefault() {
        return type.cast(Config.DEFAULT.getConfigurableForName(name).get());
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

        for (OnlyEffective oe : f.getAnnotationsByType(OnlyEffective.class)) {
            Configurable<?> conf = instance.getConfigurableForName(oe.when());
            String current;
            if (conf.type() == OptionsConfig.class) {
                current = ((OptionsConfig<?>) conf.get()).asString();
            } else {
                current = conf.get().toString();
            }
            if (!Arrays.asList(oe.equals()).contains(current)) return false;
        }

        Compatibility c = f.getDeclaredAnnotation(Compatibility.class);
        //noinspection RedundantIfStatement
        if (c != null && !Support.isEffective(c.value())) return false;

        // some more criteria later possibly...

        return true;
    }

    public boolean isDefault() {
        return get().equals(getDefault());
    }
}

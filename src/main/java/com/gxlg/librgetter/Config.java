package com.gxlg.librgetter;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("CanBeFinal")
public class Config {
    public boolean notify = false;
    public boolean autoTool = true;
    public boolean actionBar = false;
    public boolean lock = false;
    public boolean removeGoal = false;

    public boolean checkUpdate = true;
    public boolean warning = true;
    public boolean offhand = false;
    public boolean manual = false;
    public boolean waitLose = false;
    public boolean safeChecker = true;

    @IntRange(min = 0, max = 20)
    public int timeout = 0;

    public List<Enchantment> goals = new ArrayList<>();

    public static class Enchantment {
        public static final Enchantment EMPTY = new Enchantment("", -1, 0);
        final public String id;
        final public int lvl;
        public int price;

        public Enchantment(String id, int lvl, int price) {
            this.id = id;
            this.lvl = lvl;
            this.price = price;
        }

        public boolean meets(Enchantment e) {
            return e.id.equals(id) && e.lvl == lvl && e.price <= price;
        }

        public boolean same(Enchantment e) {
            return e.id.equals(id) && e.lvl == lvl;
        }

        @Override
        public String toString() {
            return id + " " + lvl;
        }
    }

    public record Configurable<T>(String name, Class<T> type) {
        public T get() {
            try {
                Field f = Config.class.getField(name);
                return type.cast(f.get(LibrGetter.config));
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
            } else throw new RuntimeException("This field does not support ArgumentTypes");
        }

        public boolean inRange(int i) {
            if (type != Integer.class) throw new RuntimeException("The field of type '" + type.getName() + "' does not support the inRange(int) method");
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
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface IntRange {
        int min() default Integer.MIN_VALUE;
        int max() default Integer.MAX_VALUE;
    }

    private static final List<Configurable<?>> configurable = new ArrayList<>();
    static {
        for (Field f : Config.class.getFields()) {
            if (f.getType() == boolean.class) configurable.add(new Configurable<>(f.getName(), Boolean.class));
            if (f.getType() == int.class) configurable.add(new Configurable<>(f.getName(), Integer.class));
        }
        configurable.sort(Comparator.comparing(Configurable::name));
    }

    public static List<Configurable<?>> getConfigurables() {
        return configurable;
    }
}
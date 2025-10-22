package dev.gxlg.librgetter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public boolean fallback = false;

    public boolean look = true;
    public boolean rotation = false;

    // compatibility configs
    public boolean _tradeCycling = false;

    @IntRange(min = 0, max = 20)
    public int timeout = 0;

    public List<Enchantment> goals = new ArrayList<>();

    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("librgetter.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Config init() {
        Config config;
        if (Files.notExists(configPath)) {
            try {
                Files.createFile(configPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize config", e);
            }
            config = new Config();
        } else {
            try (FileReader reader = new FileReader(configPath.toFile())) {
                config = GSON.fromJson(reader, Config.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }
        }
        config.save();
        return config;
    }

    public void save() {
        Path dir = configPath.getParent();
        try {
            if (Files.notExists(dir)) {
                Files.createDirectory(dir);
            } else if (!Files.isDirectory(dir)) {
                throw new IOException("Not a directory: " + dir);
            }

            Path tempPath = configPath.resolveSibling(configPath.getFileName() + ".tmp");
            Files.createFile(tempPath);
            Files.write(tempPath, GSON.toJson(this).getBytes(), StandardOpenOption.WRITE);
            Files.move(tempPath, configPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save config", e);
        }
    }

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
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface IntRange {
        int min() default Integer.MIN_VALUE;

        int max() default Integer.MAX_VALUE;
    }

    private static final List<Configurable<?>> configurable = new ArrayList<>();

    static {
        List<Configurable<?>> normal = new ArrayList<>();
        List<Configurable<?>> compat = new ArrayList<>();
        for (Field f : Config.class.getFields()) {
            List<Configurable<?>> candidate = f.getName().startsWith("_") ? compat : normal;
            if (f.getType() == boolean.class) candidate.add(new Configurable<>(f.getName(), Boolean.class));
            if (f.getType() == int.class) candidate.add(new Configurable<>(f.getName(), Integer.class));
        }
        normal.sort(Comparator.comparing(Configurable::name));
        compat.sort(Comparator.comparing(Configurable::name));
        configurable.addAll(normal);
        configurable.addAll(compat);
    }

    public static List<Configurable<?>> getConfigurables() {
        return configurable;
    }
}
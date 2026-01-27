package dev.gxlg.librgetter.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    public static final List<String> CATEGORIES;

    public static final ConfigManager DEFAULT;

    private final ConfigData data;

    private final List<Configurable<?>> configurable = new ArrayList<>();

    private final Map<String, Configurable<?>> configurableMap = new HashMap<>();

    private final Map<String, List<Configurable<?>>> categoryMap = new HashMap<>();

    private final Path configPath;

    private ConfigManager(ConfigData data, Path configPath) {
        this.data = data;
        this.configPath = configPath;

        for (Field f : ConfigData.class.getFields()) {
            Configurable<?> conf;
            if (f.getType() == boolean.class) {
                conf = new Configurable<>(f.getName(), Boolean.class, this);
            } else if (f.getType() == int.class) {
                conf = new Configurable<>(f.getName(), Integer.class, this);
            } else if (OptionsConfig.class.isAssignableFrom(f.getType())) {
                conf = new Configurable<>(f.getName(), OptionsConfig.class, this);
            } else {
                continue;
            }
            configurable.add(conf);
            configurableMap.put(f.getName(), conf);
            String key = categoriesMap.entrySet().stream().filter(e -> e.getValue().contains(f.getName())).findFirst().map(Map.Entry::getKey)
                                      .orElseThrow(() -> new RuntimeException("Uncategorized config '" + f.getName() + "'"));
            categoryMap.computeIfAbsent(key, k -> new ArrayList<>()).add(conf);
        }

        configurable.sort(Comparator.comparing(Configurable::name));
        categoryMap.forEach((key, value) -> value.sort(Comparator.comparing(c -> categoriesMap.get(key).indexOf(c.name()))));
    }

    public ConfigData getData() {
        return data;
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
            if (!Files.exists(tempPath)) {
                Files.createFile(tempPath);
            }

            Files.write(tempPath, GSON.toJson(this.data).getBytes(), StandardOpenOption.WRITE);
            Files.move(tempPath, configPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save config", e);
        }
    }

    public List<Configurable<?>> getConfigurables() {
        return configurable;
    }

    public List<Configurable<?>> getConfigurablesForCategory(String category) {
        return categoryMap.getOrDefault(category, List.of());
    }

    public Configurable<?> getConfigurableForName(String field) {
        return configurableMap.getOrDefault(field, null);
    }

    private static final Map<String, List<String>> categoriesMap;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static {
        CATEGORIES = List.of("process", "success", "messages", "matching", "compatibility");
        categoriesMap = Map.of(
            "process",
            List.of("autoTool", "offhand", "rotationMode", "manual", "waitLose", "safeChecker", "timeout"),
            "success",
            List.of("notify", "removeGoal", "lock"),
            "messages",
            List.of("logMode", "warning", "checkUpdate"),
            "matching",
            List.of("fallback", "matchMode", "matchAtLeast"),
            "compatibility",
            Arrays.stream(ConfigData.class.getFields()).filter(f -> f.getAnnotation(Compatibility.class) != null).map(Field::getName).sorted().toList()
        );

        DEFAULT = new DefaultConfig();
    }

    public static ConfigManager init(Path configPath) {
        ConfigData data;
        if (Files.notExists(configPath)) {
            try {
                Files.createFile(configPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize config", e);
            }
            data = new ConfigData();
        } else {
            try (FileReader reader = new FileReader(configPath.toFile())) {
                data = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }
        }
        ConfigManager config = new ConfigManager(data, configPath);
        config.save();
        return config;
    }

    private static class DefaultConfig extends ConfigManager {
        private DefaultConfig() {
            super(new ConfigData(), null);
        }

        @Override
        public void save() {
            // no saving for default config holder
        }
    }
}

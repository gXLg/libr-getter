package dev.gxlg.librgetter.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.utils.types.config.ConfigCategory;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotInitConfigMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotReadConfigMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.CouldNotSaveConfigMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.NoConfigFieldMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.error.UncategorizedConfigMessage;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    public static final ConfigManager DEFAULT = new DefaultConfig();

    private final ConfigData data;

    private final List<Configurable<?>> configurables = new ArrayList<>();

    private final Map<Config, Configurable<?>> configurableMap = new HashMap<>();

    private final Map<Category, List<Configurable<?>>> categoryMap = new HashMap<>();

    private final Path configPath;

    private final Notifier notifier;

    private ConfigManager(ConfigData data, Path configPath, Notifier notifier) {
        this.data = data;
        this.configPath = configPath;
        this.notifier = notifier;

        for (Config config : Config.values()) {
            Field field;
            try {
                field = ConfigData.class.getDeclaredField(config.getId());
            } catch (NoSuchFieldException e) {
                notifier.addNotification(new NoConfigFieldMessage(config));
                continue;
            }

            Configurable<?> configurable;
            if (field.getType() == boolean.class) {
                configurable = new Configurable<>(config, Boolean.class, field, this);
            } else if (field.getType() == int.class) {
                configurable = new Configurable<>(config, Integer.class, field, this);
            } else if (OptionsConfig.class.isAssignableFrom(field.getType())) {
                configurable = new Configurable<>(config, OptionsConfig.class, field, this);
            } else {
                continue;
            }

            ConfigCategory configCategory = field.getAnnotation(ConfigCategory.class);
            if (configCategory == null) {
                notifier.addNotification(new UncategorizedConfigMessage(config));
                continue;
            }

            configurables.add(configurable);
            configurableMap.put(config, configurable);

            Category category = configCategory.value();
            categoryMap.computeIfAbsent(category, k -> new ArrayList<>()).add(configurable);
        }
        configurables.sort(Comparator.comparing(c -> c.config().getId()));
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
                notifier.addNotification(new CouldNotSaveConfigMessage());
                return;
            }

            Path tempPath = configPath.resolveSibling(configPath.getFileName() + ".tmp");
            if (!Files.exists(tempPath)) {
                Files.createFile(tempPath);
            }

            Files.write(tempPath, GSON.toJson(this.data).getBytes(), StandardOpenOption.WRITE);
            Files.move(tempPath, configPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            notifier.addNotification(new CouldNotSaveConfigMessage());
        }
    }

    public List<Configurable<?>> getConfigurables() {
        return configurables;
    }

    public List<Configurable<?>> getConfigurablesForCategory(Category category) {
        return categoryMap.getOrDefault(category, List.of());
    }

    public Configurable<?> getConfigurable(Config config) {
        return configurableMap.getOrDefault(config, null);
    }

    public boolean getBoolean(Config config) {
        Configurable<?> configurable = getConfigurable(config);
        return (boolean) configurable.get();
    }

    public int getInteger(Config config) {
        Configurable<?> configurable = getConfigurable(config);
        return (int) configurable.get();
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T> & OptionsConfig<?>> T getOptions(Config config) {
        Configurable<?> configurable = getConfigurable(config);
        return (T) configurable.get();
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static ConfigManager init(Path configPath, Notifier notifier) {
        ConfigData data;
        if (Files.notExists(configPath)) {
            try {
                Files.createFile(configPath);
            } catch (IOException e) {
                notifier.addNotification(new CouldNotInitConfigMessage());
                return new DummyConfig();
            }
            data = new ConfigData();
        } else {
            try (FileReader reader = new FileReader(configPath.toFile())) {
                data = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                notifier.addNotification(new CouldNotReadConfigMessage());
                data = new ConfigData();
            }
        }
        ConfigManager config = new ConfigManager(data, configPath, notifier);
        config.save();
        return config;
    }

    public enum Category {
        PROCESS("process"),
        SUCCESS("success"),
        MESSAGES("messages"),
        MATCHING("matching"),
        COMPATIBILITY("compatibility");

        private final String id;

        Category(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private static class DefaultConfig extends ConfigManager {
        private DefaultConfig() {
            super(new ConfigData(), null, null);
        }

        @Override
        public void save() {
            // no saving for default config holder
        }
    }

    public static class DummyConfig extends ConfigManager {
        private DummyConfig() {
            super(new ConfigData(), null, null);
        }

        @Override
        public void save() {
            // no saving for dummy config holder
        }
    }
}

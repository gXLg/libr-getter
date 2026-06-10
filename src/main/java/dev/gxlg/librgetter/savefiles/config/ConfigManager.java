package dev.gxlg.librgetter.savefiles.config;

import dev.gxlg.librgetter.notifier.Notifier;
import dev.gxlg.librgetter.savefiles.JsonSaveFile;
import dev.gxlg.librgetter.savefiles.SaveFileManager;
import dev.gxlg.librgetter.savefiles.config.types.ConfigCategory;
import dev.gxlg.librgetter.savefiles.config.types.OptionsConfig;
import dev.gxlg.librgetter.savefiles.config.types.helpers.Configurable;
import dev.gxlg.librgetter.utils.messages.translatable.error.NoConfigFieldMessage;
import dev.gxlg.librgetter.utils.messages.translatable.error.UncategorizedConfigMessage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    public static final String FILENAME = "config.json";

    private final List<Configurable<?>> configurables = new ArrayList<>();

    private final Map<Config, Configurable<?>> configurableMap = new HashMap<>();

    private final Map<Category, List<Configurable<?>>> categoryMap = new HashMap<>();

    private final JsonSaveFile<ConfigData> saveFile;

    private ConfigManager(JsonSaveFile<ConfigData> saveFile, Notifier notifier) {
        this.saveFile = saveFile;
        ConfigData data = saveFile.getData();
        ConfigData defaultData = new ConfigData();

        for (Config config : Config.values()) {
            Field field;
            try {
                field = ConfigData.class.getDeclaredField(config.getId());
            } catch (NoSuchFieldException e) {
                notifier.addNotification(new NoConfigFieldMessage(config));
                continue;
            }
            field.setAccessible(true);

            Configurable<?> configurable;
            try {
                if (field.getType() == boolean.class) {
                    configurable = new Configurable<>(config, Boolean.class, field, this, data, field.getBoolean(defaultData));
                } else if (field.getType() == int.class) {
                    configurable = new Configurable<>(config, Integer.class, field, this, data, field.getInt(defaultData));
                } else if (OptionsConfig.class.isAssignableFrom(field.getType())) {
                    configurable = new Configurable<>(config, OptionsConfig.class, field, this, data, (OptionsConfig<?>) field.get(defaultData));
                } else {
                    continue;
                }
            } catch (IllegalAccessException e) {
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

    public void save() {
        saveFile.save();
    }

    public static ConfigManager init(SaveFileManager saveManager, Notifier notifier) {
        JsonSaveFile<ConfigData> saveFile = saveManager.createSaveFile(FILENAME, ConfigData.class, ConfigData::new);
        return new ConfigManager(saveFile, notifier);
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
}

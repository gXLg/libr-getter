package dev.gxlg.librgetter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.gxlg.librgetter.utils.types.Enchantment;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.OnlyEffective;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.enums.MatchMode;
import dev.gxlg.librgetter.utils.types.config.enums.RotationMode;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;

@SuppressWarnings("CanBeFinal")
public class Config {
    public boolean notify = false;

    @OnlyEffective(when = "manual", equals = "false")
    public boolean autoTool = true;

    public LogMode logMode = LogMode.CHAT;

    @OnlyEffective(when = "manual", equals = "false")
    public boolean lock = false;

    public boolean removeGoal = false;

    public boolean checkUpdate = true;

    public boolean warning = true;

    @OnlyEffective(when = "manual", equals = "false")
    public boolean offhand = false;

    public boolean manual = false;

    public boolean waitLose = false;

    public boolean safeChecker = true;

    public boolean fallback = false;

    @OnlyEffective(when = "manual", equals = "false")
    public RotationMode rotationMode = RotationMode.INSTANT;

    @IntRange(min = 0, max = 20)
    public int timeout = 0;

    @NotNull
    public MatchMode matchMode = MatchMode.VANILLA;

    @OnlyEffective(when = "matchMode", equals = "ATLEAST")
    @IntRange(min = 1)
    public int matchAtLeast = 1;

    @Compatibility("trade_cycling")
    public boolean tradeCycling = false;


    public List<Enchantment> goals = new ArrayList<>();

    private transient final List<Configurable<?>> configurable = new ArrayList<>();
    private transient final Map<String, Configurable<?>> configurableMap = new HashMap<>();
    private transient final Map<String, List<Configurable<?>>> categoryMap = new HashMap<>();

    public Config() {
        for (Field f : Config.class.getFields()) {
            Configurable<?> conf;
            if (f.getType() == boolean.class) conf = new Configurable<>(f.getName(), Boolean.class, this);
            else if (f.getType() == int.class) conf = new Configurable<>(f.getName(), Integer.class, this);
            else if (OptionsConfig.class.isAssignableFrom(f.getType()))
                conf = new Configurable<>(f.getName(), OptionsConfig.class, this);
            else continue;
            configurable.add(conf);
            configurableMap.put(f.getName(), conf);
            String key = categories.entrySet().stream().filter(e -> e.getValue().contains(f.getName())).findFirst().map(Map.Entry::getKey).orElseThrow(() -> new RuntimeException("Uncategorized config '" + f.getName() + "'"));
            categoryMap.computeIfAbsent(key, k -> new ArrayList<>()).add(conf);
        }
        configurable.sort(Comparator.comparing(Configurable::name));
        categoryMap.forEach((key, value) -> value.sort(Comparator.comparing(c -> categories.get(key).indexOf(c.name()))));
    }

    private static final Map<String, List<String>> categories = Map.of(
            "process", List.of("autoTool", "offhand", "rotationMode", "manual", "waitLose", "safeChecker", "timeout"),
            "success", List.of("notify", "removeGoal", "lock"),
            "messages", List.of("logMode", "warning", "checkUpdate"),
            "matching", List.of("fallback", "matchMode", "matchAtLeast"),
            "compatibility", Arrays.stream(Config.class.getFields()).filter(f -> f.getAnnotation(Compatibility.class) != null).map(Field::getName).sorted().toList()
    );
    public static final List<String> CATEGORIES = List.of("process", "success", "messages", "matching", "compatibility");

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
            if (!Files.exists(tempPath)) Files.createFile(tempPath);

            Files.write(tempPath, GSON.toJson(this).getBytes(), StandardOpenOption.WRITE);
            Files.move(tempPath, configPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not save config", e);
        }
    }

    public static final Config DEFAULT = new Config();

    public List<Configurable<?>> getConfigurables() {
        return configurable;
    }

    public List<Configurable<?>> getConfigurablesForCategory(String category) {
        return categoryMap.getOrDefault(category, List.of());
    }

    public Configurable<?> getConfigurableForName(String field) {
        return configurableMap.getOrDefault(field, null);
    }
}
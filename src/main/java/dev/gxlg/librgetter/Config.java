package dev.gxlg.librgetter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.gxlg.librgetter.utils.types.Enchantment;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.NoEffect;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("CanBeFinal")
public class Config {
    public boolean notify = false;

    @NoEffect(ifTrue = "manual")
    public boolean autoTool = true;

    public LogMode logMode = LogMode.CHAT;

    @NoEffect(ifTrue = "manual")
    public boolean lock = false;

    public boolean removeGoal = false;

    public boolean checkUpdate = true;

    public boolean warning = true;

    @NoEffect(ifTrue = "manual")
    public boolean offhand = false;

    public boolean manual = false;

    public boolean waitLose = false;

    public boolean safeChecker = true;

    public boolean fallback = false;

    @NoEffect(ifTrue = "manual")
    public RotationMode rotationMode = RotationMode.INSTANT;

    @IntRange(min = 0, max = 20)
    public int timeout = 0;

    public List<Enchantment> goals = new ArrayList<>();

    @NotNull
    public MatchMode matchMode = MatchMode.VANILLA;

    @Compatibility("trade_cycling")
    public boolean _tradeCycling = false;


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

    public static final Config DEFAULT = new Config();
    private static final List<Configurable<?>> configurable = new ArrayList<>();

    static {
        List<Configurable<?>> normal = new ArrayList<>();
        List<Configurable<?>> compat = new ArrayList<>();
        for (Field f : Config.class.getFields()) {
            List<Configurable<?>> candidate = f.getName().startsWith("_") ? compat : normal;
            if (f.getType() == boolean.class) candidate.add(new Configurable<>(f.getName(), Boolean.class));
            if (f.getType() == int.class) candidate.add(new Configurable<>(f.getName(), Integer.class));
            if (OptionsConfig.class.isAssignableFrom(f.getType()))
                candidate.add(new Configurable<>(f.getName(), OptionsConfig.class));
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
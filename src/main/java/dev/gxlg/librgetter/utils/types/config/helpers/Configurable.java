package dev.gxlg.librgetter.utils.types.config.helpers;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.gxlg.librgetter.utils.chaining.compatibility.Compatibility;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigData;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.CanNotChangeWhileRunning;
import dev.gxlg.librgetter.utils.types.config.CompatibilityWith;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.OnlyEffective;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class Configurable<T> {
    private final Config config;

    private final Class<T> type;

    private final ConfigManager managerInstance;

    private final Field field;

    public Configurable(Config config, Class<T> type, ConfigManager managerInstance) {
        this.config = config;
        this.type = type;
        this.managerInstance = managerInstance;
        try {
            Field configurableField = ConfigData.class.getDeclaredField(config.getId());
            configurableField.setAccessible(true);
            this.field = configurableField;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public T get() {
        try {
            T configurableType = type.cast(field.get(managerInstance.getData()));
            if (configurableType != null) {
                return configurableType;
            }
            return getDefault();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(T value) {
        try {
            field.set(managerInstance.getData(), value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T getDefault() {
        return type.cast(ConfigManager.DEFAULT.getConfigurable(config).get());
    }

    public ArgumentType<?> commandArgument() {
        if (type == Boolean.class) {
            return BoolArgumentType.bool();

        } else if (type == Integer.class) {
            IntRange rangeAnnotation = field.getDeclaredAnnotation(IntRange.class);
            if (rangeAnnotation == null) {
                return IntegerArgumentType.integer();
            }
            return IntegerArgumentType.integer(rangeAnnotation.min(), rangeAnnotation.max());

        } else if (type == OptionsConfig.class) {
            return ((OptionsConfig<?>) get()).argumentType();

        } else {
            throw new RuntimeException("This field does not support ArgumentTypes");
        }
    }

    public boolean inRange(int value) {
        if (type != Integer.class) {
            throw new RuntimeException("The field of type '" + type.getName() + "' does not support the inRange(int) method");
        }
        IntRange rangeAnnotation = field.getDeclaredAnnotation(IntRange.class);
        if (rangeAnnotation == null) {
            return true;
        } else {
            return rangeAnnotation.min() <= value && value <= rangeAnnotation.max();
        }
    }

    public boolean hasEffect() {
        for (OnlyEffective onlyEffectiveCondition : field.getAnnotationsByType(OnlyEffective.class)) {
            Configurable<?> configurable = managerInstance.getConfigurable(onlyEffectiveCondition.when());
            String current;
            if (configurable.type() == OptionsConfig.class) {
                current = ((OptionsConfig<?>) configurable.get()).getName();
            } else {
                current = configurable.get().toString();
            }
            if (!Arrays.asList(onlyEffectiveCondition.equals()).contains(current)) {
                return false;
            }
        }

        CompatibilityWith modCompatibilityCondition = field.getDeclaredAnnotation(CompatibilityWith.class);
        //noinspection RedundantIfStatement
        if (modCompatibilityCondition != null && !Compatibility.isModPresent(modCompatibilityCondition.value())) {
            return false;
        }

        // some more criteria later possibly...

        return true;
    }

    public boolean isDefault() {
        return get().equals(getDefault());
    }

    public boolean isCompatibility() {
        CompatibilityWith modCompatibilityCondition = field.getDeclaredAnnotation(CompatibilityWith.class);
        return modCompatibilityCondition != null;
    }

    public boolean canChangeWhileRunning() {
        CanNotChangeWhileRunning cantChangeWhileRunning = field.getDeclaredAnnotation(CanNotChangeWhileRunning.class);
        return cantChangeWhileRunning == null;
    }

    public Config config() {
        return config;
    }

    public Class<T> type() {
        return type;
    }
}

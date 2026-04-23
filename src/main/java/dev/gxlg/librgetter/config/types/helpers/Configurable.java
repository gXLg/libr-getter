package dev.gxlg.librgetter.config.types.helpers;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.gxlg.librgetter.config.Config;
import dev.gxlg.librgetter.config.ConfigManager;
import dev.gxlg.librgetter.config.types.CanNotChangeWhileRunning;
import dev.gxlg.librgetter.config.types.CompatibilityWith;
import dev.gxlg.librgetter.config.types.IntRange;
import dev.gxlg.librgetter.config.types.OnlyEffective;
import dev.gxlg.librgetter.config.types.OptionsConfig;
import dev.gxlg.librgetter.utils.chaining.compatibility.Compatibility;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class Configurable<T> {
    private final Config config;

    private final Class<T> type;

    private final ConfigManager managerInstance;

    private final Object data;

    private final Field field;

    private final T defaultValue;

    public Configurable(Config config, Class<T> type, Field field, ConfigManager managerInstance, Object data, T defaultValue) {
        this.config = config;
        this.type = type;
        this.managerInstance = managerInstance;
        this.data = data;
        this.field = field;
        this.defaultValue = defaultValue;
    }

    public T get() {
        try {
            T configurableType = type.cast(field.get(data));
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
            field.set(data, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T getDefault() {
        return defaultValue;
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
            if (!configurable.hasEffect()) {
                continue;
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

package dev.gxlg.librgetter.utils.types.config.helpers;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.gxlg.librgetter.utils.chaining.support.Support;
import dev.gxlg.librgetter.utils.config.ConfigData;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.Compatibility;
import dev.gxlg.librgetter.utils.types.config.IntRange;
import dev.gxlg.librgetter.utils.types.config.OnlyEffective;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;

import java.lang.reflect.Field;
import java.util.Arrays;

public record Configurable<T>(String name, Class<T> type, ConfigManager managerInstance) {
    public T get() {
        try {
            Field configurableField = ConfigData.class.getField(name);
            T configurableType = type.cast(configurableField.get(managerInstance.getData()));
            if (configurableType != null) {
                return configurableType;
            }
            return getDefault();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(T value) {
        try {
            Field configurableField = ConfigData.class.getField(name);
            configurableField.set(managerInstance.getData(), value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T getDefault() {
        return type.cast(ConfigManager.DEFAULT.getConfigurableForName(name).get());
    }

    public ArgumentType<?> commandArgument() {
        Field configurableField;
        try {
            configurableField = ConfigData.class.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        if (type == Boolean.class) {
            return BoolArgumentType.bool();

        } else if (type == Integer.class) {
            IntRange rangeAnnotation = configurableField.getDeclaredAnnotation(IntRange.class);
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
        Field configurableField;
        try {
            configurableField = ConfigData.class.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        IntRange rangeAnnotation = configurableField.getDeclaredAnnotation(IntRange.class);
        if (rangeAnnotation == null) {
            return true;
        } else {
            return rangeAnnotation.min() <= value && value <= rangeAnnotation.max();
        }
    }

    public boolean hasEffect() {
        Field configurableField;
        try {
            configurableField = ConfigData.class.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        for (OnlyEffective onlyEffectiveCondition : configurableField.getAnnotationsByType(OnlyEffective.class)) {
            Configurable<?> configurable = managerInstance.getConfigurableForName(onlyEffectiveCondition.when());
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

        Compatibility modCompatibilityCondition = configurableField.getDeclaredAnnotation(Compatibility.class);
        //noinspection RedundantIfStatement
        if (modCompatibilityCondition != null && !Support.getImpl().isModPresent(modCompatibilityCondition.value())) {
            return false;
        }

        // some more criteria later possibly...

        return true;
    }

    public boolean isDefault() {
        return get().equals(getDefault());
    }
}

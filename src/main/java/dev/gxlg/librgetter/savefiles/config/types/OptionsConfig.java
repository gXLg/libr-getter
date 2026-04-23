package dev.gxlg.librgetter.savefiles.config.types;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.librgetter.savefiles.config.types.helpers.EnumArgumentType;

public interface OptionsConfig<T extends Enum<T> & OptionsConfig<T>> {
    T[] getValues();

    default T next() {
        T[] c = getValues();
        return c[(((Enum<?>) this).ordinal() + 1) % c.length];
    }

    default String getName() {
        return ((Enum<?>) this).name();
    }

    default ArgumentType<T> argumentType() {
        return EnumArgumentType.of(this.getValues());
    }
}

package dev.gxlg.librgetter.utils.types.config;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.librgetter.utils.types.config.helpers.EnumArgumentTypeImpl;
import net.minecraft.util.StringIdentifiable;

public interface OptionsConfig<T extends Enum<T> & StringIdentifiable> extends StringIdentifiable {
    T[] getValues();
    @Override
    default String asString() {
        return ((Enum<?>) this).name();
    }

    default T next() {
        T[] c = getValues();
        return c[(((Enum<?>) this).ordinal() + 1) % c.length];
    }

    default ArgumentType<T> argumentType() {
        return EnumArgumentTypeImpl.of(this::getValues);
    }
}

package dev.gxlg.librgetter.utils.types.config;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.librgetter.utils.types.config.helpers.EnumArgumentType;
import net.minecraft.util.StringRepresentable;

public interface OptionsConfig<T extends Enum<T> & StringRepresentable> extends StringRepresentable {
    T[] getValues();

    @Override
    default String getSerializedName() {
        return ((Enum<?>) this).name();
    }

    default T next() {
        T[] c = getValues();
        return c[(((Enum<?>) this).ordinal() + 1) % c.length];
    }

    default ArgumentType<T> argumentType() {
        return EnumArgumentType.of(this::getValues);
    }
}

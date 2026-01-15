package dev.gxlg.librgetter.utils.reflection.chaining.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.multiversion.gen.net.minecraft.commands.CommandBuildContextWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgument$ResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgumentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.RegistriesWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Commands_1_21_0 extends Commands_1_19_3 {
    @Override
    protected @NotNull Optional<?> fromArgument(ResourceOrTagArgument$ResultWrapper argument) {
        return (Optional<?>) argument.cast(RegistriesWrapper.ENCHANTMENT());
    }

    @Override
    protected ArgumentType<?> getEnchantmentArgumentType(CommandBuildContextWrapper context) {
        return ResourceOrTagArgumentWrapper.resourceOrTag(context, RegistriesWrapper.ENCHANTMENT());
    }
}

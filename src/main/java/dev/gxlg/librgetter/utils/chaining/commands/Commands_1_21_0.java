package dev.gxlg.librgetter.utils.chaining.commands;

import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.java.lang.Object;
import dev.gxlg.versiont.gen.net.minecraft.commands.CommandBuildContext;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.ResourceOrTagArgument;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.ResourceOrTagArgument$Result;
import dev.gxlg.versiont.gen.net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Commands_1_21_0 extends Commands_1_19_3 {
    @Override
    protected @NotNull Optional<Object> fromArgument(ResourceOrTagArgument$Result argument) {
        return argument.cast(Registries.ENCHANTMENT());
    }

    @Override
    protected ArgumentType getEnchantmentArgumentType(CommandBuildContext context) {
        return ResourceOrTagArgument.resourceOrTag(context, Registries.ENCHANTMENT());
    }
}

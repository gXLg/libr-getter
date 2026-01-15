package dev.gxlg.librgetter.utils.reflection.chaining.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import dev.gxlg.librgetter.utils.types.exceptions.commands.CommandException;
import dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.ResourceOrTagArgument$ResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.Holder$ReferenceWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.HolderSet$NamedWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Commands_1_19_3 extends Commands_1_19_0 {
    @Override
    public List<Enchantment> getEnchantmentsFromCommandContext(CommandContext<?> context) throws CommandException {
        ResourceOrTagArgument$ResultWrapper argument = ResourceOrTagArgument$ResultWrapper.inst(context.getArgument("enchantment", ResourceOrTagArgument$ResultWrapper.clazz.self()));

        Optional<?> optionalResult = fromArgument(argument);
        if (optionalResult.isEmpty()) {
            throw new CommandException("librgetter.argument");
        }

        Either<?, ?> entry = (Either<?, ?>) ResourceOrTagArgument$ResultWrapper.inst(optionalResult.get()).unwrap();
        Optional<?> optEnchantment = entry.left();
        Optional<?> optTag = entry.right();

        if (optEnchantment.isEmpty()) {
            if (optTag.isEmpty()) {
                throw new CommandException("librgetter.wrong");
            }
            Stream<?> stream = (Stream<?>) HolderSet$NamedWrapper.inst(optTag.get()).stream();
            return stream.map(ref -> (Enchantment) Holder$ReferenceWrapper.inst(ref).value()).toList();
        } else {
            return List.of((Enchantment) Holder$ReferenceWrapper.inst(optEnchantment.get()).value());
        }
    }

    protected @NotNull Optional<?> fromArgument(ResourceOrTagArgument$ResultWrapper argument) {
        return (Optional<?>) argument.cast(BuiltInRegistriesWrapper.ENCHANTMENT().key());
    }
}

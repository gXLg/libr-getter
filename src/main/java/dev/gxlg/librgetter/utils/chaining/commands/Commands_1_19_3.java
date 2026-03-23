package dev.gxlg.librgetter.utils.chaining.commands;

import com.mojang.datafixers.util.Either;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.ArgumentNotSupportedException;
import dev.gxlg.librgetter.utils.types.exceptions.commands.WrongEnchantmentException;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.java.lang.Object;
import dev.gxlg.versiont.gen.net.minecraft.commands.arguments.ResourceOrTagArgument$Result;
import dev.gxlg.versiont.gen.net.minecraft.core.Holder;
import dev.gxlg.versiont.gen.net.minecraft.core.Holder$Reference;
import dev.gxlg.versiont.gen.net.minecraft.core.HolderSet$Named;
import dev.gxlg.versiont.gen.net.minecraft.core.registries.BuiltInRegistries;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Commands_1_19_3 extends Commands_1_19_0 {
    @Override
    public List<Enchantment> getEnchantmentsFromCommandContext(CommandContext context) throws LibrGetterException {
        ResourceOrTagArgument$Result argument = (ResourceOrTagArgument$Result) context.getArgument("enchantment", ResourceOrTagArgument$Result.clazz);

        Optional<?> optionalResult = fromArgument(argument);
        if (optionalResult.isEmpty()) {
            throw new ArgumentNotSupportedException();
        }

        Either<Object, Object> entry = ((ResourceOrTagArgument$Result) optionalResult.get()).unwrap2();
        Optional<Object> optEnchantment = entry.left();
        Optional<Object> optTag = entry.right();

        if (optEnchantment.isEmpty()) {
            if (optTag.isEmpty()) {
                throw new WrongEnchantmentException();
            }
            Stream<Holder> stream = ((HolderSet$Named) optTag.get()).stream();
            return stream.map(ref -> ((Enchantment) ((Holder$Reference) ref).value())).toList();
        } else {
            return List.of(((Enchantment) ((Holder$Reference) optEnchantment.get()).value()));
        }
    }

    protected @NotNull Optional<Object> fromArgument(ResourceOrTagArgument$Result argument) {
        return argument.cast(BuiltInRegistries.ENCHANTMENT().key());
    }
}

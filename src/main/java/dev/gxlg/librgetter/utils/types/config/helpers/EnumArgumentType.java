package dev.gxlg.librgetter.utils.types.config.helpers;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// Code adapted from Minecraft Java 1.21.10
public class EnumArgumentType<T extends Enum<T> & StringRepresentable> implements ArgumentType<T> {
    private final Supplier<T[]> valuesSupplier;

    protected EnumArgumentType(Supplier<T[]> valuesSupplier) {
        this.valuesSupplier = valuesSupplier;
    }

    public T parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        return Arrays.stream(valuesSupplier.get()).filter(t -> t.name().equals(string)).findFirst().orElseThrow(() -> INVALID_ENUM_EXCEPTION.createWithContext(stringReader, string));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(Arrays.stream(this.valuesSupplier.get()).map(StringRepresentable::getSerializedName).collect(Collectors.toList()), builder);
    }

    public Collection<String> getExamples() {
        return Arrays.stream(this.valuesSupplier.get()).map(StringRepresentable::getSerializedName).limit(2L).collect(Collectors.toList());
    }

    private static final DynamicCommandExceptionType INVALID_ENUM_EXCEPTION = new DynamicCommandExceptionType((value) -> Component.nullToEmpty("Invalid enum value"));

    public static <S extends Enum<S> & StringRepresentable> EnumArgumentType<S> of(Supplier<S[]> valuesSupplier) {
        return new EnumArgumentType<>(valuesSupplier);
    }
}
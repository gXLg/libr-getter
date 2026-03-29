package dev.gxlg.librgetter.utils.types.config.helpers;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.versiont.gen.net.minecraft.commands.SharedSuggestionProvider;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// Code adapted from Minecraft Java 1.21.10
public class EnumArgumentType<T extends Enum<T> & OptionsConfig<T>> implements ArgumentType<T> {
    private final T[] values;

    protected EnumArgumentType(T[] values) {
        this.values = values;
    }

    @Override
    public T parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        return Arrays.stream(values).filter(t -> t.name().equals(string)).findFirst().orElseThrow(() -> INVALID_ENUM_EXCEPTION.createWithContext(stringReader, string));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String input = builder.getRemaining().toLowerCase(Locale.ROOT);
        for (T option : this.values) {
            String optionName = option.getName();
            if (SharedSuggestionProvider.matchesSubStr(input, optionName.toLowerCase(Locale.ROOT))) {
                builder.suggest(optionName);
            }
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return Arrays.stream(this.values).map(Enum::name).limit(2).collect(Collectors.toList());
    }

    private static final DynamicCommandExceptionType INVALID_ENUM_EXCEPTION = new DynamicCommandExceptionType(value -> Component.nullToEmpty("Invalid enum value").unwrap(Message.class));

    public static <S extends Enum<S> & OptionsConfig<S>> EnumArgumentType<S> of(S[] values) {
        return new EnumArgumentType<>(values);
    }
}
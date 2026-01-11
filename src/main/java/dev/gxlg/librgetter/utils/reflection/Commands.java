package dev.gxlg.librgetter.utils.reflection;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.command.LibrGetCommand;
import dev.gxlg.librgetter.multiversion.C;
import dev.gxlg.librgetter.multiversion.R;
import dev.gxlg.librgetter.multiversion.V;
import dev.gxlg.librgetter.utils.reflection.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import net.minecraft.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Commands {
    public static boolean getEnchantments(List<Either<Enchantment, String>> list, CommandContext<?> context) {
        if (!V.lower("1.19.3")) {
            R.RClass pred = R.clz("net.minecraft.class_7737$class_7741/net.minecraft.command.argument.RegistryEntryPredicateArgumentType$EntryPredicate");
            Object argument;
            try {
                argument = context.getArgument("enchantment", pred.self());
            } catch (IllegalArgumentException ignored) {
                list.add(Either.right(context.getArgument("enchantment_custom", String.class)));
                return true;
            }

            Optional<?> opt = fromArgument(pred.inst(argument));
            if (opt.isEmpty()) {
                Texts.getImpl().sendTranslatableError("librgetter.argument");
                return false;
            }


            Object predicate = opt.get();
            Either<?, ?> entry = (Either<?, ?>) pred.inst(predicate).mthd("method_45647/getEntry").invk();
            Optional<?> optrefl = entry.left();
            Optional<?> optrefr = entry.right();

            R.RClass entryClass = R.clz("net.minecraft.class_6880$class_6883/net.minecraft.registry.entry.RegistryEntry$Reference");
            if (optrefl.isEmpty()) {
                if (optrefr.isEmpty()) {
                    Texts.getImpl().sendTranslatableError("librgetter.wrong");
                    return false;
                }

                Stream<?> stream = (Stream<?>) R.clz("net.minecraft.class_6885$class_6888/net.minecraft.registry.entry.RegistryEntryList$Named").inst(optrefr.get()).mthd("method_40239/stream").invk();
                stream.forEach(ref -> list.add(Either.left((Enchantment) entryClass.inst(ref).mthd("comp_349/value").invk())));
            } else {
                Enchantment enchantment = (Enchantment) entryClass.inst(optrefl.get()).mthd("comp_349/value").invk();
                list.add(Either.left(enchantment));
            }
        } else {
            try {
                Enchantment enchantment = context.getArgument("enchantment", Enchantment.class);
                list.add(Either.left(enchantment));
            } catch (IllegalArgumentException ignored) {
                list.add(Either.right(context.getArgument("enchantment_custom", String.class)));
            }
        }
        return true;
    }

    public static void registerCommands() {
        if (!V.lower("1.19")) {
            R.RClass registrationCallback = R.clz("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");
            Object listener = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(), new Class[]{ registrationCallback.self() }, (proxy, method, args) -> {
                    if (method.getName().equals("register")) {
                        Object registryAccess = args[1];
                        registerCommand((CommandDispatcher<?>) args[0], registryAccess);
                        return null;
                    }
                    return method.invoke(proxy, args);
                }
            );
            R.clz("net.fabricmc.fabric.api.event.Event").inst(registrationCallback.fld("EVENT").get()).mthd("register", Object.class).invk(listener);
        } else {
            R.RClass clientCommandManager = R.clz("net.fabricmc.fabric.api.client.command.v1.ClientCommandManager");
            registerCommand((CommandDispatcher<?>) clientCommandManager.fld("DISPATCHER").get(), null);
        }
    }

    private static void registerCommand(CommandDispatcher<?> dispatcher, Object registryAccess) {
        Object baseCommand = literal("librget");

        Object subCommand;

        // TODO: chained commands instead of re-assigned

        // add subcommand
        {
            Object addRunner = runner(LibrGetCommand::add);
            subCommand = literal("add");
            Object enchantmentArgument, levelArgument, priceArgument;

            enchantmentArgument = executes(argument("enchantment", enchantmentArgument(registryAccess)), addRunner);
            levelArgument = executes(argument("level", IntegerArgumentType.integer(1)), addRunner);
            priceArgument = executes(argument("maxprice", IntegerArgumentType.integer(1, 64)), addRunner);
            subCommand = then(subCommand, then(enchantmentArgument, then(levelArgument, priceArgument)));

            enchantmentArgument = argument("enchantment_custom", enchantmentArgument(registryAccess));
            levelArgument = executes(argument("level", IntegerArgumentType.integer(1)), addRunner);
            priceArgument = executes(argument("maxprice", IntegerArgumentType.integer(1, 64)), addRunner);
            subCommand = then(subCommand, then(enchantmentArgument, then(levelArgument, priceArgument)));

            baseCommand = then(baseCommand, subCommand);
        }

        // remove subcommand
        {
            Object removeRunner = runner(LibrGetCommand::remove);
            subCommand = literal("remove");
            Object enchantmentArgument, levelArgument;

            enchantmentArgument = executes(argument("enchantment", enchantmentArgument(registryAccess)), removeRunner);
            levelArgument = executes(argument("level", IntegerArgumentType.integer(1)), removeRunner);
            subCommand = then(subCommand, then(enchantmentArgument, levelArgument));

            enchantmentArgument = argument("enchantment_custom", enchantmentArgument(registryAccess));
            levelArgument = executes(argument("level", IntegerArgumentType.integer(1)), removeRunner);
            subCommand = then(subCommand, then(enchantmentArgument, levelArgument));

            baseCommand = then(baseCommand, subCommand);
        }

        // no-arg subcommands
        {
            subCommand = literal("clear").executes(ctx -> LibrGetCommand.clearGoals());
            baseCommand = then(baseCommand, subCommand);

            subCommand = literal("list").executes(ctx -> LibrGetCommand.list());
            baseCommand = then(baseCommand, subCommand);

            subCommand = literal("stop").executes(ctx -> LibrGetCommand.stopWorking());
            baseCommand = then(baseCommand, subCommand);

            subCommand = literal("start").executes(ctx -> LibrGetCommand.startWorking());
            baseCommand = then(baseCommand, subCommand);

            subCommand = literal("continue").executes(ctx -> LibrGetCommand.continueWorking());
            baseCommand = then(baseCommand, subCommand);

            subCommand = literal("auto").executes(ctx -> LibrGetCommand.autostart());
            baseCommand = then(baseCommand, subCommand);
        }

        // automatically create config commands for each simply configurable value in Config
        {
            subCommand = literal("config");
            for (Configurable<?> configurable : LibrGetter.config.getConfigurables()) {
                String name = configurable.name();
                Object configRunner = runner(LibrGetCommand::config, configurable);

                Object configArgument = executes(literal(name), configRunner);
                Object valueArgument = executes(argument("value", configurable.commandArgument()), configRunner);

                subCommand = then(subCommand, then(configArgument, valueArgument));
            }
            baseCommand = then(baseCommand, subCommand);
        }

        // selector
        {
            Object selectRunner = runner(ctx -> LibrGetCommand.selector());
            baseCommand = executes(baseCommand, selectRunner);
        }

        R.clz(CommandDispatcher.class).inst(dispatcher).mthd("register", LiteralArgumentBuilder.class).invk(baseCommand);
    }

    private static @NotNull Optional<?> fromArgument(R.RInstance argument) {
        Object key;
        if (!V.lower("1.21")) {
            key = C.RegistryKeys.fld("field_41265/ENCHANTMENT").get();
        } else {
            key = C.Registry.inst(C.Registries.fld("field_41176/ENCHANTMENT").get()).mthd("method_30517/getKey").invk();
        }

        R.RClass keyCls = C.RegistryKey;
        return (Optional<?>) argument.mthd("method_45648/tryCast", keyCls).invk(key);
    }

    private static ArgumentType<?> enchantmentArgument(Object registryAccess) {
        if (!V.lower("1.19.3")) {
            Object key;
            if (!V.lower("1.21")) {
                key = C.RegistryKeys.fld("field_41265/ENCHANTMENT").get();
            } else {
                key = C.Registry.inst(C.Registries.fld("field_41176/ENCHANTMENT").get()).mthd("method_30517/getKey").invk();
            }

            return (ArgumentType<?>) R.clz("net.minecraft.class_7737/net.minecraft.command.argument.RegistryEntryPredicateArgumentType")
                                      .mthd("method_45637/registryEntryPredicate", R.clz("net.minecraft.class_7157/net.minecraft.command.CommandRegistryAccess"), C.RegistryKey)
                                      .invk(registryAccess, key);
        } else {
            return (ArgumentType<?>) R.clz("net.minecraft.class_2194/net.minecraft.command.argument.EnchantmentArgumentType/net.minecraft.command.argument.ItemEnchantmentArgumentType")
                                      .mthd("method_9336/enchantment/itemEnchantment").invk();
        }
    }

    private static ArgumentBuilder<?, ?> literal(String command) {
        return (ArgumentBuilder<?, ?>) getClientCommandManager().mthd("literal", String.class).invk(command);
    }

    private static ArgumentBuilder<?, ?> argument(String command, ArgumentType<?> argumentType) {
        return (ArgumentBuilder<?, ?>) getClientCommandManager().mthd("argument", String.class, ArgumentType.class).invk(command, argumentType);
    }

    private static ArgumentBuilder<?, ?> executes(Object builder, Object cmd) {
        return (ArgumentBuilder<?, ?>) R.clz(ArgumentBuilder.class).inst(builder).mthd("executes", Command.class).invk(cmd);
    }

    private static ArgumentBuilder<?, ?> then(Object builder, Object builder2) {
        return (ArgumentBuilder<?, ?>) R.clz(ArgumentBuilder.class).inst(builder).mthd("then", ArgumentBuilder.class).invk(builder2);
    }


    private static <T> Command<T> runner(Function<CommandContext<T>, Integer> function) {
        return function::apply;
    }

    private static <T, U> Command<T> runner(BiFunction<CommandContext<T>, Configurable<U>, Integer> function, Configurable<U> config) {
        return t -> function.apply(t, config);
    }

    private static R.RClass getClientCommandManager() {
        if (!V.lower("1.19")) {
            return R.clz("net.fabricmc.fabric.api.client.command.v2.ClientCommandManager");
        } else {
            return R.clz("net.fabricmc.fabric.api.client.command.v1.ClientCommandManager");
        }
    }
}

package dev.gxlg.librgetter.utils.reflection;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import dev.gxlg.librgetter.Config;
import dev.gxlg.librgetter.Reflection;
import dev.gxlg.librgetter.command.LibrGetCommand;
import net.minecraft.enchantment.Enchantment;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Commands {
    public static boolean getEnchantments(List<Either<Enchantment, String>> list, CommandContext<?> context) {
        if (Reflection.version(">= 1.19.3")) {
            Class<?> pred = (Class<?>) Reflection.wrapn(".class_7737$class_7741/.command.argument.RegistryEntryPredicateArgumentType$EntryPredicate");
            Object argument;
            try {
                argument = pred.cast(context.getArgument("enchantment", pred));
            } catch (IllegalArgumentException ignored) {
                list.add(Either.right(context.getArgument("enchantment_custom", String.class)));
                return true;
            }

            Object key;
            if (Reflection.version(">= 1.21")) {
                key = Reflection.wrap("[.class_7924/.registry.RegistryKeys] field_41265/ENCHANTMENT");
            } else {
                key = Reflection.wrap("[.class_2378/.registry.Registry]:[[.class_7923/.registry.Registries] field_41176/ENCHANTMENT] method_30517/getKey");
            }
            Optional<?> opt = (Optional<?>) Reflection.wrapn("pred:argument method_45648/tryCast [.class_5321/.registry.RegistryKey]:key", argument, key);

            if (opt.isEmpty()) {
                Texts.sendError(context, "librgetter.argument");
                return false;
            }

            Object predicate = opt.get();
            Either<?, ?> entry = (Either<?, ?>) Reflection.wrapn("pred:predicate method_45647/getEntry", predicate);
            Optional<?> optrefl = entry.left();
            Optional<?> optrefr = entry.right();

            Class<?> entryClass = (Class<?>) Reflection.wrap(".class_6880$class_6883/.registry.entry.RegistryEntry$Reference");
            if (optrefl.isEmpty()) {
                if (optrefr.isEmpty()) {
                    Texts.sendError(context, "librgetter.wrong");
                    return false;
                }
                Stream<?> stream = (Stream<?>) Reflection.wrapn("[.class_6885$class_6888/.registry.entry.RegistryEntryList$Named]:optrefr.get() method_40239/stream");
                stream.forEach(ref -> list.add(Either.left((Enchantment) Reflection.wrap("entryClass:ref comp_349/value", entryClass))));
            } else {
                Enchantment enchantment = (Enchantment) Reflection.wrap("entryClass:optrefl.get() comp_349/value", entryClass);
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

    private static ArgumentType<?> enchantmentArgument(Object registryAccess) {
        if (Reflection.version(">= 1.19.3")) {
            Object key;
            if (Reflection.version(">= 1.21")) {
                key = Reflection.wrap("[.class_7924/.registry.RegistryKeys] field_41265/ENCHANTMENT");
            } else {
                key = Reflection.wrap("[.class_2378/.registry.Registry]:[[.class_7923/.registry.Registries] field_41176/ENCHANTMENT] method_30517/getKey");
            }
            return (ArgumentType<?>) Reflection.wrap("[.class_7737/.command.argument.RegistryEntryPredicateArgumentType] method_45637/registryEntryPredicate [.class_7157/.command.CommandRegistryAccess]:registryAccess [.class_5321/.registry.RegistryKey]:key", key, registryAccess);
        } else {
            return (ArgumentType<?>) Reflection.wrap("[.class_2194/.command.argument.EnchantmentArgumentType/.command.argument.ItemEnchantmentArgumentType] method_9336/enchantment/itemEnchantment");
        }
    }

    public static void registerCommand() {
        if (Reflection.version(">= 1.19")) {
            Class<?> cb = (Class<?>) Reflection.wrap("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");
            Class<?> cra = (Class<?>) Reflection.wrapn(".class_7157/.command.CommandRegistryAccess");
            Class<?> ccm = (Class<?>) Reflection.wrap("net.fabricmc.fabric.api.client.command.v2.ClientCommandManager");

            Object listener = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cb}, (proxy, method, args) -> {
                if (method.getName().equals("register")) {
                    Object registryAccess = cra.cast(args[1]);
                    command(ccm, (CommandDispatcher<?>) args[0], registryAccess);
                    return null;
                }
                return method.invoke(proxy, args);
            });
            Reflection.wrapi("net.fabricmc.fabric.api.event.Event:[cb EVENT§f] register§m Object:listener", listener);
        } else {
            Class<?> ccm = (Class<?>) Reflection.wrap("net.fabricmc.fabric.api.client.command.v1.ClientCommandManager");
            command(ccm, (CommandDispatcher<?>) Reflection.wrap("ccm DISPATCHER§f"), null);
        }
    }

    private static ArgumentBuilder<?, ?> literal(Class<?> ccm, String command) {
        return (ArgumentBuilder<?, ?>) Reflection.wrap("ccm literal§m command", ccm, command);
    }

    private static ArgumentBuilder<?, ?> argument(Class<?> ccm, String command, ArgumentType<?> argumentType) {
        return (ArgumentBuilder<?, ?>) Reflection.wrap("ccm argument§m String:command ArgumentType:argumentType", ccm, command, argumentType);
    }

    private static <T> Command<T> runner(Function<CommandContext<T>, Integer> function) {
        return function::apply;
    }

    private static <T, U> Command<T> runner(BiFunction<CommandContext<T>, Config.Configurable<U>, Integer> function, Config.Configurable<U> config) {
        return t -> function.apply(t, config);
    }

    public static void command(Class<?> ccm, CommandDispatcher<?> dispatcher, Object registryAccess) {
        Object base = literal(ccm, "librget");
        Object a, l, r, d;

        {
            Object add = runner(LibrGetCommand::add);
            l = literal(ccm, "add");

            a = argument(ccm, "enchantment", enchantmentArgument(registryAccess));
            a = Reflection.wrap("ArgumentBuilder:a executes§m Command:add", a, add);

            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.wrap("ArgumentBuilder:r executes§m Command:add", r, add);

            d = argument(ccm, "maxprice", IntegerArgumentType.integer(1, 64));
            d = Reflection.wrap("ArgumentBuilder:d executes§m Command:add", d, add);

            r = Reflection.wrap("ArgumentBuilder:r then§m ArgumentBuilder:d", r, d);
            a = Reflection.wrap("ArgumentBuilder:a then§m ArgumentBuilder:r", a, r);
            l = Reflection.wrap("ArgumentBuilder:l then§m ArgumentBuilder:a", l, a);

            a = argument(ccm, "enchantment_custom", StringArgumentType.string());

            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.wrap("ArgumentBuilder:r executes§m Command:add", r, add);

            d = argument(ccm, "maxprice", IntegerArgumentType.integer(1, 64));
            d = Reflection.wrap("ArgumentBuilder:d executes§m Command:add", d, add);

            r = Reflection.wrap("ArgumentBuilder:r then§m ArgumentBuilder:d", r, d);
            a = Reflection.wrap("ArgumentBuilder:a then§m ArgumentBuilder:r", a, r);
            l = Reflection.wrap("ArgumentBuilder:l then§m ArgumentBuilder:a", l, a);

            base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);
        }
        {
            Object remove = runner(LibrGetCommand::remove);
            l = literal(ccm, "remove");

            a = argument(ccm, "enchantment", enchantmentArgument(registryAccess));

            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.wrap("ArgumentBuilder:r executes§m Command:remove", r, remove);

            a = Reflection.wrap("ArgumentBuilder:a then§m ArgumentBuilder:r", a, r);
            l = Reflection.wrap("ArgumentBuilder:l then§m ArgumentBuilder:a", l, a);

            a = argument(ccm, "enchantment_custom", StringArgumentType.string());

            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.wrap("ArgumentBuilder:r executes§m Command:remove", r, remove);

            a = Reflection.wrap("ArgumentBuilder:a then§m ArgumentBuilder:r", a, r);
            l = Reflection.wrap("ArgumentBuilder:l then§m ArgumentBuilder:a", l, a);

            base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);
        }

        l = literal(ccm, "clear").executes(LibrGetCommand::clear);
        base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);

        l = literal(ccm, "list").executes(LibrGetCommand::list);
        base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);

        l = literal(ccm, "stop").executes(LibrGetCommand::stop);
        base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);

        l = literal(ccm, "start").executes(LibrGetCommand::start);
        base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);

        l = literal(ccm, "auto").executes(LibrGetCommand::autostart);
        base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);

        // automatically create config commands for each simply configurable value in Config
        l = literal(ccm, "config");
        for (Config.Configurable<?> configurable : Config.getConfigurables()) {
            String name = configurable.name();
            a = literal(ccm, name);
            r = argument(ccm, "value", configurable.argument());

            Object runner = runner(LibrGetCommand::config, configurable);

            r = Reflection.wrap("ArgumentBuilder:r executes§m Command:runner", r, runner);
            a = Reflection.wrap("ArgumentBuilder:a then§m ArgumentBuilder:r", a, r);
            a = Reflection.wrap("ArgumentBuilder:a executes§m Command:runner", a, runner);

            l = Reflection.wrap("ArgumentBuilder:l then§m ArgumentBuilder:a", l, a);
        }

        base = Reflection.wrap("ArgumentBuilder:base then§m ArgumentBuilder:l", base, l);

        Object selector = runner(LibrGetCommand::selector);
        base = Reflection.wrap("ArgumentBuilder:base executes§m Command:selector", base, selector);

        Reflection.wrapi("CommandDispatcher:dispatcher register§m base", base, dispatcher);
    }

}

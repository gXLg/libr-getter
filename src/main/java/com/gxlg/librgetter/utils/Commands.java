package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.Config;
import com.gxlg.librgetter.command.LibrGetCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.enchantment.Enchantment;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Commands {
    public static boolean getEnchantments(List<Either<Enchantment, String>> list, CommandContext<?> context) {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.TAGS)) {
            Class<?> pred = Reflection.clazz("net.minecraft.class_7737$class_7741", "net.minecraft.command.argument.RegistryEntryPredicateArgumentType$EntryPredicate");
            Object argument;
            try {
                argument = pred.cast(context.getArgument("enchantment", pred));
            } catch (IllegalArgumentException ignored) {
                list.add(Either.right(context.getArgument("enchantment_custom", String.class)));
                return true;
            }

            Object key;
            Class<?> rkc = Reflection.clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.EFFECTS)) {
                Class<?> rks = Reflection.clazz("net.minecraft.class_7924", "net.minecraft.registry.RegistryKeys");
                key = Reflection.field(rks, null, "field_41265", "ENCHANTMENT");
            } else {
                Class<?> registries = Reflection.clazz("net.minecraft.class_7923", "net.minecraft.registry.Registries");
                Object ench = Reflection.field(registries, null, "field_41176", "ENCHANTMENT");
                Class<?> registry = Reflection.clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
                key = Reflection.invokeMethod(registry, ench, null, "method_30517", "getKey");
            }

            Optional<?> opt = (Optional<?>) Reflection.invokeMethod(pred, argument, new Object[]{key}, new Class[]{rkc}, "method_45648", "tryCast");

            if (opt.isEmpty()) {
                Messages.sendError(context, "librgetter.argument");
                return false;
            }

            Object predicate = opt.get();
            Either<?, ?> entry = (Either<?, ?>) Reflection.invokeMethod(pred, predicate, null, "method_45647", "getEntry");
            Optional<?> optrefl = entry.left();
            Optional<?> optrefr = entry.right();

            Class<?> entryClass = Reflection.clazz("net.minecraft.class_6880$class_6883", "net.minecraft.registry.entry.RegistryEntry$Reference");
            if (optrefl.isEmpty()) {
                if (optrefr.isEmpty()) {
                    Messages.sendError(context, "librgetter.wrong");
                    return false;
                }
                Class<?> refClass = Reflection.clazz("net.minecraft.class_6885$class_6888", "net.minecraft.registry.entry.RegistryEntryList$Named");
                Object refL = optrefr.get();
                Stream<?> stream = (Stream<?>) Reflection.invokeMethod(refClass, refL, null, "method_40239", "stream");
                stream.forEach(ref -> list.add(Either.left((Enchantment) Reflection.invokeMethod(entryClass, ref, null, "comp_349", "value"))));
            } else {
                Object ref = optrefl.get();
                Enchantment enchantment = (Enchantment) Reflection.invokeMethod(entryClass, ref, null, "comp_349", "value");
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
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.TAGS)) {
            Object key;
            if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.EFFECTS)) {
                Class<?> rks = Reflection.clazz("net.minecraft.class_7924", "net.minecraft.registry.RegistryKeys");
                key = Reflection.field(rks, null, "field_41265", "ENCHANTMENT");
            } else {
                Class<?> registries = Reflection.clazz("net.minecraft.class_7923", "net.minecraft.registry.Registries");
                Object ench = Reflection.field(registries, null, "field_41176", "ENCHANTMENT");
                Class<?> registry = Reflection.clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
                key = Reflection.invokeMethod(registry, ench, null, "method_30517", "getKey");
            }

            Class<?> argType = Reflection.clazz("net.minecraft.class_7737", "net.minecraft.command.argument.RegistryEntryPredicateArgumentType");
            Class<?> registryKey = Reflection.clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            Class<?> cra = Reflection.clazz("net.minecraft.class_7157", "net.minecraft.command.CommandRegistryAccess");
            return (ArgumentType<?>) Reflection.invokeMethod(argType, null, new Object[]{registryAccess, key}, new Class[]{cra, registryKey}, "method_45637", "registryEntryPredicate");
        } else {
            Class<?> et = Reflection.clazz("net.minecraft.class_2194", "net.minecraft.command.argument.EnchantmentArgumentType", "net.minecraft.command.argument.ItemEnchantmentArgumentType");
            return (ArgumentType<?>) Reflection.invokeMethod(et, null, null, "method_9336", "enchantment", "itemEnchantment");
        }
    }

    public static void registerCommand() {
        if (MultiVersion.isApiLevel(MultiVersion.ApiLevel.API_COMMAND_V2)) {
            Class<?> cb = Reflection.clazz("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");
            Class<?> cra = Reflection.clazz("net.minecraft.class_7157", "net.minecraft.command.CommandRegistryAccess");
            Class<?> ccm = Reflection.clazz("net.fabricmc.fabric.api.client.command.v2.ClientCommandManager");

            Object listener = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cb}, (proxy, method, args) -> {
                if (method.getName().equals("register")) {
                    Object registryAccess = cra.cast(args[1]);
                    command(ccm, (CommandDispatcher<?>) args[0], registryAccess);
                    return null;
                }
                return method.invoke(proxy, args);
            });

            Event<?> event = (Event<?>) Reflection.field(cb, null, "EVENT");
            Reflection.invokeMethod(Event.class, event, new Object[]{listener}, new Class[]{Object.class}, "register");
        } else {
            Class<?> ccm = Reflection.clazz("net.fabricmc.fabric.api.client.command.v1.ClientCommandManager");
            command(ccm, (CommandDispatcher<?>) Reflection.field(ccm, null, "DISPATCHER"), null);
        }
    }

    private static ArgumentBuilder<?, ?> literal(Class<?> ccm, String command) {
        return (ArgumentBuilder<?, ?>) Reflection.invokeMethod(ccm, null, new Object[]{command}, "literal");
    }

    private static ArgumentBuilder<?, ?> argument(Class<?> ccm, String command, ArgumentType<?> argumentType) {
        return (ArgumentBuilder<?, ?>) Reflection.invokeMethod(ccm, null, new Object[]{command, argumentType}, new Class[]{String.class, ArgumentType.class}, "argument");
    }

    private static <T> Command<T> runner(Function<CommandContext<T>, Integer> function) {
        return function::apply;
    }

    private static <T> Command<T> runner(BiFunction<CommandContext<T>, String, Integer> function, String config) {
        return t -> function.apply(t, config);
    }

    public static void command(Class<?> ccm, CommandDispatcher<?> dispatcher, Object registryAccess) {
        Object base = literal(ccm, "librget");
        Object a, l, r, d;

        {
            l = literal(ccm, "add");
            a = argument(ccm, "enchantment", enchantmentArgument(registryAccess));
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{runner(LibrGetCommand::add)}, new Class[]{Command.class}, "executes");
            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::add)}, new Class[]{Command.class}, "executes");
            d = argument(ccm, "maxprice", IntegerArgumentType.integer(1, 64));
            d = Reflection.invokeMethod(ArgumentBuilder.class, d, new Object[]{runner(LibrGetCommand::add)}, new Class[]{Command.class}, "executes");
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{d}, new Class[]{ArgumentBuilder.class}, "then");
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
            l = Reflection.invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
            a = argument(ccm, "enchantment_custom", StringArgumentType.string());
            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::add)}, new Class[]{Command.class}, "executes");
            d = argument(ccm, "maxprice", IntegerArgumentType.integer(1, 64));
            d = Reflection.invokeMethod(ArgumentBuilder.class, d, new Object[]{runner(LibrGetCommand::add)}, new Class[]{Command.class}, "executes");
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{d}, new Class[]{ArgumentBuilder.class}, "then");
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
            l = Reflection.invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
            base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{l}, new Class[]{ArgumentBuilder.class}, "then");
        }
        {
            l = literal(ccm, "remove");
            a = argument(ccm, "enchantment", enchantmentArgument(registryAccess));
            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::remove)}, new Class[]{Command.class}, "executes");
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
            l = Reflection.invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
            a = argument(ccm, "enchantment_custom", StringArgumentType.string());
            r = argument(ccm, "level", IntegerArgumentType.integer(1));
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::remove)}, new Class[]{Command.class}, "executes");
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
            l = Reflection.invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
            base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{l}, new Class[]{ArgumentBuilder.class}, "then");
        }

        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "clear").executes(LibrGetCommand::clear)
        }, new Class[]{ArgumentBuilder.class}, "then");

        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "list").executes(LibrGetCommand::list)
        }, new Class[]{ArgumentBuilder.class}, "then");

        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "stop").executes(LibrGetCommand::stop)
        }, new Class[]{ArgumentBuilder.class}, "then");

        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "start").executes(LibrGetCommand::start)
        }, new Class[]{ArgumentBuilder.class}, "then");

        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "auto").executes(runner(LibrGetCommand::autostart))
        }, new Class[]{ArgumentBuilder.class}, "then");


        // automatically create config commands for each boolean in Config.class
        l = literal(ccm, "config");
        for (String name : Config.listBooleans()) {
            a = literal(ccm, name.toLowerCase());
            r = argument(ccm, "toggle", BoolArgumentType.bool());
            r = Reflection.invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::config, name)}, new Class[]{Command.class}, "executes");
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
            a = Reflection.invokeMethod(ArgumentBuilder.class, a, new Object[]{runner(LibrGetCommand::config, name)}, new Class[]{Command.class}, "executes");
            l = Reflection.invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        }
        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{l}, new Class[]{ArgumentBuilder.class}, "then");

        base = Reflection.invokeMethod(ArgumentBuilder.class, base, new Object[]{runner(LibrGetCommand::selector)}, new Class[]{Command.class}, "executes");

        Reflection.invokeMethod(CommandDispatcher.class, dispatcher, new Object[]{base}, "register");
    }

}

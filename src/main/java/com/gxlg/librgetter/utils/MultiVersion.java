package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.Config;
import com.gxlg.librgetter.LibrGetter;
import com.gxlg.librgetter.command.LibrGetCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.SharedConstants;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.village.TradeOfferList;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class MultiVersion {
    private final String version;

    public MultiVersion() {
        Class<?> clazzGameVersion = clazz("com.mojang.bridge.game.GameVersion", "net.minecraft.class_6489", "net.minecraft.GameVersion");
        Class<?> clazzConstants = SharedConstants.class;
        Object gameVersion = invokeMethod(clazzConstants, null, null, "method_16673", "getGameVersion");
        version = (String) invokeMethod(clazzGameVersion, gameVersion, null, "method_48019", "getName");
    }

    public String getVersion() {
        return version;
    }

    public int getApiLevel() {
        if (version.equals("1.20.4") || version.equals("1.20.3") || version.equals("1.20.2")) return 5;
        if (version.equals("1.20.1") || version.equals("1.20") || version.equals("1.19.4") || version.equals("1.19.3"))
            return 4;
        if (version.equals("1.19.2") || version.equals("1.19.1") || version.equals("1.19")) return 3;
        if (version.equals("1.18.2") || version.equals("1.18.1") || version.equals("1.18") || version.equals("1.17.1"))
            return 2;
        if (version.equals("1.17")) return 1;
        if (version.equals("1.16.5") || version.equals("1.16.4")) return 0;
        return -1;
    }

    public ClientConnection getConnection(ClientPlayNetworkHandler handler) {
        return (ClientConnection) invokeMethod(ClientPlayNetworkHandler.class, handler, null, "method_48296", "method_2872", "getConnection");
    }

    public void sendError(Object source, String message) {
        Class<?> fcs;
        Class<?> tc = clazz("net.minecraft.class_2561", "net.minecraft.text.Text");
        Object text;
        if (getApiLevel() >= 3) {
            fcs = clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = invokeMethod(tc, null, new Object[]{message}, "method_43470", "literal");
        } else {
            fcs = clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Class<?> lit = clazz("net.minecraft.class_2585", "net.minecraft.text.LiteralText");
            text = construct(lit, new Object[]{message}, String.class);
        }
        invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendError");
    }

    public void sendFeedback(Object source, String message, Formatting... format) {
        Class<?> fcs;
        Class<?> mc = clazz("net.minecraft.class_5250", "net.minecraft.text.MutableText");
        Class<?> tc = clazz("net.minecraft.class_2561", "net.minecraft.text.Text");
        Object text;
        if (getApiLevel() >= 3) {
            fcs = clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = invokeMethod(tc, null, new Object[]{message}, "method_43470", "literal");
        } else {
            fcs = clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Class<?> lit = clazz("net.minecraft.class_2585", "net.minecraft.text.LiteralText");
            text = construct(lit, new Object[]{message}, String.class);
        }
        if (format.length != 0) {
            text = invokeMethod(mc, text, new Object[]{format[0]}, "method_27692", "formatted");
        }
        invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendFeedback");
    }

    public void sendMessage(ClientPlayerEntity player, String message, boolean ab) {
        Class<?> tc = clazz("net.minecraft.class_2561", "net.minecraft.text.Text");
        Object text;
        if (getApiLevel() >= 3) {
            text = invokeMethod(tc, null, new Object[]{message}, "method_43470", "literal");
        } else {
            Class<?> lit = clazz("net.minecraft.class_2585", "net.minecraft.text.LiteralText");
            text = construct(lit, new Object[]{message}, String.class);
        }
        invokeMethod(ClientPlayerEntity.class, player, new Object[]{text, ab}, new Class[]{tc, boolean.class}, "method_7353", "sendMessage");
    }

    public void list(Object source) {
        Class<?> fcs;
        Class<?> tc = clazz("net.minecraft.class_2561", "net.minecraft.text.Text");
        Class<?> mc = clazz("net.minecraft.class_5250", "net.minecraft.text.MutableText");
        Object text;
        Object rem;
        if (getApiLevel() >= 3) {
            fcs = clazz("net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource");
            text = invokeMethod(tc, null, new Object[]{"Goals list:"}, "method_43470", "literal");
            rem = invokeMethod(tc, null, new Object[]{"(remove)"}, "method_43470", "literal");
        } else {
            fcs = clazz("net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource");
            Object t = invokeMethod(tc, null, new Object[]{"Goals list:"}, "method_30163", "of");
            text = invokeMethod(tc, t, null, "method_27662", "copy");
            rem = invokeMethod(tc, null, new Object[]{"(remove)"}, "method_30163", "of");
        }
        for (Config.Enchantment l : LibrGetter.config.goals) {
            text = invokeMethod(mc, text, new Object[]{"\n- " + l + " (" + l.price + ") "}, "method_27693", "append");
            Style style = Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/librget remove " + l));
            Object remC = invokeMethod(tc, rem, null, "method_27662", "copy");
            invokeMethod(mc, remC, new Object[]{style}, "method_10862", "setStyle");
            text = invokeMethod(mc, text, new Object[]{remC}, new Class[]{tc}, "method_10852", "append");
        }
        invokeMethod(fcs, source, new Object[]{text}, new Class[]{tc}, "sendFeedback");
    }

    public ClientWorld getWorld(ClientPlayerEntity player) {
        if (getApiLevel() >= 3) {
            return (ClientWorld) invokeMethod(ClientPlayerEntity.class, player, null, "method_37908", "getWorld");
        } else {
            return (ClientWorld) field(ClientPlayerEntity.class, player, "field_17892", "clientWorld");
        }
    }

    public void interactBlock(ClientPlayerInteractionManager manager, ClientPlayerEntity player, BlockHitResult lowBlock) {
        if (getApiLevel() >= 3) {
            invokeMethod(ClientPlayerInteractionManager.class, manager, new Object[]{player, Hand.MAIN_HAND, lowBlock}, "method_2896", "interactBlock");
        } else {
            invokeMethod(ClientPlayerInteractionManager.class, manager, new Object[]{player, getWorld(player), Hand.MAIN_HAND, lowBlock}, "method_2896", "interactBlock");
        }
    }

    public boolean getEnchantments(List<Enchantment> list, CommandContext<?> context) {
        if (getApiLevel() >= 4) {
            Class<?> pred = clazz("net.minecraft.class_7737$class_7741", "net.minecraft.command.argument.RegistryEntryPredicateArgumentType$EntryPredicate");
            Object argument = pred.cast(context.getArgument("enchantment", pred));

            Class<?> registries = clazz("net.minecraft.class_7923", "net.minecraft.registry.Registries");
            Object ench = field(registries, null, "field_41176", "ENCHANTMENT");
            Class<?> registry = clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
            Object key = invokeMethod(registry, ench, null, "method_30517", "getKey");

            Optional<?> opt = (Optional<?>) invokeMethod(pred, argument, new Object[]{key}, "method_45648", "tryCast");

            if (!opt.isPresent()) {
                sendError(context, "This argument type is not supported!");
                return false;
            }

            Object predicate = opt.get();
            Either<?, ?> entry = (Either<?, ?>) invokeMethod(pred, predicate, null, "method_45647", "getEntry");
            Optional<?> optrefl = entry.left();
            Optional<?> optrefr = entry.right();

            Class<?> entryClass = clazz("net.minecraft.class_6880$class_6883", "net.minecraft.registry.entry.RegistryEntry$Reference");
            if (!optrefl.isPresent()) {
                if (!optrefr.isPresent()) {
                    sendError(context, "Wrong enchantment provided!");
                    return false;
                }
                Class<?> refClass = clazz("net.minecraft.class_6885$class_6888", "net.minecraft.registry.entry.RegistryEntryList$Named");
                Object refL = optrefr.get();
                Stream<?> stream = (Stream<?>) invokeMethod(refClass, refL, null, "method_40239", "stream");
                stream.forEach(ref -> list.add((Enchantment) invokeMethod(entryClass, ref, null, "comp_349", "value")));
            } else {
                Object ref = optrefl.get();
                Enchantment enchantment = (Enchantment) invokeMethod(entryClass, ref, null, "comp_349", "value");
                list.add(enchantment);
            }
        } else {
            Enchantment enchantment = context.getArgument("enchantment", Enchantment.class);
            list.add(enchantment);
        }
        return true;
    }

    private ArgumentType<?> enchantmentArgument(Object registryAccess) {
        if (getApiLevel() >= 4) {
            Class<?> registries = clazz("net.minecraft.class_7923", "net.minecraft.registry.Registries");
            Object ench = field(registries, null, "field_41176", "ENCHANTMENT");
            Class<?> registry = clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
            Object key = invokeMethod(registry, ench, null, "method_30517", "getKey");
            Class<?> argType = clazz("net.minecraft.class_7737", "net.minecraft.command.argument.RegistryEntryPredicateArgumentType");
            Class<?> registryKey = clazz("net.minecraft.class_5321", "net.minecraft.registry.RegistryKey");
            Class<?> cra = clazz("net.minecraft.class_7157", "net.minecraft.command.CommandRegistryAccess");
            return (ArgumentType<?>) invokeMethod(argType, null, new Object[]{registryAccess, key}, new Class[]{cra, registryKey}, "method_45637", "registryEntryPredicate");
        } else {
            Class<?> et = clazz("net.minecraft.class_2194", "net.minecraft.command.argument.EnchantmentArgumentType", "net.minecraft.command.argument.ItemEnchantmentArgumentType");
            return (ArgumentType<?>) invokeMethod(et, null, null, "method_9336", "enchantment", "itemEnchantment");
        }
    }

    public Identifier enchantmentId(Enchantment enchantment) {
        if (getApiLevel() >= 4) {
            Class<?> registries = clazz("net.minecraft.class_7923", "net.minecraft.registry.Registries");
            Object ench = field(registries, null, "field_41176", "ENCHANTMENT");
            Class<?> registry = clazz("net.minecraft.class_2378", "net.minecraft.registry.Registry");
            return (Identifier) invokeMethod(registry, ench, new Object[]{enchantment}, new Class[]{Object.class}, "method_10221", "getId");
        } else {
            Class<?> registry = clazz("net.minecraft.class_2378", "net.minecraft.util.registry.Registry");
            Object ench = field(registry, null, "field_11160", "ENCHANTMENT");
            return (Identifier) invokeMethod(registry, ench, new Object[]{enchantment}, new Class[]{Object.class}, "method_10221", "getId");
        }
    }

    public void registerCommand() {
        if (getApiLevel() >= 3) {
            Class<?> cb = clazz("net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback");
            Class<?> cra = clazz("net.minecraft.class_7157", "net.minecraft.command.CommandRegistryAccess");
            Class<?> ccm = clazz("net.fabricmc.fabric.api.client.command.v2.ClientCommandManager");

            Object listener = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cb}, (proxy, method, args) -> {
                if (method.getName().equals("register")) {
                    Object registryAccess = cra.cast(args[1]);
                    command(ccm, (CommandDispatcher<?>) args[0], registryAccess);
                    return null;
                }
                return method.invoke(proxy, args);
            });

            Event<?> event = (Event<?>) field(cb, null, "EVENT");
            invokeMethod(Event.class, event, new Object[]{listener}, new Class[]{Object.class}, "register");
        } else {
            Class<?> ccm = clazz("net.fabricmc.fabric.api.client.command.v1.ClientCommandManager");
            command(ccm, (CommandDispatcher<?>) field(ccm, null, "DISPATCHER"), null);
        }
    }

    private ArgumentBuilder<?, ?> literal(Class<?> ccm, String command) {
        return (ArgumentBuilder<?, ?>) invokeMethod(ccm, null, new Object[]{command}, "literal");
    }

    private ArgumentBuilder<?, ?> argument(Class<?> ccm, String command, ArgumentType<?> argumentType) {
        return (ArgumentBuilder<?, ?>) invokeMethod(ccm, null, new Object[]{command, argumentType}, new Class[]{String.class, ArgumentType.class}, "argument");
    }

    private <T> Command<T> runner(Function<CommandContext<T>, Integer> function) {
        return function::apply;
    }

    public void command(Class<?> ccm, CommandDispatcher<?> dispatcher, Object registryAccess) {
        Object base = literal(ccm, "librget");
        Object a, l, r, d;

        l = literal(ccm, "add");
        a = argument(ccm, "enchantment", LibrGetter.MULTI.enchantmentArgument(registryAccess));
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{runner(LibrGetCommand::runAdd)}, new Class[]{Command.class}, "executes");
        r = argument(ccm, "level", IntegerArgumentType.integer(1));
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runAdd)}, new Class[]{Command.class}, "executes");
        d = argument(ccm, "maxprice", IntegerArgumentType.integer(1, 64));
        d = invokeMethod(ArgumentBuilder.class, d, new Object[]{runner(LibrGetCommand::runAdd)}, new Class[]{Command.class}, "executes");
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{d}, new Class[]{ArgumentBuilder.class}, "then");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{l}, new Class[]{ArgumentBuilder.class}, "then");

        l = literal(ccm, "remove");
        a = argument(ccm, "enchantment", LibrGetter.MULTI.enchantmentArgument(registryAccess));
        r = argument(ccm, "level", IntegerArgumentType.integer(1));
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runRemove)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{l}, new Class[]{ArgumentBuilder.class}, "then");

        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "clear").executes(LibrGetCommand::runClear)
        }, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "list").executes(LibrGetCommand::runList)
        }, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "stop").executes(LibrGetCommand::runStop)
        }, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "start").executes(LibrGetCommand::runStart)
        }, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{
                literal(ccm, "auto").executes(runner(LibrGetCommand::runAutostart))
        }, new Class[]{ArgumentBuilder.class}, "then");

        l = literal(ccm, "config");
        a = literal(ccm, "notify");
        r = argument(ccm, "toggle", BoolArgumentType.bool());
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runNotify)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        a = literal(ccm, "autotool");
        r = argument(ccm, "toggle", BoolArgumentType.bool());
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runTool)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        a = literal(ccm, "actionbar");
        r = argument(ccm, "toggle", BoolArgumentType.bool());
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runActionBar)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        a = literal(ccm, "lock");
        r = argument(ccm, "toggle", BoolArgumentType.bool());
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runLock)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        a = literal(ccm, "removegoal");
        r = argument(ccm, "toggle", BoolArgumentType.bool());
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runRemoveGoal)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        a = literal(ccm, "checkupdate");
        r = argument(ccm, "toggle", BoolArgumentType.bool());
        r = invokeMethod(ArgumentBuilder.class, r, new Object[]{runner(LibrGetCommand::runCheckUpdate)}, new Class[]{Command.class}, "executes");
        a = invokeMethod(ArgumentBuilder.class, a, new Object[]{r}, new Class[]{ArgumentBuilder.class}, "then");
        l = invokeMethod(ArgumentBuilder.class, l, new Object[]{a}, new Class[]{ArgumentBuilder.class}, "then");
        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{l}, new Class[]{ArgumentBuilder.class}, "then");

        base = invokeMethod(ArgumentBuilder.class, base, new Object[]{runner(LibrGetCommand::runSelector)}, new Class[]{Command.class}, "executes");

        invokeMethod(CommandDispatcher.class, dispatcher, new Object[]{base}, "register");
    }

    public PlayerInventory getInventory(ClientPlayerEntity player) {
        if (getApiLevel() >= 2) {
            return (PlayerInventory) invokeMethod(ClientPlayerEntity.class, player, null, "method_31548", "getInventory");
        } else {
            return (PlayerInventory) field(ClientPlayerEntity.class, player, "field_7514", "inventory");
        }
    }

    public PlayerInteractEntityC2SPacket interactPacket(VillagerEntity villager) {
        if (getApiLevel() >= 2) {
            return (PlayerInteractEntityC2SPacket) invokeMethod(PlayerInteractEntityC2SPacket.class, null, new Object[]{villager, false, Hand.MAIN_HAND}, new Class[]{Entity.class, boolean.class, Hand.class}, "method_34207", "interact");
        } else {
            return (PlayerInteractEntityC2SPacket) construct(PlayerInteractEntityC2SPacket.class, new Object[]{villager, Hand.MAIN_HAND, false}, Entity.class, Hand.class, boolean.class);
        }
    }

    public Either<Config.Enchantment, String> parseTrade(TradeOfferList trades, int trade) {
        Object tag = invokeMethod(ItemStack.class, trades.get(trade).getSellItem(), null, "method_7969", "getNbt", "getTag");
        Class<?> nbtcompound = clazz("net.minecraft.class_2487", "net.minecraft.nbt.CompoundTag", "net.minecraft.nbt.NbtCompound");
        Class<?> nbtlist = clazz("net.minecraft.class_2499", "net.minecraft.nbt.ListTag", "net.minecraft.nbt.NbtList");
        Class<?> nbtelement = clazz("net.minecraft.class_2520", "net.minecraft.nbt.Tag", "net.minecraft.nbt.NbtElement");
        Class<?> nbtshort = clazz("net.minecraft.class_2516", "net.minecraft.nbt.ShortTag", "net.minecraft.nbt.NbtShort");

        if (tag == null) {
            return Either.right("InternalError: tag == null");
        }

        Object list = invokeMethod(nbtcompound, tag, new Object[]{"StoredEnchantments", 10}, new Class[]{String.class, int.class}, "method_10554", "getList");
        Object element = nbtcompound.cast(invokeMethod(nbtlist, list, new Object[]{0}, new Class[]{int.class}, "method_10534", "get"));

        Object id = invokeMethod(nbtcompound, element, new Object[]{"id"}, "method_10580", "get");
        Object lvl = invokeMethod(nbtcompound, element, new Object[]{"lvl"}, "method_10580", "get");

        ItemStack f = trades.get(trade).getAdjustedFirstBuyItem();
        ItemStack s = trades.get(trade).getSecondBuyItem();
        if (f.getItem() != Items.EMERALD) f = null;
        if (s.getItem() == Items.EMERALD) f = s;

        if (id == null || lvl == null || f == null) {
            return Either.right("InternalError: id == null or lvl == null or f == null");
        }

        String ids = (String) invokeMethod(nbtelement, id, null, "method_10714", "asString");
        int lvli = (int) invokeMethod(nbtshort, nbtshort.cast(lvl), null, "method_10701", "getInt", "intValue");

        return Either.left(new Config.Enchantment(ids, lvli, f.getCount()));
    }


    private static Object construct(Class<?> clazz, Object[] args, Class<?>... params) {
        try {
            Constructor<?> con = clazz.getConstructor(params);
            return con.newInstance(args);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method method(Class<?> clazz, Class<?>[] args, String... methods) {
        for (String method : methods) {
            try {
                return clazz.getMethod(method, args);
            } catch (NoSuchMethodException ignored) {
            }
        }
        throw new RuntimeException("method not found from " + Arrays.toString(methods));
    }

    private static Object invokeMethod(Class<?> clazz, Object instance, Object[] args, String... methods) {
        if (args == null) args = new Object[0];

        Class<?>[] search = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) search[i] = args[i].getClass();

        return invokeMethod(clazz, instance, args, search, methods);
    }

    private static Object invokeMethod(Class<?> clazz, Object instance, Object[] args, Class<?>[] search, String... methods) {
        if (args == null) args = new Object[0];
        if (search == null) search = new Class<?>[0];

        Method method = method(clazz, search, methods);
        try {
            return method.invoke(instance, args);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodError e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> clazz(String... classes) {
        for (String clazz : classes) {
            try {
                return Thread.currentThread().getContextClassLoader().loadClass(clazz);
            } catch (ClassNotFoundException ignored) {
            }
        }
        throw new RuntimeException("class not found from " + Arrays.toString(classes));
    }

    private static Object field(Class<?> clazz, Object instance, String... fields) {
        for (String field : fields) {
            try {
                Field f = clazz.getField(field);
                return f.get(instance);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }
        throw new RuntimeException("field not found from " + Arrays.toString(fields));
    }
}

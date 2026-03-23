package dev.gxlg.librgetter.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.gxlg.librgetter.gui.ConfigScreen;
import dev.gxlg.librgetter.utils.chaining.commands.Commands;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.config.Config;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.AlreadyRunningException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.BlockNotLecternException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.CouldNotFindLecternException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.CouldNotFindLibrarianException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.EntityNotVillagerException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.NotInGoalsException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.NothingTargetedException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.commands.VillagerNotLibrarianException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.parser.CouldNotParseCustomException;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.tasks.ProcessNotRunningException;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ConfigValueMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.EnchantmentRemovedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.GoalsListClearedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.LecternSelectedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.LibrarianSelectedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ListGoalsMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.ProcessStoppedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.CustomTradeAddedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.PriceChangedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.TradeAddedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.TranslatableSuccessMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.warning.AddingCustomEnchantmentMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.warning.CanNotBeTradedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.warning.LevelOverMaxMessage;
import dev.gxlg.librgetter.worker.scheduling.controllers.UserSchedulerController;
import dev.gxlg.librgetter.worker.state.StateView;
import dev.gxlg.librgetter.worker.tasks.StandbyTask;
import dev.gxlg.librgetter.worker.tasks.StartTask;
import dev.gxlg.librgetter.worker.types.switcher.TaskSwitch;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.CommandDispatcher;
import dev.gxlg.versiont.gen.com.mojang.brigadier.arguments.ArgumentType;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.ArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.screens.Screen;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.commands.CommandBuildContext;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.Entity;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.item.Items;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.BlockHitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.EntityHitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.HitResult;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.HitResult$Type;

import java.util.ArrayList;
import java.util.List;

public class LibrGetCommand implements CommandsManager.Command {
    private final ConfigManager configManager;

    private final UserSchedulerController controller;

    private final StateView stateView;

    public LibrGetCommand(ConfigManager configManager, UserSchedulerController controller, StateView stateView) {
        this.configManager = configManager;
        this.controller = controller;
        this.stateView = stateView;
    }

    private void add(CommandContext context) throws LibrGetterException {
        manageGoals(context, false);
    }

    private void addCustom(CommandContext context) throws LibrGetterException {
        manageGoalsCustom(context, false);
    }

    private void remove(CommandContext context) throws LibrGetterException {
        manageGoals(context, true);
    }

    private void removeCustom(CommandContext context) throws LibrGetterException {
        manageGoalsCustom(context, true);
    }

    private void list() {
        Texts.sendMessage(new ListGoalsMessage(configManager.getData().getGoals()));
    }

    private <T> void config(CommandContext context, Configurable<T> config) {
        T value = config.get();
        try {
            Class<T> type = config.type();
            value = context.getArgument("value", R.clz(type)).unwrap(type);
            config.set(value);
            config.managerInstance().save();
        } catch (IllegalArgumentException ignored) {
        }

        Screen screen = Minecraft.getInstance().getScreenField();
        if (screen instanceof ConfigScreen configScreen) {
            configScreen.updateScreen();
        } else {
            Texts.sendMessage(new ConfigValueMessage(config.config(), value));
        }
    }

    private void autostart() throws LibrGetterException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }

        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        ClientLevel world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }

        BlockPos foundLecternPos = null;
        for (int distance = 1; distance < 5; distance++) {
            for (int deltaX = -distance; deltaX <= distance; deltaX++) {
                for (int deltaY = -distance; deltaY <= distance; deltaY++) {
                    for (int deltaZ = -distance; deltaZ <= distance; deltaZ++) {
                        if (distance != Math.abs(deltaX) && distance != Math.abs(deltaY) && distance != Math.abs(deltaZ)) {
                            continue;
                        }

                        BlockPos pos = player.blockPosition().offset(deltaX, deltaY, deltaZ);
                        if (world.getBlockState(pos).is(Blocks.LECTERN())) {
                            foundLecternPos = pos;
                            break;
                        }
                    }
                    if (foundLecternPos != null) {
                        break;
                    }
                }
                if (foundLecternPos != null) {
                    break;
                }
            }
            if (foundLecternPos != null) {
                break;
            }
        }
        if (foundLecternPos == null) {
            throw new CouldNotFindLecternException();
        }
        Iterable<Entity> worldEntities = world.entitiesForRendering();
        Villager foundVillager = null;
        float minDistance = Float.MAX_VALUE;
        for (Entity entity : worldEntities) {
            if (entity instanceof Villager villager) {
                if (Villagers.isVillagerLibrarian(villager)) {
                    float distance = villager.distanceTo(player);
                    if (distance < minDistance && distance < 10) {
                        foundVillager = villager;
                        minDistance = distance;
                    }
                }
            }
        }
        if (foundVillager == null) {
            throw new CouldNotFindLibrarianException();
        }

        BlockPos finalLecternPos = foundLecternPos;
        Villager finalVillager = foundVillager;

        controller.scheduleContextUpdate(ctx -> ctx.setLecternPos(finalLecternPos).setVillager(finalVillager));
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(true)));
    }

    private void clearGoals() {
        configManager.getData().clearGoals();
        configManager.save();
        Texts.sendMessage(new GoalsListClearedMessage());
    }

    private void stopWorking() throws ProcessNotRunningException {
        if (!stateView.isWorking()) {
            throw new ProcessNotRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> {
            Texts.sendMessage(new ProcessStoppedMessage());
            return new StandbyTask();
        }));
    }

    private void startWorking() throws AlreadyRunningException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(true)));
    }

    private void continueWorking() throws AlreadyRunningException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }
        controller.scheduleTaskSwitch(TaskSwitch.nextTick(() -> new StartTask(false)));
    }

    private void selector() throws LibrGetterException {
        if (stateView.isWorking()) {
            throw new AlreadyRunningException();
        }

        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.getLevelField();
        if (world == null) {
            throw new InternalErrorException("world");
        }
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            throw new InternalErrorException("player");
        }
        HitResult hit = client.getHitResultField();
        if (hit == null) {
            throw new InternalErrorException("hit");
        }
        HitResult$Type hitType = hit.getType();
        if (hitType.equals(HitResult$Type.MISS())) {
            throw new NothingTargetedException();
        }

        if (hitType.equals(HitResult$Type.BLOCK())) {
            BlockPos blockPos = ((BlockHitResult) hit).getBlockPos();
            if (!world.getBlockState(blockPos).is(Blocks.LECTERN())) {
                throw new BlockNotLecternException();
            }
            controller.scheduleContextUpdate(ctx -> {
                ctx.setLecternPos(blockPos);
                Texts.sendMessage(new LecternSelectedMessage());
            });

        } else if (hitType.equals(HitResult$Type.ENTITY())) {
            Entity entity = ((EntityHitResult) hit).getEntity();
            if (!(entity instanceof Villager villager)) {
                throw new EntityNotVillagerException();
            }
            if (!Villagers.isVillagerLibrarian(villager)) {
                throw new VillagerNotLibrarianException();
            }
            controller.scheduleContextUpdate(ctx -> {
                ctx.setVillager(villager);
                Texts.sendMessage(new LibrarianSelectedMessage());
            });
        }
    }

    private void manageGoals(CommandContext context, boolean remove) throws LibrGetterException {
        List<Enchantment> enchantments = Commands.getEnchantmentsFromCommandContext(context);

        int globalLvlCriteria;
        try {
            globalLvlCriteria = context.getArgument("level", R.clz(Integer.class)).unwrap(Integer.class);
        } catch (IllegalArgumentException ignored) {
            globalLvlCriteria = -1;
        }

        int price = Items.EMERALD().getDefaultMaxStackSize();
        try {
            price = context.getArgument("maxprice", R.clz(Integer.class)).unwrap(Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        List<EnchantmentTrade> trades = new ArrayList<>();
        List<Integer> maxLevels = new ArrayList<>();
        for (Enchantment enchantment : enchantments) {
            Identifier enchantmentId = Enchantments.enchantmentId(enchantment);
            if (enchantmentId == null) {
                throw new InternalErrorException("enchantmentId");
            }
            int max = enchantment.getMaxLevel();
            maxLevels.add(max);
            int level = (globalLvlCriteria == -1 && !remove) ? max : globalLvlCriteria;
            trades.add(new EnchantmentTrade(enchantmentId.toString(), level, price));
        }

        if (!remove) {
            for (int i = 0; i < enchantments.size(); i++) {
                Enchantment enchantment = enchantments.get(i);
                EnchantmentTrade trade = trades.get(i);
                int maxLevel = maxLevels.get(i);

                if (configManager.getBoolean(Config.WARNING) && !Enchantments.canBeTraded(enchantment)) {
                    Texts.sendMessage(new CanNotBeTradedMessage(trade));
                }

                if (configManager.getBoolean(Config.WARNING) && trade.lvl() > maxLevel) {
                    new LevelOverMaxMessage(trade, enchantment.getMaxLevel());
                }

                addGoal(trade, false);
            }
            return;
        }

        boolean anyRemoved = false;
        if (globalLvlCriteria == -1) {
            for (EnchantmentTrade trade : trades) {
                try {
                    removeGoalAllLevels(trade);
                } catch (NotInGoalsException e) {
                    continue;
                }
                anyRemoved = true;
            }
        } else {
            for (int i = 0; i < enchantments.size(); i++) {
                EnchantmentTrade trade = trades.get(i);
                int maxLevel = maxLevels.get(i);
                if (configManager.getBoolean(Config.WARNING) && globalLvlCriteria > maxLevel) {
                    Texts.sendMessage(new LevelOverMaxMessage(trade, maxLevel));
                }
                try {
                    removeGoal(trade);
                } catch (NotInGoalsException e) {
                    continue;
                }
                anyRemoved = true;
            }
        }
        if (!anyRemoved) {
            throw new NotInGoalsException(trades);
        }
    }

    private void manageGoalsCustom(CommandContext context, boolean remove) throws LibrGetterException {
        String enchantment = Commands.getCustomEnchantmentFromCommandContext(context);
        if (Identifier.tryParse(enchantment) == null) {
            throw new CouldNotParseCustomException();
        }

        int enchantmentLevel = context.getArgument("level", R.clz(Integer.class)).unwrap(Integer.class);

        int price = Items.EMERALD().getDefaultMaxStackSize();
        try {
            price = context.getArgument("maxprice", R.clz(Integer.class)).unwrap(Integer.class);
        } catch (IllegalArgumentException ignored) {
        }

        EnchantmentTrade trade = new EnchantmentTrade(enchantment, enchantmentLevel, price);

        if (remove) {
            removeGoal(trade);
        } else {
            if (configManager.getBoolean(Config.WARNING)) {
                Texts.sendMessage(new AddingCustomEnchantmentMessage(trade));
            }
            addGoal(trade, true);
        }
    }

    private void addGoal(EnchantmentTrade newTrade, boolean custom) {
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : configManager.getData().getGoals()) {
            if (trade.same(newTrade)) {
                alreadyPresentTrade = trade;
                break;
            }
        }

        if (alreadyPresentTrade != null) {
            Texts.sendMessage(new PriceChangedMessage(alreadyPresentTrade, newTrade.price()));
            configManager.getData().removeGoal(alreadyPresentTrade);
        } else {
            TranslatableSuccessMessage message = custom ? new CustomTradeAddedMessage(newTrade, newTrade.price()) : new TradeAddedMessage(newTrade, newTrade.price());
            Texts.sendMessage(message);
        }
        configManager.getData().addGoal(newTrade);
        configManager.save();
    }

    private void removeGoalAllLevels(EnchantmentTrade tradeToRemove) throws NotInGoalsException {
        List<EnchantmentTrade> alreadyPresentTrades = new ArrayList<>();
        for (EnchantmentTrade trade : configManager.getData().getGoals()) {
            if (trade.id().equals(tradeToRemove.id())) {
                alreadyPresentTrades.add(trade);
            }
        }
        if (alreadyPresentTrades.isEmpty()) {
            throw new NotInGoalsException(tradeToRemove);
        }
        for (EnchantmentTrade trade : alreadyPresentTrades) {
            configManager.getData().removeGoal(trade);
            Texts.sendMessage(new EnchantmentRemovedMessage(trade));
        }
        configManager.save();
    }

    private void removeGoal(EnchantmentTrade tradeToRemove) throws NotInGoalsException {
        EnchantmentTrade alreadyPresentTrade = null;
        for (EnchantmentTrade trade : configManager.getData().getGoals()) {
            if (trade.same(tradeToRemove)) {
                alreadyPresentTrade = trade;
                break;
            }
        }
        if (alreadyPresentTrade == null) {
            throw new NotInGoalsException(tradeToRemove);
        }
        configManager.getData().removeGoal(alreadyPresentTrade);
        configManager.save();
        Texts.sendMessage(new EnchantmentRemovedMessage(tradeToRemove));
    }

    public void register(CommandDispatcher dispatcher, CommandBuildContext context) {
        ArgumentType enchantmentArgumentType = Commands.getEnchantmentArgumentType(context);

        ArgumentBuilder baseCommand = Commands.literal("librget");

        ArgumentBuilder subCommand;

        // add subcommand
        {
            subCommand = Commands.literal("add");

            ArgumentBuilder enchantmentArgument, levelArgument, priceArgument;

            enchantmentArgument = Commands.argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(this::add));
            levelArgument = Commands.argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(this::add));
            priceArgument = Commands.argument("maxprice", IntegerArgumentType.integer(1, 64)).executes(CommandHelper.commandWrapper(this::add));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument.then(priceArgument)));

            enchantmentArgument = Commands.argument("enchantment_custom", StringArgumentType.string());
            levelArgument = Commands.argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(this::addCustom));
            priceArgument = Commands.argument("maxprice", IntegerArgumentType.integer(1, 64)).executes(CommandHelper.commandWrapper(this::addCustom));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument.then(priceArgument)));

            baseCommand = baseCommand.then(subCommand);
        }

        // remove subcommand
        {
            subCommand = Commands.literal("remove");
            ArgumentBuilder enchantmentArgument, levelArgument;

            enchantmentArgument = Commands.argument("enchantment", enchantmentArgumentType).executes(CommandHelper.commandWrapper(this::remove));
            levelArgument = Commands.argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(this::remove));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument));

            enchantmentArgument = Commands.argument("enchantment_custom", StringArgumentType.string()).executes(CommandHelper.commandWrapper(this::removeCustom));
            levelArgument = Commands.argument("level", IntegerArgumentType.integer(1)).executes(CommandHelper.commandWrapper(this::removeCustom));
            subCommand = subCommand.then(enchantmentArgument.then(levelArgument));

            baseCommand = baseCommand.then(subCommand);
        }

        // no-arg subcommands
        {
            subCommand = Commands.literal("clear").executes(CommandHelper.commandWrapper(ctx -> clearGoals()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = Commands.literal("list").executes(CommandHelper.commandWrapper(ctx -> list()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = Commands.literal("stop").executes(CommandHelper.commandWrapper(ctx -> stopWorking()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = Commands.literal("start").executes(CommandHelper.commandWrapper(ctx -> startWorking()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = Commands.literal("continue").executes(CommandHelper.commandWrapper(ctx -> continueWorking()));
            baseCommand = baseCommand.then(subCommand);

            subCommand = Commands.literal("auto").executes(CommandHelper.commandWrapper(ctx -> autostart()));
            baseCommand = baseCommand.then(subCommand);
        }

        // automatically create config commands for each simply configurable value in Config
        {
            subCommand = Commands.literal("config");
            for (Configurable<?> configurable : configManager.getConfigurables()) {
                String name = configurable.config().getId();

                ArgumentBuilder configArgument = Commands.literal(name).executes(CommandHelper.commandWrapper(ctx -> config(ctx, configurable)));
                ArgumentBuilder valueArgument = Commands.argument("value", configurable.commandArgument()).executes(CommandHelper.commandWrapper(ctx -> config(ctx, configurable)));

                subCommand = subCommand.then(configArgument.then(valueArgument));
            }
            baseCommand = baseCommand.then(subCommand);
        }

        // selector
        {
            baseCommand = baseCommand.executes(CommandHelper.commandWrapper(ctx -> selector()));
        }

        dispatcher.register((LiteralArgumentBuilder) baseCommand);
    }
}
package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.enums.LogMode;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.runtime.UnexpectedConfigurableTypeException;
import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.librgetter.utils.types.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.config.TranslatableCategory;
import dev.gxlg.librgetter.utils.types.messages.translatable.config.TranslatableConfigDescription;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.NewVersionReleasedMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.feedback.OfferMessage;
import dev.gxlg.librgetter.utils.types.messages.translatable.partial.TranslatableConfigMenuName;
import dev.gxlg.librgetter.utils.types.messages.translatable.partial.TranslatableGoalsListName;
import dev.gxlg.librgetter.utils.types.messages.translatable.partial.TranslatableRemoveButton;
import dev.gxlg.librgetter.utils.types.messages.translatable.success.EnchantmentFoundMessage;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.locale.Language;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent$Action;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent$Action;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.contents.TranslatableContents;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;

public class Texts_1_17_0 extends Texts.Base {
    protected void sendMessage(Component text, boolean actionbar) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            return;
        }
        player.displayClientMessage(text, actionbar);
    }

    @Override
    public void sendTranslatable(TranslatableMessage translatableMessage) {
        sendMessage(translatableMessage.getComponent(), false);
    }

    @Override
    public void sendFound(EnchantmentTrade enchant, int counter) {
        MutableComponent text = new EnchantmentFoundMessage(enchant, counter).getComponent();
        text = text.withStyle(ChatFormatting.GREEN());

        if (!LibrGetter.config.removeGoal) {
            text = text.append(" ");
            Style style = Style.EMPTY().withClickEvent(runnable("/librget remove \"" + enchant.id() + "\" " + enchant.lvl()));
            text = text.append(new TranslatableRemoveButton().getComponent().withStyle(style));
        }
        sendMessage(text, false);
    }

    public void sendTradeLog(List<EnchantmentTrade> offeredEnchantments) {
        if (LibrGetter.config.logMode == LogMode.NONE) {
            return;
        }
        boolean ab = LibrGetter.config.logMode == LogMode.ACTIONBAR;
        MutableComponent text = new OfferMessage(offeredEnchantments).getComponent();
        sendMessage(text, ab);
    }

    @Override
    public void sendNewVersion(String releaseName, String description) {
        MutableComponent text = new NewVersionReleasedMessage(releaseName).getComponent();
        Style style = Style.EMPTY().withHoverEvent(hoverable(literal(description)));
        sendMessage(text.withStyle(style), false);
    }

    @Override
    public void sendListOfGoals() {
        MutableComponent text = new TranslatableGoalsListName().getComponent();
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            text = text.append("\n- ").append(new TradeMessage(l).getComponent()).append(" (" + l.price() + ") ");
            Style style = Style.EMPTY().withClickEvent(runnable("/librget remove \"" + l.id() + "\" " + l.lvl()));
            text = text.append(new TranslatableRemoveButton().getComponent().withStyle(style));
        }
        sendMessage(text, false);
    }

    @Override
    public MutableComponent bookMainPage(Map<ConfigManager.Category, Integer> categories) {
        MutableComponent text = title();
        text = text.append(new TranslatableConfigMenuName().getComponent());
        text = text.append("\n");
        for (ConfigManager.Category category : ConfigManager.Category.values()) {
            text = text.append("\n* ");
            text = text.append(new TranslatableCategory(category).getComponent().withStyle(Style.EMPTY().withClickEvent(paging(categories.get(category) + 1))));
        }
        return text;
    }

    @Override
    public MutableComponent bookTitle(ConfigManager.Category category) {
        MutableComponent text = title();
        text = text.append(literal("↩").withStyle(Style.EMPTY().withClickEvent(paging(1))));
        text = text.append(literal(" "));
        return text.append(new TranslatableCategory(category).getComponent());
    }

    @Override
    public MutableComponent bookEntry(MutableComponent text, Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;
        MutableComponent name = new TranslatableConfigDescription(configurable).getComponent();

        ChatFormatting green = configurable.hasEffect() ? ChatFormatting.GREEN() : ChatFormatting.GRAY();
        ChatFormatting black = configurable.hasEffect() ? ChatFormatting.BLACK() : ChatFormatting.GRAY();
        ChatFormatting red = configurable.hasEffect() ? ChatFormatting.RED() : ChatFormatting.GRAY();

        MutableComponent c = literal(showName).withStyle(Style.EMPTY().withHoverEvent(hoverable(name)).withColor(black));
        text = text.append("\n\n").append(c);

        ClickEvent resetCommand;
        MutableComponent leftText, middleText, rightText;
        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();

            resetCommand = runnable("/librget config " + config + " " + configurable.getDefault().toString());
            leftText = literal("[" + value + "]").withStyle(Style.EMPTY().withClickEvent(runnable("/librget config " + config + " " + (!value))).withColor(value ? green : red));
            middleText = literal(" ");
            rightText = literal("");

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            Style minusStyle = configurable.inRange(value - 1) ? Style.EMPTY().withClickEvent(runnable("/librget config " + config + " " + (value - 1))).withColor(red) :
                               Style.EMPTY().withColor(ChatFormatting.GRAY());
            Style plusStyle = configurable.inRange(value + 1) ? Style.EMPTY().withClickEvent(runnable("/librget config " + config + " " + (value + 1))).withColor(green) :
                              Style.EMPTY().withColor(ChatFormatting.GRAY());

            resetCommand = runnable("/librget config " + config + " " + configurable.getDefault().toString());
            leftText = literal("[-]").withStyle(minusStyle);
            middleText = literal(" " + value + " ").withStyle(Style.EMPTY().withColor(black));
            rightText = literal("[+]").withStyle(plusStyle);

        } else if (configurable.type() == OptionsConfig.class) {
            OptionsConfig<?> value = (OptionsConfig<?>) configurable.get();
            Style optionValueStyle = Style.EMPTY().withClickEvent(runnable("/librget config " + config + " " + value.next().getName())).withColor(value.getName().equals("NONE") ? red : green);

            resetCommand = runnable("/librget config " + config + " " + ((OptionsConfig<?>) configurable.getDefault()).getName());
            leftText = literal("[" + value.getName() + "]").withStyle(optionValueStyle);
            middleText = literal("");
            rightText = literal("");

        } else {
            throw new UnexpectedConfigurableTypeException(configurable.type());
        }

        if (!configurable.isDefault()) {
            text = text.append(literal(" ")).append(literal("↩").withStyle(Style.EMPTY().withClickEvent(resetCommand).withColor(black)));
        }
        return text.append("\n").append(leftText).append(middleText).append(rightText);
    }

    @Override
    public MutableComponent literal(String message) {
        return Component.nullToEmpty(message).plainCopy();
    }

    @Override
    public MutableComponent translatable(String message, Object... args) {
        Object[] convertedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof Component componentWrapper) {
                arg = componentWrapper.unwrap();
            } else if (arg instanceof Message messageObj) {
                arg = messageObj.getComponent().unwrap();
            }
            convertedArgs[i] = arg;
        }
        return createTranslatable(message, convertedArgs);
    }

    protected MutableComponent createTranslatable(String key, Object... args) {
        return new TranslatableContents(key, args);
    }

    @Override
    public MutableComponent enchantmentTradeToComponent(EnchantmentTrade trade) {
        Identifier id = Identifier.tryParse(trade.id());
        if (id == null) {
            return literal(trade.id());
        }
        return literal(translateEnchantmentId(id) + (trade.lvl() == -1 ? "" : " " + trade.lvl()));
    }

    private String translateEnchantmentId(Identifier id) {
        Language lang = Language.getInstance();
        String fullLanguageKey = "enchantment." + id.getNamespace() + "." + id.getPath();
        return translateEnchantmentId(lang, fullLanguageKey);
    }

    protected String translateEnchantmentId(Language languageWrapper, String fullLanguageKey) {
        return languageWrapper.getOrDefault(fullLanguageKey);
    }

    protected ClickEvent runnable(String command) {
        return new ClickEvent(ClickEvent$Action.RUN_COMMAND(), command);
    }

    protected ClickEvent paging(int page) {
        String pageString = String.valueOf(page);
        return new ClickEvent(ClickEvent$Action.CHANGE_PAGE(), pageString);
    }

    protected HoverEvent hoverable(Component text) {
        return new HoverEvent(HoverEvent$Action.SHOW_TEXT(), text);
    }

    private MutableComponent title() {
        return literal("").append(literal("LibrGetter " + LibrGetter.getVersion() + "\n").withStyle(ChatFormatting.DARK_GREEN()));
    }
}

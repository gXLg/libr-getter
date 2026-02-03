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
import dev.gxlg.multiversion.gen.net.minecraft.ChatFormattingWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.MinecraftWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEvent$ActionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ClickEventWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.ComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEvent$ActionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.HoverEventWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.MutableComponentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.StyleWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.network.chat.contents.TranslatableContentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.IdentifierWrapper;

import java.util.List;
import java.util.Map;

public class Texts_1_17_0 extends Texts {
    protected void sendMessage(ComponentWrapper text, boolean actionbar) {
        MinecraftWrapper client = MinecraftWrapper.getInstance();
        LocalPlayerWrapper player = client.getPlayerField();
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
        MutableComponentWrapper text = new EnchantmentFoundMessage(enchant, counter).getComponent();
        text = text.withStyle(ChatFormattingWrapper.GREEN());

        if (!LibrGetter.config.removeGoal) {
            text = text.append(" ");
            StyleWrapper style = StyleWrapper.EMPTY().withClickEvent(runnable("/librget remove \"" + enchant.id() + "\" " + enchant.lvl()));
            text = text.append(new TranslatableRemoveButton().getComponent().withStyle(style));
        }
        sendMessage(text, false);
    }

    public void sendTradeLog(List<EnchantmentTrade> offeredEnchantments) {
        if (LibrGetter.config.logMode == LogMode.NONE) {
            return;
        }
        boolean ab = LibrGetter.config.logMode == LogMode.ACTIONBAR;
        MutableComponentWrapper text = new OfferMessage(offeredEnchantments).getComponent();
        sendMessage(text, ab);
    }

    @Override
    public void sendNewVersion(String releaseName, String description) {
        MutableComponentWrapper text = new NewVersionReleasedMessage(releaseName).getComponent();
        StyleWrapper style = StyleWrapper.EMPTY().withHoverEvent(hoverable(literal(description)));
        sendMessage(text.withStyle(style), false);
    }

    @Override
    public void sendListOfGoals() {
        MutableComponentWrapper text = new TranslatableGoalsListName().getComponent();
        for (EnchantmentTrade l : LibrGetter.config.goals) {
            text = text.append("\n- ").append(new TradeMessage(l).getComponent()).append(" (" + l.price() + ") ");
            StyleWrapper style = StyleWrapper.EMPTY().withClickEvent(runnable("/librget remove \"" + l.id() + "\" " + l.lvl()));
            text = text.append(new TranslatableRemoveButton().getComponent().withStyle(style));
        }
        sendMessage(text, false);
    }

    @Override
    public MutableComponentWrapper bookMainPage(Map<String, Integer> categories) {
        MutableComponentWrapper text = title();
        text = text.append(new TranslatableConfigMenuName().getComponent());
        text = text.append("\n");
        for (String cat : ConfigManager.CATEGORIES) {
            text = text.append("\n* ");
            text = text.append(new TranslatableCategory(cat).getComponent().withStyle(StyleWrapper.EMPTY().withClickEvent(paging(categories.get(cat) + 1))));
        }
        return text;
    }

    @Override
    public MutableComponentWrapper bookTitle(String category) {
        MutableComponentWrapper text = title();
        text = text.append(literal("↩").withStyle(StyleWrapper.EMPTY().withClickEvent(paging(1))));
        text = text.append(literal(" "));
        return text.append(new TranslatableCategory(category).getComponent());
    }

    @Override
    public MutableComponentWrapper bookEntry(MutableComponentWrapper text, Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;
        MutableComponentWrapper name = new TranslatableConfigDescription(configurable).getComponent();

        ChatFormattingWrapper green = configurable.hasEffect() ? ChatFormattingWrapper.GREEN() : ChatFormattingWrapper.GRAY();
        ChatFormattingWrapper black = configurable.hasEffect() ? ChatFormattingWrapper.BLACK() : ChatFormattingWrapper.GRAY();
        ChatFormattingWrapper red = configurable.hasEffect() ? ChatFormattingWrapper.RED() : ChatFormattingWrapper.GRAY();

        MutableComponentWrapper c = literal(showName).withStyle(StyleWrapper.EMPTY().withHoverEvent(hoverable(name)).withColor(black));
        text = text.append("\n\n").append(c);

        ClickEventWrapper resetCommand;
        MutableComponentWrapper leftText, middleText, rightText;
        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();

            resetCommand = runnable("/librget config " + config + " " + configurable.getDefault().toString());
            leftText = literal("[" + value + "]").withStyle(StyleWrapper.EMPTY().withClickEvent(runnable("/librget config " + config + " " + (!value))).withColor(value ? green : red));
            middleText = literal(" ");
            rightText = literal("");

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            StyleWrapper minusStyle = configurable.inRange(value - 1) ? StyleWrapper.EMPTY().withClickEvent(runnable("/librget config " + config + " " + (value - 1))).withColor(red) :
                                      StyleWrapper.EMPTY().withColor(ChatFormattingWrapper.GRAY());
            StyleWrapper plusStyle = configurable.inRange(value + 1) ? StyleWrapper.EMPTY().withClickEvent(runnable("/librget config " + config + " " + (value + 1))).withColor(green) :
                                     StyleWrapper.EMPTY().withColor(ChatFormattingWrapper.GRAY());

            resetCommand = runnable("/librget config " + config + " " + configurable.getDefault().toString());
            leftText = literal("[-]").withStyle(minusStyle);
            middleText = literal(" " + value + " ").withStyle(StyleWrapper.EMPTY().withColor(black));
            rightText = literal("[+]").withStyle(plusStyle);

        } else if (configurable.type() == OptionsConfig.class) {
            OptionsConfig<?> value = (OptionsConfig<?>) configurable.get();
            StyleWrapper optionValueStyle = StyleWrapper.EMPTY().withClickEvent(runnable("/librget config " + config + " " + value.next().getName()))
                                                        .withColor(value.getName().equals("NONE") ? red : green);

            resetCommand = runnable("/librget config " + config + " " + ((OptionsConfig<?>) configurable.getDefault()).getName());
            leftText = literal("[" + value.getName() + "]").withStyle(optionValueStyle);
            middleText = literal("");
            rightText = literal("");

        } else {
            throw new UnexpectedConfigurableTypeException(configurable.type());
        }

        if (!configurable.isDefault()) {
            text = text.append(literal(" ")).append(literal("↩").withStyle(StyleWrapper.EMPTY().withClickEvent(resetCommand).withColor(black)));
        }
        return text.append("\n").append(leftText).append(middleText).append(rightText);
    }

    @Override
    public MutableComponentWrapper literal(String message) {
        return ComponentWrapper.nullToEmpty(message).plainCopy();
    }

    @Override
    public MutableComponentWrapper translatable(String message, Object... args) {
        Object[] convertedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof ComponentWrapper componentWrapper) {
                arg = componentWrapper.unwrap();
            } else if (arg instanceof Message messageObj) {
                arg = messageObj.getComponent().unwrap();
            }
            convertedArgs[i] = arg;
        }
        return createTranslatable(message, convertedArgs);
    }

    protected MutableComponentWrapper createTranslatable(String key, Object... args) {
        return new TranslatableContentsWrapper(key, args);
    }

    @Override
    public MutableComponentWrapper enchantmentTradeToComponent(EnchantmentTrade trade) {
        IdentifierWrapper id = IdentifierWrapper.tryParse(trade.id());
        if (id == null) {
            return literal(trade.id());
        }
        return literal(translateEnchantmentId(id) + (trade.lvl() == -1 ? "" : " " + trade.lvl()));
    }

    private String translateEnchantmentId(IdentifierWrapper id) {
        LanguageWrapper lang = LanguageWrapper.getInstance();
        String fullLanguageKey = "enchantment." + id.getNamespace() + "." + id.getPath();
        return translateEnchantmentId(lang, fullLanguageKey);
    }

    protected String translateEnchantmentId(LanguageWrapper languageWrapper, String fullLanguageKey) {
        return languageWrapper.getOrDefault(fullLanguageKey);
    }

    protected ClickEventWrapper runnable(String command) {
        return new ClickEventWrapper(ClickEvent$ActionWrapper.RUN_COMMAND(), command);
    }

    protected ClickEventWrapper paging(int page) {
        String pageString = String.valueOf(page);
        return new ClickEventWrapper(ClickEvent$ActionWrapper.CHANGE_PAGE(), pageString);
    }

    protected HoverEventWrapper hoverable(ComponentWrapper text) {
        return new HoverEventWrapper(HoverEvent$ActionWrapper.SHOW_TEXT(), text);
    }

    private MutableComponentWrapper title() {
        return literal("").append(literal("LibrGetter " + LibrGetter.getVersion() + "\n").withStyle(ChatFormattingWrapper.DARK_GREEN()));
    }
}

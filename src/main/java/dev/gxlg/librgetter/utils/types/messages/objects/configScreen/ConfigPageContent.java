package dev.gxlg.librgetter.utils.types.messages.objects.configScreen;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.config.ConfigManager;
import dev.gxlg.librgetter.utils.types.config.OptionsConfig;
import dev.gxlg.librgetter.utils.types.config.helpers.Configurable;
import dev.gxlg.librgetter.utils.types.exceptions.runtime.UnexpectedConfigurableTypeException;
import dev.gxlg.librgetter.utils.types.messages.translatable.config.TranslatableCategory;
import dev.gxlg.librgetter.utils.types.messages.translatable.config.TranslatableConfigDescription;
import dev.gxlg.versiont.gen.net.minecraft.ChatFormatting;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Style;

import java.util.List;

public class ConfigPageContent extends PageContent {
    private final ConfigManager.Category category;

    private final List<Configurable<?>> configurables;

    public ConfigPageContent(ConfigManager.Category category, List<Configurable<?>> configurables) {
        this.category = category;
        this.configurables = configurables;
    }

    @Override
    protected MutableComponent buildComponent() {
        MutableComponent text = title();
        text = text.append(Texts.literal("↩").withStyle(Style.EMPTY().withClickEvent(Texts.paging(1))));
        text = text.append(Texts.literal(" "));
        text = text.append(new TranslatableCategory(category).getComponent());
        for (Configurable<?> configurable : configurables) {
            text = text.append("\n\n").append(buildEntry(configurable));
        }
        return text;
    }

    private MutableComponent buildEntry(Configurable<?> configurable) {
        String config = configurable.name();
        String showName = config.startsWith("_") ? "+ " + config.substring(1) : config;
        MutableComponent name = new TranslatableConfigDescription(configurable).getComponent();

        ChatFormatting green = configurable.hasEffect() ? ChatFormatting.GREEN() : ChatFormatting.GRAY();
        ChatFormatting black = configurable.hasEffect() ? ChatFormatting.BLACK() : ChatFormatting.GRAY();
        ChatFormatting red = configurable.hasEffect() ? ChatFormatting.RED() : ChatFormatting.GRAY();

        MutableComponent text = Texts.literal(showName).withStyle(Style.EMPTY().withHoverEvent(Texts.hoverable(name)).withColor(black));

        ClickEvent resetCommand;
        MutableComponent leftText, middleText, rightText;
        if (configurable.type() == Boolean.class) {
            boolean value = (boolean) configurable.get();

            resetCommand = Texts.runnable("/librget config " + config + " " + configurable.getDefault().toString());
            leftText = Texts.literal("[" + value + "]").withStyle(Style.EMPTY().withClickEvent(Texts.runnable("/librget config " + config + " " + (!value))).withColor(value ? green : red));
            middleText = Texts.literal(" ");
            rightText = Texts.literal("");

        } else if (configurable.type() == Integer.class) {
            int value = (int) configurable.get();
            Style minusStyle = configurable.inRange(value - 1) ? Style.EMPTY().withClickEvent(Texts.runnable("/librget config " + config + " " + (value - 1))).withColor(red) :
                               Style.EMPTY().withColor(ChatFormatting.GRAY());
            Style plusStyle = configurable.inRange(value + 1) ? Style.EMPTY().withClickEvent(Texts.runnable("/librget config " + config + " " + (value + 1))).withColor(green) :
                              Style.EMPTY().withColor(ChatFormatting.GRAY());

            resetCommand = Texts.runnable("/librget config " + config + " " + configurable.getDefault().toString());
            leftText = Texts.literal("[-]").withStyle(minusStyle);
            middleText = Texts.literal(" " + value + " ").withStyle(Style.EMPTY().withColor(black));
            rightText = Texts.literal("[+]").withStyle(plusStyle);

        } else if (configurable.type() == OptionsConfig.class) {
            OptionsConfig<?> value = (OptionsConfig<?>) configurable.get();
            Style optionValueStyle = Style.EMPTY().withClickEvent(Texts.runnable("/librget config " + config + " " + value.next().getName())).withColor(value.getName().equals("NONE") ? red : green);

            resetCommand = Texts.runnable("/librget config " + config + " " + ((OptionsConfig<?>) configurable.getDefault()).getName());
            leftText = Texts.literal("[" + value.getName() + "]").withStyle(optionValueStyle);
            middleText = Texts.literal("");
            rightText = Texts.literal("");

        } else {
            throw new UnexpectedConfigurableTypeException(configurable.type());
        }

        if (!configurable.isDefault()) {
            text = text.append(Texts.literal(" ")).append(Texts.literal("↩").withStyle(Style.EMPTY().withClickEvent(resetCommand).withColor(black)));
        }
        return text.append("\n").append(leftText).append(middleText).append(rightText);
    }
}

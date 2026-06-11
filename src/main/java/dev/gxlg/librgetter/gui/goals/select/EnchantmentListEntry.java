package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionListEntry;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.exceptions.common.InternalErrorException;
import dev.gxlg.librgetter.utils.messages.translatable.partial.TranslatablePartialMessage;
import dev.gxlg.librgetter.utils.messages.translatable.partial.gui.TranslatableNotTradeableLabel;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.NonNull;

public class EnchantmentListEntry extends CustomSelectionListEntry {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionListEntry.class, EnchantmentListEntry.class);

    public static final TranslatablePartialMessage NOT_TRADEABLE_LABEL = new TranslatableNotTradeableLabel();

    private final Enchantment enchantment;

    private final String idString;

    private final String translatedName;

    private final boolean tradeable;

    public EnchantmentListEntry(Font font, Enchantment enchantment) {
        super(font);
        this.enchantment = enchantment;
        Identifier id = Enchantments.enchantmentId(enchantment);
        this.idString = id.toString();
        this.translatedName = Texts.translateIdentifier(Texts.IdentifierType.ENCHANTMENT, id);
        boolean tradeable;
        try {
            tradeable = Enchantments.canBeTraded(enchantment);
        } catch (InternalErrorException ignored) {
            tradeable = true;
        }
        this.tradeable = tradeable;
    }

    @Override
    public @NonNull Component getNarration() {
        return Texts.literal(translatedName);
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
        int contentX = left + 7;
        int contentY = top + height / 2 - 4;
        int contentWidth = width - 14;

        Gui.extractText(poseStack, graphics, font, translatedName, contentX, contentY, -1);
        if (!tradeable) {
            Component text = NOT_TRADEABLE_LABEL.getComponent();
            Gui.extractText(poseStack, graphics, font, text, contentX + contentWidth - font.width(text), contentY, -1);
        }
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getIdString() {
        return idString;
    }

    public String getTranslatedName() {
        return translatedName;
    }
}

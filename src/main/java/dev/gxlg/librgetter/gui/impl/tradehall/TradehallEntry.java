package dev.gxlg.librgetter.gui.impl.tradehall;

import dev.gxlg.librgetter.gui.lib.widgets.list.CustomSelectionListEntry;
import dev.gxlg.librgetter.savefiles.tradehalls.WorkstationList;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.messages.objects.trades.TradeListMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

import java.util.List;

public class TradehallEntry extends CustomSelectionListEntry {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionListEntry.class, TradehallEntry.class);

    private final WorkstationList.Position position;

    private final List<EnchantmentTrade> trades;

    private final List<String> translatedTrades;

    protected TradehallEntry(Font font, WorkstationList.Position position, List<EnchantmentTrade> trades) {
        super(font);
        this.position = position;
        this.trades = trades;
        this.translatedTrades = trades.stream().map(t -> Texts.translateIdentifier(Texts.IdentifierType.ENCHANTMENT, Identifier.tryParse(t.id()))).toList();
    }

    public boolean filterMatches(String filter) {
        if (filter.isEmpty()) {
            return true;
        }
        String lowerFilter = filter.toLowerCase();
        for (EnchantmentTrade trade : trades) {
            if (trade.id().toLowerCase().contains(lowerFilter)) {
                return true;
            }
        }
        for (String translated : translatedTrades) {
            if (translated.toLowerCase().contains(lowerFilter)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
        int contentX = left + 7;
        int contentY = top + height / 2 - 4;
        int contentWidth = width - 14;

        Component leftText = new TradeListMessage(trades).getComponent();
        Component rightText = Texts.literal(position.toString());

        Gui.extractText(poseStack, graphics, font, leftText, contentX, contentY, -1);
        Gui.extractText(poseStack, graphics, font, rightText, contentX + contentWidth - font.width(rightText), contentY, -1);
    }

    @Override
    public Component getNarration() {
        return Texts.literal(position.toString());
    }

    public WorkstationList.Position getPosition() {
        return position;
    }
}

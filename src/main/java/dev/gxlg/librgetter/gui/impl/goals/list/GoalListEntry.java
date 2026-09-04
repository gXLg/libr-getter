package dev.gxlg.librgetter.gui.impl.goals.list;

import dev.gxlg.librgetter.gui.lib.widgets.WidgetDimensions;
import dev.gxlg.librgetter.gui.lib.widgets.list.CustomSelectionListEntry;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class GoalListEntry extends CustomSelectionListEntry {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionListEntry.class, GoalListEntry.class);

    private final EnchantmentTrade trade;

    private final Component component;

    private final String priceString;

    public GoalListEntry(Font font, EnchantmentTrade trade) {
        super(font);
        this.trade = trade;
        this.component = new TradeMessage(trade).getComponent();
        this.priceString = String.valueOf(trade.price());
    }

    @Override
    public @NonNull Component getNarration() {
        return component;
    }

    @Override
    public void render(PoseStack poseStack, GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
        WidgetDimensions content = getContentDimensions(top, left, width, height);

        Gui.extractText(poseStack, graphics, font, component, content.x(), content.y(), -1);
        Gui.extractText(poseStack, graphics, font, priceString, content.x() + content.width() - font.width(priceString), content.y(), -1);
    }

    public EnchantmentTrade getTrade() {
        return trade;
    }
}

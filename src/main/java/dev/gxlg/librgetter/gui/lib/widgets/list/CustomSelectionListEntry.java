package dev.gxlg.librgetter.gui.lib.widgets.list;

import dev.gxlg.librgetter.gui.lib.widgets.WidgetDimensions;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$EntryI;
import org.jspecify.annotations.NonNull;

public abstract class CustomSelectionListEntry extends ObjectSelectionList$Entry implements ObjectSelectionList$EntryI {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList$Entry.class, CustomSelectionListEntry.class);

    public static final int FONT_HEIGHT = 8;

    public static final int HORIZONTAL_PADDING = 7;

    protected final Font font;

    protected CustomSelectionListEntry(Font font) {
        this.font = font;
    }

    @Override
    public void extractContent(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        render(graphics, 0, getYField(), getXField(), getWidthField(), getHeightField(), mouseX, mouseY, hovered, a);
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
        render(null, graphics, index, top, left, width, height, mouseX, mouseY, isMouseOver, partialTick);
    }

    @Override
    public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
        render(poseStack, null, index, top, left, width, height, mouseX, mouseY, isMouseOver, partialTick);
    }

    public abstract void render(PoseStack poseStack, GuiGraphicsExtractor graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks);

    protected WidgetDimensions getContentDimensions(int top, int left, int width, int height) {
        return WidgetDimensions.from(left + HORIZONTAL_PADDING, top + height / 2 - FONT_HEIGHT / 2, width - HORIZONTAL_PADDING * 2, FONT_HEIGHT);
    }
}

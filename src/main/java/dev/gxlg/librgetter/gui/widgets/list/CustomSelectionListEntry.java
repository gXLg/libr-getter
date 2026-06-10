package dev.gxlg.librgetter.gui.widgets.list;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.ObjectSelectionList$EntryI;
import org.jspecify.annotations.NonNull;

public abstract class CustomSelectionListEntry extends ObjectSelectionList$Entry implements ObjectSelectionList$EntryI {
    public static final R.RClass clazz = R.extendWrapper(ObjectSelectionList$Entry.class, CustomSelectionListEntry.class);

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
}

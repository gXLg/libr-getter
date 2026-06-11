package dev.gxlg.librgetter.gui.widgets.unified.string;

import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractWidget;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.narration.NarrationElementOutput;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class LegacyStringWidget extends AbstractWidget implements UnifiedStringWidget {
    public static final R.RClass clazz = R.extendWrapper(AbstractWidget.class, LegacyStringWidget.class);

    private final Component text;

    private final Font font;

    public LegacyStringWidget(int x, int y, Component text, Font font) {
        super(x, y, font.width(text), font.getLineHeightField(), Texts.literal(""));
        this.text = text;
        this.font = font;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        font.draw(poseStack, text, getXField(), getYField(), -1);
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }
}

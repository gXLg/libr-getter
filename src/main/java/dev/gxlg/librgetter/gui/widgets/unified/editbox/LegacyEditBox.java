package dev.gxlg.librgetter.gui.widgets.unified.editbox;

import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.EditBox;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;

public class LegacyEditBox extends EditBox implements UnifiedEditBox {
    public static final R.RClass clazz = R.extendWrapper(EditBox.class, LegacyEditBox.class);

    private final Font font;

    private Component hint = null;

    public LegacyEditBox(Font font, int x, int y, int width, int height, Component narration) {
        super(font, x, y, width, height, narration);
        this.font = font;
    }

    @Override
    public void setHint(Component hint) {
        this.hint = hint;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        if (getValue().isEmpty() && hint != null) {
            font.draw(poseStack, hint, getXField() + 4, getYField() + (getHeightField() - font.getLineHeightField()) / 2f, 0xFFAAAAAA);
        }
    }
}

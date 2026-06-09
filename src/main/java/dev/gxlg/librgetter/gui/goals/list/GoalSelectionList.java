package dev.gxlg.librgetter.gui.goals.list;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList;
import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionListEntry;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.librgetter.utils.messages.objects.trades.TradeMessage;
import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.blaze3d.vertex.PoseStack;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.Font;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.GuiGraphicsExtractor;
import dev.gxlg.versiont.gen.net.minecraft.client.input.KeyEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class GoalSelectionList extends CustomSelectionList implements UnifiedGoalSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList.class, GoalSelectionList.class);

    private final GoalListScreen goalListScreen;

    public GoalSelectionList(GoalListScreen goalListScreen, int y, int w, int h) {
        super(goalListScreen.getMinecraftField(), w, h, y, 18);
        this.goalListScreen = goalListScreen;
    }

    private void removeSelectedGoal() {
        GoalEntry selectedEntry = (GoalEntry) getSelected();
        if (selectedEntry == null) {
            return;
        }
        if (entries.size() > 1) {
            int index = entries.indexOf(selectedEntry);
            GoalEntry nextEntry = (GoalEntry) entries.get(index == entries.size() - 1 ? index - 1 : index + 1);
            setSelected(nextEntry);
        }
        goalListScreen.getGoalListManager().removeGoal(selectedEntry.trade);
        entries.remove(selectedEntry);
        Gui.removeListEntry(this, selectedEntry);
        Gui.refreshScrollAmount(this);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_DELETE) {
            removeSelectedGoal();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_DELETE) {
            removeSelectedGoal();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void updateList() {
        clearEntries();
        entries.clear();
        Font font = goalListScreen.getFontField();
        for (EnchantmentTrade trade : goalListScreen.getGoalListManager().getGoals()) {
            GoalEntry entry = new GoalEntry(font, trade);
            this.addEntry(entry);
            entries.add(entry);
        }
    }

    public static class GoalEntry extends CustomSelectionListEntry {
        public static final R.RClass clazz = R.extendWrapper(CustomSelectionListEntry.class, GoalEntry.class);

        private final EnchantmentTrade trade;

        private final Component component;

        private final String priceString;

        public GoalEntry(Font font, EnchantmentTrade trade) {
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
            int contentX = left + 7;
            int contentY = top + height / 2 - 4;
            int contentWidth = width - 14;

            Gui.extractText(poseStack, graphics, font, component, contentX, contentY, -1);
            Gui.extractText(poseStack, graphics, font, priceString, contentX + contentWidth - font.width(priceString), contentY, -1);
        }

        public EnchantmentTrade getTrade() {
            return trade;
        }
    }
}

package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList_1_20_2;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class EnchantmentSelectionList_1_20_2 extends CustomSelectionList_1_20_2 implements UnifiedEnchantmentSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList_1_20_2.class, EnchantmentSelectionList_1_20_2.class);

    private final SelectEnchantmentScreen selectEnchantmentScreen;

    public EnchantmentSelectionList_1_20_2(SelectEnchantmentScreen selectEnchantmentScreen, int y, int w, int h) {
        super(selectEnchantmentScreen.getMinecraftField(), w, h, y, y + h, 18);
        this.selectEnchantmentScreen = selectEnchantmentScreen;
        init();
    }

    private void init() {
        for (Enchantment enchantment : Enchantments.getAllEnchantments()) {
            EnchantmentSelectionList.EnchantmentEntry entry = new EnchantmentSelectionList.EnchantmentEntry(selectEnchantmentScreen.getFontField(), enchantment);
            addEntry(entry);
            entries.add(entry);
        }
    }

    public void filterEntries(final String filter) {
        List<AbstractSelectionList$Entry> filtered = entries.stream().map(e -> (EnchantmentSelectionList.EnchantmentEntry) e)
                                                            .filter(e -> filter.isEmpty() || e.getTranslatedName().contains(filter) || e.getIdString().contains(filter))
                                                            .map(e -> (AbstractSelectionList$Entry) e).toList();
        replaceEntries(filtered);
        Gui.refreshScrollAmount(this);
    }
}

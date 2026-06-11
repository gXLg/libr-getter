package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList_1_20_3;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public class EnchantmentSelectionList_1_20_3 extends CustomSelectionList_1_20_3 implements UnifiedEnchantmentSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList_1_20_3.class, EnchantmentSelectionList_1_20_3.class);

    private final SelectEnchantmentScreen selectEnchantmentScreen;

    public EnchantmentSelectionList_1_20_3(SelectEnchantmentScreen selectEnchantmentScreen, int y, int w, int h) {
        super(selectEnchantmentScreen.getMinecraftField(), w, h, y, 18);
        this.selectEnchantmentScreen = selectEnchantmentScreen;
        init();
    }

    private void init() {
        for (Enchantment enchantment : Enchantments.getAllEnchantments()) {
            EnchantmentListEntry entry = new EnchantmentListEntry(selectEnchantmentScreen.getFontField(), enchantment);
            addEntry(entry);
            entries.add(entry);
        }
    }

    public void filterEntries(final String filter) {
        List<AbstractSelectionList$Entry> filtered = entries.stream().map(e -> (EnchantmentListEntry) e)
                                                            .filter(e -> filter.isEmpty() || e.getTranslatedName().contains(filter) || e.getIdString().contains(filter))
                                                            .map(e -> (AbstractSelectionList$Entry) e).toList();
        replaceEntries(filtered);
        Gui.refreshScrollAmount(this);
    }

}

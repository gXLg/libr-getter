package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionListInterface;
import dev.gxlg.librgetter.gui.widgets.unified.list.UnifiedList;
import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import dev.gxlg.librgetter.utils.chaining.gui.Gui;
import dev.gxlg.versiont.gen.net.minecraft.client.gui.components.AbstractSelectionList$Entry;
import dev.gxlg.versiont.gen.net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;

public interface UnifiedEnchantmentSelectionList extends CustomSelectionListInterface, UnifiedList {
    SelectEnchantmentScreen getselectEnchantmentScreen();

    default void init() {
        for (Enchantment enchantment : Enchantments.getAllEnchantments()) {
            EnchantmentListEntry entry = new EnchantmentListEntry(getselectEnchantmentScreen().getFontField(), enchantment);
            addEntry(entry);
            getCustomEntries().add(entry);
        }
    }

    default void filterEntries(String filter) {
        List<AbstractSelectionList$Entry> filtered = getCustomEntries().stream().map(e -> (EnchantmentListEntry) e)
                                                                       .filter(e -> filter.isEmpty() || e.getTranslatedName().contains(filter) || e.getIdString().contains(filter))
                                                                       .map(e -> (AbstractSelectionList$Entry) e).toList();
        replaceEntries(filtered);
        Gui.refreshScrollAmount(this);
    }
}

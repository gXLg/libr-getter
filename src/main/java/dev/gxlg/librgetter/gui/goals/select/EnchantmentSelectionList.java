package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList;
import dev.gxlg.versiont.api.R;

public class EnchantmentSelectionList extends CustomSelectionList implements UnifiedEnchantmentSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList.class, EnchantmentSelectionList.class);

    private final SelectEnchantmentScreen selectEnchantmentScreen;

    public EnchantmentSelectionList(SelectEnchantmentScreen selectEnchantmentScreen, int y, int w, int h) {
        super(selectEnchantmentScreen.getMinecraftField(), w, h, y, y + h, 18);
        this.selectEnchantmentScreen = selectEnchantmentScreen;
        init();
    }

    @Override
    public SelectEnchantmentScreen getSelectEnchantmentScreen() {
        return selectEnchantmentScreen;
    }
}

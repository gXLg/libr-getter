package dev.gxlg.librgetter.gui.goals.select;

import dev.gxlg.librgetter.gui.widgets.list.CustomSelectionList_1_20_3;
import dev.gxlg.versiont.api.R;

public class EnchantmentSelectionList_1_20_3 extends CustomSelectionList_1_20_3 implements UnifiedEnchantmentSelectionList {
    public static final R.RClass clazz = R.extendWrapper(CustomSelectionList_1_20_3.class, EnchantmentSelectionList_1_20_3.class);

    private final SelectEnchantmentScreen selectEnchantmentScreen;

    public EnchantmentSelectionList_1_20_3(SelectEnchantmentScreen selectEnchantmentScreen, int y, int w, int h) {
        super(selectEnchantmentScreen.getMinecraftField(), w, h, y, 18);
        this.selectEnchantmentScreen = selectEnchantmentScreen;
        init();
    }

    @Override
    public SelectEnchantmentScreen getSelectEnchantmentScreen() {
        return selectEnchantmentScreen;
    }
}

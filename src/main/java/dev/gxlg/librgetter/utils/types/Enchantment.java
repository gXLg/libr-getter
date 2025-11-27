package dev.gxlg.librgetter.utils.types;

import dev.gxlg.librgetter.Reflection;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;

public record Enchantment(String id, int lvl, int price) {
    public static final Enchantment EMPTY = new Enchantment("", -1, 0);

    public boolean meets(Enchantment e) {
        return e.id.equals(id) && e.lvl == lvl && e.price <= price;
    }

    public boolean same(Enchantment e) {
        return e.id.equals(id) && e.lvl == lvl;
    }

    @Override
    public String toString() {
        Identifier iid = Identifier.tryParse(id);
        if (iid == null) return id + " " + lvl;
        Language lang = Language.getInstance();
        String full = "enchantment." + iid.getNamespace() + "." + iid.getPath();
        String name = (String) (
                Reflection.version("< 1.19.4") ?
                        Reflection.wrapn("Language:lang method_4679/get String:full", lang, full) :
                        Reflection.wrapn("Language:lang method_48307/get String:full", lang, full)
        );
        return name + " " + lvl;
    }
}

package dev.gxlg.librgetter.utils.types;

import dev.gxlg.librgetter.Reflection;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("ClassCanBeRecord") // GSON can't handle records in earlier versions
public class EnchantmentTrade {

    private final String id;
    private final int lvl;
    private final int price;

    public EnchantmentTrade(String id, int lvl, int price) {
        this.id = id;
        this.lvl = lvl;
        this.price = price;
    }

    public static final EnchantmentTrade EMPTY = new EnchantmentTrade("", -1, 0);

    public boolean meets(EnchantmentTrade e) {
        return e.id.equals(id) && e.lvl == lvl && e.price <= price;
    }

    public boolean same(EnchantmentTrade e) {
        return e.id.equals(id) && e.lvl == lvl;
    }

    @Override
    public @NonNull String toString() {
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

    public String id() {
        return id;
    }

    public int lvl() {
        return lvl;
    }

    public int price() {
        return price;
    }
}

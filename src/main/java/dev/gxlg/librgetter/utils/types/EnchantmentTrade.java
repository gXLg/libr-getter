package dev.gxlg.librgetter.utils.types;

import dev.gxlg.librgetter.utils.reflection.MinecraftHelper;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ClassCanBeRecord") // GSON can't handle records in earlier versions
public class EnchantmentTrade {

    public static final EnchantmentTrade EMPTY = new EnchantmentTrade("", -1, 0);

    private final String id;

    private final int lvl;

    private final int price;

    public EnchantmentTrade(String id, int lvl, int price) {
        this.id = id;
        this.lvl = lvl;
        this.price = price;
    }

    public boolean meets(EnchantmentTrade e) {
        return e.id.equals(id) && e.lvl == lvl && e.price <= price;
    }

    public boolean same(EnchantmentTrade e) {
        return e.id.equals(id) && e.lvl == lvl;
    }

    @Override
    public @NotNull String toString() {
        return MinecraftHelper.translateEnchantmentId(id) + " " + lvl;
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

    public static class EnchantmentOnly {

        private final String id;

        private final int lvl;

        public EnchantmentOnly(String id, int lvl) {
            this.id = id;
            this.lvl = lvl;
        }

        public String id() {
            return id;
        }

        public int lvl() {
            return lvl;
        }

        public EnchantmentTrade tradeWithPrice(int price) {
            return new EnchantmentTrade(id, lvl, price);
        }
    }
}

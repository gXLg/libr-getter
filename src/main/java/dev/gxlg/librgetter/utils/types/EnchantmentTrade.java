package dev.gxlg.librgetter.utils.types;

import dev.gxlg.librgetter.utils.chaining.enchantments.Enchantments;
import org.jetbrains.annotations.NotNull;

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

    public boolean meets(EnchantmentTrade e) {
        return e.id.equals(id) && e.lvl == lvl && e.price <= price;
    }

    public boolean same(EnchantmentTrade e) {
        return e.id.equals(id) && e.lvl == lvl;
    }

    @Override
    public @NotNull String toString() {
        return Enchantments.getImpl().translateEnchantmentId(id) + " " + lvl;
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

    public record EnchantmentOnly(String id, int lvl) {
        public EnchantmentTrade tradeWithPrice(int price) {
            return new EnchantmentTrade(id, lvl, price);
        }
    }
}

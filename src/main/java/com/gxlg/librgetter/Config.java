package com.gxlg.librgetter;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public boolean notify = false;
    public boolean autoTool = true;
    public List<Enchantment> goals = new ArrayList<>();
    public boolean actionBar = false;
    public boolean lock = false;
    public boolean removeGoal = false;

    public boolean checkUpdate = true;
    public boolean warning = true;

    public static class Enchantment {
        final public String id;
        final public int lvl;
        public int price;

        public Enchantment(String id, int lvl, int price) {
            this.id = id;
            this.lvl = lvl;
            this.price = price;
        }

        public boolean meets(Enchantment e) {
            return e.id.equals(id) && e.lvl == lvl && e.price <= price;
        }

        public boolean same(Enchantment e) {
            return e.id.equals(id) && e.lvl == lvl;
        }

        @Override
        public String toString() {
            return id + " " + lvl;
        }
    }
}
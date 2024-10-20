package com.gxlg.librgetter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("CanBeFinal")
public class Config {
    public boolean notify = false;
    public boolean autoTool = true;
    public boolean actionBar = false;
    public boolean lock = false;
    public boolean removeGoal = false;

    public boolean checkUpdate = true;
    public boolean warning = true;
    public boolean offhand = false;
    public boolean manual = false;
    public boolean waitLose = false;

    public List<Enchantment> goals = new ArrayList<>();

    public static class Enchantment {
        public static final Enchantment EMPTY = new Enchantment("", -1, 0);
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

    private static List<String> booleans = null;

    public static List<String> listBooleans() {
        if (booleans != null) return booleans;
        booleans = new ArrayList<>();
        for (Field f : Config.class.getFields()) {
            if (f.getType() == boolean.class) booleans.add(f.getName());
        }
        Collections.sort(booleans);
        return booleans;
    }

    public static boolean getBoolean(String name) {
        try {
            Field f = Config.class.getField(name);
            return f.getBoolean(LibrGetter.config);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setBoolean(String name, boolean value) {
        try {
            Field f = Config.class.getField(name);
            f.setBoolean(LibrGetter.config, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
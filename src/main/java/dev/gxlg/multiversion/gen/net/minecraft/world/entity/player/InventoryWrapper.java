package dev.gxlg.multiversion.gen.net.minecraft.world.entity.player;

import dev.gxlg.multiversion.R;

public class InventoryWrapper extends R.RWrapper<InventoryWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1661/net.minecraft.world.entity.player.Inventory");

    private final R.RField selected;

    protected InventoryWrapper(Object instance) {
        super(clazz.inst(instance));
        this.selected = this.instance.fld("field_7545/selected");
    }

    public void setSelectedHotbarSlot(int slot){
        this.instance.mthd("method_61496/setSelectedHotbarSlot", int.class).invk(slot);
    }

    public int getSelected() {
        return (int) this.selected.get();
    }
    
    public void setSelected(int value) {
        this.selected.set(value);
    }

    public static InventoryWrapper inst(Object instance) {
        return new InventoryWrapper(instance);
    }
}
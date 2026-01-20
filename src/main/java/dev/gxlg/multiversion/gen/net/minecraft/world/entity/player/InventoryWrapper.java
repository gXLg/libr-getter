package dev.gxlg.multiversion.gen.net.minecraft.world.entity.player;

import dev.gxlg.multiversion.R;

public class InventoryWrapper extends R.RWrapper<InventoryWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1661/net.minecraft.world.entity.player.Inventory");

    private final R.RField selected;

    protected InventoryWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
        this.selected = rInstance.fld("field_7545/selected");
    }

    public void setSelectedSlot(int slot){
        clazz.inst(this.instance).mthd("method_61496/setSelectedSlot", int.class).invk(slot);
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
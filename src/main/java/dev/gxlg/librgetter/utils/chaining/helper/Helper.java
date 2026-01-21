package dev.gxlg.librgetter.utils.chaining.helper;

import dev.gxlg.multiversion.V;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class Helper {

    public abstract Connection getConnection(ClientPacketListener handler);

    public abstract ClientLevel getWorld(LocalPlayer player);

    public abstract void interactBlock(MultiPlayerGameMode manager, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand);

    public abstract void playFoundNotification(LocalPlayer player);

    public abstract void setActionResultFail(CallbackInfoReturnable<InteractionResult> info);

    public abstract void setSelectedSlot(Inventory inventory, int slot);

    private static Helper implementation = null;

    public static Helper getImpl() {
        if (implementation != null) {
            return implementation;
        }
        if (V.lower("1.19.0")) {
            implementation = new Helper_1_17_0();
        } else if (V.lower("1.21.5")) {
            implementation = new Helper_1_19_0();
        } else if (V.equal("1.21.9")) {
            implementation = new Helper_1_21_5();
        } else {
            implementation = new Helper_1_21_9();
        }
        return implementation;
    }
}

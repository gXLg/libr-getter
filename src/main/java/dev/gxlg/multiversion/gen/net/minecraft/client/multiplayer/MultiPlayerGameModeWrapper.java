package dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer;

import dev.gxlg.multiversion.R;

public class MultiPlayerGameModeWrapper extends R.RWrapper<MultiPlayerGameModeWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_636/net.minecraft.client.multiplayer.MultiPlayerGameMode");

    protected MultiPlayerGameModeWrapper(Object instance) {
        super(instance);
        R.RInstance rInstance = clazz.inst(instance);
    }

    public net.minecraft.world.InteractionResult useItemOn(net.minecraft.client.player.LocalPlayer player, net.minecraft.world.InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit){
        return (net.minecraft.world.InteractionResult) clazz.inst(this.instance).mthd("method_2896/useItemOn", net.minecraft.client.player.LocalPlayer.class, net.minecraft.world.InteractionHand.class, net.minecraft.world.phys.BlockHitResult.class).invk(player, hand, hit);
    }

    public net.minecraft.world.InteractionResult useItemOn(net.minecraft.client.player.LocalPlayer player, net.minecraft.client.multiplayer.ClientLevel clientWorld, net.minecraft.world.InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit){
        return (net.minecraft.world.InteractionResult) clazz.inst(this.instance).mthd("method_2896/useItemOn", net.minecraft.client.player.LocalPlayer.class, net.minecraft.client.multiplayer.ClientLevel.class, net.minecraft.world.InteractionHand.class, net.minecraft.world.phys.BlockHitResult.class).invk(player, clientWorld, hand, hit);
    }

    public static MultiPlayerGameModeWrapper inst(Object instance) {
        return new MultiPlayerGameModeWrapper(instance);
    }
}
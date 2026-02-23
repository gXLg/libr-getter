package dev.gxlg.librgetter.worker.types.context;

import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;

@SuppressWarnings("UnusedReturnValue")
public class TaskContextBuilder {
    private BlockPos selectedLecternPos = null;

    private ItemStack defaultItem = null;

    private Villager selectedVillager = null;

    private int attemptsCounter = 0;

    private TradeOfferData tradeOfferData = null;

    private MinecraftData minecraftData = null;

    public TaskContextBuilder reset() {
        this.selectedLecternPos = null;
        this.defaultItem = null;
        this.selectedVillager = null;
        this.attemptsCounter = 0;
        this.tradeOfferData = null;
        this.minecraftData = null;
        return this;
    }

    public TaskContextBuilder setLecternPos(BlockPos pos) {
        this.selectedLecternPos = pos;
        return this;
    }

    public TaskContextBuilder setDefaultItem(ItemStack item) {
        this.defaultItem = item;
        return this;
    }

    public TaskContextBuilder setVillager(Villager villager) {
        this.selectedVillager = villager;
        return this;
    }

    public TaskContextBuilder resetAttemptsCounter() {
        this.attemptsCounter = 0;
        return this;
    }

    public TaskContextBuilder increaseAttemptsCounter() {
        this.attemptsCounter++;
        return this;
    }

    public TaskContextBuilder setTradeOfferData(TradeOfferData data) {
        this.tradeOfferData = data;
        return this;
    }

    public TaskContextBuilder setMinecraftData(MinecraftData data) {
        this.minecraftData = data;
        return this;
    }

    public TaskContext build() {
        return new TaskContext(selectedLecternPos, defaultItem, selectedVillager, attemptsCounter, tradeOfferData, minecraftData);
    }
}

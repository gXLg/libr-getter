package dev.gxlg.librgetter.worker.types.context;

import dev.gxlg.librgetter.utils.types.TradeOfferData;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.item.ItemStack;

public record TaskContext(BlockPos selectedLecternPos, ItemStack defaultItem, Villager selectedVillager, int attemptsCounter, TradeOfferData tradeOfferData, MinecraftData minecraftData) { }

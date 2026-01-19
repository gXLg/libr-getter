package dev.gxlg.librgetter.utils.reflection;

import dev.gxlg.librgetter.LibrGetter;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.common.InternalErrorException;
import dev.gxlg.multiversion.V;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientPacketListenerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.MultiPlayerGameModeWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.client.player.LocalPlayerWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryAccessWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.RegistryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.BuiltInRegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.registries.RegistriesWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.locale.LanguageWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.resources.ResourceKeyWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.tags.EnchantmentTagsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.InteractionResultWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.entity.player.InventoryWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.ItemStackWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentHelperWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.EnchantmentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.item.enchantment.ItemEnchantmentsWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.LevelWrapper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.locale.Language;
import net.minecraft.network.Connection;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MinecraftHelper {

    // TODO: split into multiple helper classes?
    // done: parser methods

    public static Connection getConnection(ClientPacketListener handler) {
        return ClientPacketListenerWrapper.inst(handler).getConnection();
    }

    public static ClientLevel getWorld(LocalPlayer player) {
        LocalPlayerWrapper playerWrapper = LocalPlayerWrapper.inst(player);
        if (!V.lower("1.21.9")) {
            return playerWrapper.level();
        } else if (!V.lower("1.19")) {
            return playerWrapper.getLevel();
        } else {
            return playerWrapper.getClientLevel();
        }
    }

    public static void interactBlock(MultiPlayerGameMode manager, LocalPlayer player, BlockHitResult lowBlock, boolean useMainHand) {
        InteractionHand hand = useMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        MultiPlayerGameModeWrapper managerWrapper = MultiPlayerGameModeWrapper.inst(manager);
        if (!V.lower("1.19")) {
            managerWrapper.useItemOn(player, hand, lowBlock);
        } else {
            managerWrapper.useItemOn(player, getWorld(player), hand, lowBlock);
        }
    }

    public static Identifier enchantmentId(Enchantment enchantment) {
        if (!V.lower("1.21")) {
            ClientLevel world = Minecraft.getInstance().level;
            if (world == null) {
                return null;
            }
            return RegistryAccessWrapper.inst(world.registryAccess()).lookupOrThrow(RegistriesWrapper.ENCHANTMENT()).getKey(enchantment);

        } else if (!V.lower("1.19.3")) {
            return BuiltInRegistriesWrapper.ENCHANTMENT().getKey(enchantment);
        } else {
            return RegistryWrapper.ENCHANTMENT().getKey(enchantment);
        }
    }

    public static int getEfficiencyLevel(ItemStack stack) {
        if (!V.lower("1.21")) {
            ResourceKeyWrapper efficiency = EnchantmentsWrapper.EFFICIENCY2();
            ItemEnchantmentsWrapper enchantments = ItemStackWrapper.inst(stack).getEnchantments();
            return enchantments.entrySet().stream().filter(e -> e.getKey().is(efficiency)).findFirst().map(Object2IntMap.Entry::getIntValue).orElse(0);
        } else {
            return EnchantmentHelperWrapper.getItemEnchantmentLevel(EnchantmentsWrapper.EFFICIENCY(), stack);
        }
    }

    public static boolean canBeTraded(Enchantment enchantment) throws InternalErrorException {
        if (!V.lower("1.21")) {
            ClientLevel world = Minecraft.getInstance().level;
            if (world == null) {
                throw new InternalErrorException("world");
            }
            RegistryWrapper registry = RegistryAccessWrapper.inst(world.registryAccess()).lookupOrThrow(RegistriesWrapper.ENCHANTMENT());
            return registry.wrapAsHolder(enchantment).is(EnchantmentTagsWrapper.TRADEABLE());
        } else {
            return EnchantmentWrapper.inst(enchantment).isTradeable();
        }
    }

    public static void playFoundNotification(ClientLevel world, LocalPlayer player) {
        if (!LibrGetter.config.notify) {
            return;
        }
        LevelWrapper.inst(world).playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 10F, 0.7F, false);
    }

    public static void setActionResultFail(CallbackInfoReturnable<InteractionResult> info) {
        info.setReturnValue(InteractionResultWrapper.FAIL());
    }

    public static boolean isVillagerLibrarian(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        if (!V.lower("1.21.5")) {
            ResourceKeyWrapper librarianProfession = VillagerProfessionWrapper.LIBRARIAN2();
            return VillagerDataWrapper.inst(villagerData).profession().is(librarianProfession);

        } else {
            VillagerProfessionWrapper librarianProfession = VillagerProfessionWrapper.LIBRARIAN();
            return VillagerDataWrapper.inst(villagerData).getProfession().equals(librarianProfession);
        }
    }

    public static boolean isVillagerUnemployed(Villager villager) {
        VillagerData villagerData = villager.getVillagerData();
        if (!V.lower("1.21.5")) {
            ResourceKeyWrapper noneProfession = VillagerProfessionWrapper.NONE2();
            return VillagerDataWrapper.inst(villagerData).profession().is(noneProfession);
        } else {
            VillagerProfessionWrapper noneProfession = VillagerProfessionWrapper.NONE();
            return VillagerDataWrapper.inst(villagerData).getProfession().equals(noneProfession);
        }
    }

    public static void setSelectedSlot(Inventory inventory, int slot) {
        if (!V.lower("1.21.5")) {
            InventoryWrapper.inst(inventory).setSelectedHotbarSlot(slot);
        } else {
            InventoryWrapper.inst(inventory).setSelected(slot);
        }
    }

    public static String translateEnchantmentId(String stringId) {
        Identifier id = Identifier.tryParse(stringId);
        if (id == null) {
            return stringId;
        }
        return translateEnchantmentId(id);
    }

    public static String translateEnchantmentId(Identifier id) {
        Language lang = Language.getInstance();
        String fullLanguageKey = "enchantment." + id.getNamespace() + "." + id.getPath();
        if (!V.lower("1.19.4")) {
            return LanguageWrapper.inst(lang).get2(fullLanguageKey);
        } else {
            return LanguageWrapper.inst(lang).get(fullLanguageKey);
        }
    }
}

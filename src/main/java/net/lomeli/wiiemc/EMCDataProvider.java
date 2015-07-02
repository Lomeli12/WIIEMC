package net.lomeli.wiiemc;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.wiiemc.config.ModConfig;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.api.knowledge.TransmutationKnowledgeRegistryProxy;

public class EMCDataProvider implements IWailaDataProvider {
    private static DecimalFormat energyValueDecimalFormat = new DecimalFormat("###,###,###,###,###.###");

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        int meta = accessor.getMetadata();
        if (block != null) {
            ItemStack stack = new ItemStack(block, 1, meta);
            EntityPlayer player = accessor.getPlayer();
            boolean isKnown = playerKnowsItem(player, stack);
            boolean canBeLearned = canBeLearned(player, stack);
            if (ModConfig.showEMC) {
                EnergyValue value = getItemEnergyValue(stack);
                if (value != null && value.getValue() > 0f) {
                    String tip = StatCollector.translateToLocal(ModLang.ENERGY_VALUE);
                    String energy = energyValueDecimalFormat.format(value.getValue());
                    tooltip.add(String.format(tip, energy));
                } else {
                    if (ModConfig.showNoEMC)
                        tooltip.add(StatCollector.translateToLocal(ModLang.NO_ENERGY));
                }
            }

            if (ModConfig.showIsLearned) {
                if (canBeLearned) {
                    if (ModConfig.showIsLearned)
                        tooltip.add(StatCollector.translateToLocal(ModLang.UNKNOWN));
                } else {
                    if (isKnown && ModConfig.showIsLearned)
                        tooltip.add(StatCollector.translateToLocal(ModLang.IS_KNOWN));
                    else if (ModConfig.showCantLearn)
                        tooltip.add(StatCollector.translateToLocal(ModLang.NOT_LEARNABLE));
                }
            }
        }
        return tooltip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return null;
    }

    public boolean playerKnowsItem(EntityPlayer player, ItemStack stack) {
        return (player != null && stack != null && stack.getItem() != null) ? TransmutationKnowledgeRegistryProxy.doesPlayerKnow(player, stack) : false;
    }

    public EnergyValue getItemEnergyValue(ItemStack stack) {
        EnergyValue value = null;
        if (stack != null && stack.getItem() != null && EnergyValueRegistryProxy.hasEnergyValue(stack))
            value = EnergyValueRegistryProxy.getEnergyValue(stack);
        return value;
    }

    public boolean canBeLearned(EntityPlayer player, ItemStack stack) {
        if (player != null && stack != null && stack.getItem() != null && EnergyValueRegistryProxy.hasEnergyValue(stack))
            return TransmutationKnowledgeRegistryProxy.canPlayerLearn(player, stack);
        return false;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new EMCDataProvider(), Block.class);
    }
}

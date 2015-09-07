package net.lomeli.wiiemc.providers.ee3;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.wiiemc.ModLang;
import net.lomeli.wiiemc.WIIEMC;
import net.lomeli.wiiemc.config.ModConfig;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;

public class BlockEMCDataProvider implements IWailaDataProvider {
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
        if (!ModConfig.ee3Block)
            return tooltip;
        Block block = accessor.getBlock();
        int meta = accessor.getMetadata();
        MovingObjectPosition pos = accessor.getPosition();
        if (block != null) {
            ItemStack stack = new ItemStack(block, 1, meta);
            boolean isKnown = WIIEMC.proxy.doesPlayerKnow(stack);
            boolean canBeLearned = WIIEMC.proxy.canPlayerLearn(stack);
            EnergyValue value = EnergyValueRegistryProxy.getEnergyValue(stack);

            if (value == null) {
                Item item;
                if (block == Blocks.redstone_wire)
                    item = Items.redstone;
                else if (block instanceof BlockBush)
                    item = block.getItem(accessor.getWorld(), pos.blockX, pos.blockY, pos.blockZ);
                else if (block == Blocks.wooden_door)
                    item = Items.wooden_door;
                else if (block == Blocks.iron_door)
                    item = Items.iron_door;
                else
                    item = Item.getItemFromBlock(block);

                if (item != null) {
                    stack = new ItemStack(item);
                    value = EnergyValueRegistryProxy.getEnergyValue(stack);
                    isKnown = WIIEMC.proxy.doesPlayerKnow(stack);
                    canBeLearned = WIIEMC.proxy.canPlayerLearn(stack);
                }
            }
            if (value != null && value.getValue() > 0f) {
                if (ModConfig.showEMC) {
                    String tip = StatCollector.translateToLocal(ModLang.ENERGY_VALUE_EE3);
                    String energy = energyValueDecimalFormat.format(value.getValue());
                    tooltip.add(String.format(tip, energy));
                }
            } else {
                if (ModConfig.showNoEMC)
                    tooltip.add(StatCollector.translateToLocal(ModLang.NO_ENERGY_EE3));
            }

            if (canBeLearned && value != null) {
                if (ModConfig.showCanLearn)
                    tooltip.add(StatCollector.translateToLocal(ModLang.UNKNOWN));
            } else {
                if (isKnown && ModConfig.showIsLearned)
                    tooltip.add(StatCollector.translateToLocal(ModLang.IS_KNOWN));
                else if (ModConfig.showCantLearn)
                    tooltip.add(StatCollector.translateToLocal(ModLang.NOT_LEARNABLE));
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
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new BlockEMCDataProvider(), Block.class);
    }
}

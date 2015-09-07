package net.lomeli.wiiemc.providers.projecte;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import moze_intel.projecte.api.ProjectEAPI;

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
import net.lomeli.wiiemc.config.ModConfig;

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
        if (!ModConfig.projectEBlock)
            return tooltip;
        Block block = accessor.getBlock();
        int meta = accessor.getMetadata();
        MovingObjectPosition pos = accessor.getPosition();
        if (block != null) {
            int emc = getEMCValue(block, meta, accessor.getWorld(), pos.blockX, pos.blockY, pos.blockZ);
            if (emc > 0) {
                if (ModConfig.showEMC) {
                    String tip = StatCollector.translateToLocal(ModLang.ENERGY_VALUE_PE);
                    String energy = energyValueDecimalFormat.format(emc);
                    tooltip.add(String.format(tip, energy));
                }
            } else {
                if (ModConfig.showNoEMC)
                    tooltip.add(StatCollector.translateToLocal(ModLang.NO_ENERGY_PE));
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

    private int getEMCValue(Block block, int meta, World world, int blockX, int blockY, int blockZ) {
        int emc;
        ItemStack stack = new ItemStack(block, 1, meta);
        emc = ProjectEAPI.getEMCProxy().getValue(stack);
        if (emc <= 0) {
            Item item;
            if (block == Blocks.redstone_wire)
                item = Items.redstone;
            else if (block instanceof BlockBush)
                item = block.getItem(world, blockX, blockY, blockZ);
            else if (block == Blocks.wooden_door)
                item = Items.wooden_door;
            else if (block == Blocks.iron_door)
                item = Items.iron_door;
            else
                item = Item.getItemFromBlock(block);

            if (item != null)
                emc = ProjectEAPI.getEMCProxy().getValue(item);
        }
        return emc;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new BlockEMCDataProvider(), Block.class);
    }
}

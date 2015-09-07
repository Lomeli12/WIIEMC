package net.lomeli.wiiemc.providers.projecte;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import moze_intel.projecte.api.tile.IEmcStorage;
import moze_intel.projecte.api.tile.ITileEmc;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;

import net.lomeli.wiiemc.ModLang;
import net.lomeli.wiiemc.config.ModConfig;

public class TileEMCDataProvider implements IWailaDataProvider {
    private static DecimalFormat energyValueDecimalFormat = new DecimalFormat("###,###,###,###,###.###");
    private ModContainer container;

    public TileEMCDataProvider() {
        container = FMLCommonHandler.instance().findContainerFor("ProjectE");
    }

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
        if (!ModConfig.projectETile)
            return tooltip;
        TileEntity tile = accessor.getTileEntity();
        int type = isEMCStorage(tile);
        if (type != 0) {
            double emc, maxEmc;
            switch (type) {
                case 1:
                    emc = ((ITileEmc) tile).getStoredEmc();
                    if (ModConfig.showTileEMC) {
                        String text = StatCollector.translateToLocal(ModLang.STORED_ENERGY_PE);
                        String emcText = energyValueDecimalFormat.format(emc);
                        tooltip.add(String.format(text, emcText));
                    }
                    break;
                case 2:
                    emc = ((IEmcStorage) tile).getStoredEmc();
                    maxEmc = ((IEmcStorage) tile).getMaximumEmc();
                    if (ModConfig.showTileEMC) {
                        String text = StatCollector.translateToLocal(ModLang.STORED_ENERGY_PE);
                        String emcText = energyValueDecimalFormat.format(emc) + "/" + energyValueDecimalFormat.format(maxEmc);
                        tooltip.add(String.format(text, emcText));
                    }
                    break;
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

    private int isEMCStorage(TileEntity tile) {
        int type = 0;
        if (container != null) {
            if (container.getVersion().equals("1.7.10-PE1.8.0") && tile instanceof ITileEmc)
                return 1;
            else {
                if (tile instanceof ITileEmc)
                    return 1;
                else if (tile instanceof IEmcStorage)
                    return 2;
            }
        }
        return type;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new TileEMCDataProvider(), TileEntity.class);
    }
}

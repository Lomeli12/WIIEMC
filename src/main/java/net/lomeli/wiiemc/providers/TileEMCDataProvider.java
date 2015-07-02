package net.lomeli.wiiemc.providers;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.common.Loader;

import net.lomeli.simplecondenser.lib.enums.RedstoneState;
import net.lomeli.simplecondenser.tile.TileCondenserBase;
import net.lomeli.wiiemc.ModLang;
import net.lomeli.wiiemc.config.ModConfig;
import net.lomeli.wiiemc.core.helper.ObfUtil;

import com.pahimar.ee3.api.exchange.EnergyValue;

public class TileEMCDataProvider implements IWailaDataProvider {
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
        TileEntity tile = accessor.getTileEntity();
        if (tile != null) {
            if (isTransmutationTablet(tile) && ModConfig.showTabletEMC) {
                EnergyValue value = getTabletAvailableEnergyValue(tile);
                if (value != null) {
                    String text = StatCollector.translateToLocal(ModLang.AVAILABLE_ENERGY);
                    String emc = energyValueDecimalFormat.format(value.getValue());
                    tooltip.add(String.format(text, emc));
                }
            } else if (Loader.isModLoaded("simplecondenser") && tile instanceof TileCondenserBase) {
                TileCondenserBase condenser = (TileCondenserBase) tile;
                EnergyValue value = condenser.getStoredEnergyValue();
                if (ModConfig.showCondenserEMC && value != null) {
                    String text = StatCollector.translateToLocal(ModLang.STORED_ENERGY);
                    String emc = energyValueDecimalFormat.format(value.getValue());
                    tooltip.add(String.format(text, emc));
                }
                if (ModConfig.showCondenserRate) {
                    String text = StatCollector.translateToLocal(ModLang.CONDENSER_RATE);
                    tooltip.add(String.format(text, condenser.getType().getSpeed()));
                }
                if (ModConfig.showCondenserRedstoneState) {
                    String text = StatCollector.translateToLocal(ModLang.CONDENSER_REDSTONE_IGNORE);
                    RedstoneState state = condenser.getRedstoneState();
                    if (state == RedstoneState.HIGH)
                        text = StatCollector.translateToLocal(ModLang.CONDENSER_REDSTONE_ON);
                    else if (state == RedstoneState.LOW)
                        text = StatCollector.translateToLocal(ModLang.CONDENSER_REDSTONE_OFF);
                    tooltip.add(text);
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
        return tag;
    }

    public boolean isTransmutationTablet(TileEntity tile) {
        try {
            Class<?> clazz = Class.forName("com.pahimar.ee3.tileentity.TileEntityTransmutationTablet");
            return clazz.isInstance(tile);
        } catch (Exception e) {
        }
        return false;
    }

    public EnergyValue getTabletAvailableEnergyValue(Object obj) {
        try {
            Class<?> clazz = Class.forName("com.pahimar.ee3.tileentity.TileEntityTransmutationTablet");
            EnergyValue value = ObfUtil.getFieldValue(clazz, obj, "availableEnergyValue");
            return value != null ? value : new EnergyValue(0);
        } catch (Exception e) {
        }
        return new EnergyValue(0);
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        try {
            Class<?> tabletClass = Class.forName("com.pahimar.ee3.tileentity.TileEntityTransmutationTablet");
            if (tabletClass != null)
                registrar.registerBodyProvider(new TileEMCDataProvider(), tabletClass);
            if (Loader.isModLoaded("simplecondenser")) {
                Class<?> condenserClass = Class.forName("net.lomeli.simplecondenser.tile.TileCondenserBase");
                if (condenserClass != null)
                    registrar.registerBodyProvider(new TileEMCDataProvider(), condenserClass);
            }
        } catch (Exception e) {
        }
    }
}

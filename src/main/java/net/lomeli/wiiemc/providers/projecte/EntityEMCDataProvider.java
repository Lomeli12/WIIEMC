package net.lomeli.wiiemc.providers.projecte;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import moze_intel.projecte.api.ProjectEAPI;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.wiiemc.ModLang;
import net.lomeli.wiiemc.config.ModConfig;

public class EntityEMCDataProvider implements IWailaEntityProvider {
    private static DecimalFormat energyValueDecimalFormat = new DecimalFormat("###,###,###,###,###.###");

    @Override
    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public List<String> getWailaBody(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        if (!ModConfig.projectEEntity)
            return tooltip;
        if (entity != null) {
            ItemStack entityItem = null, storedItem = null;
            if (entity instanceof EntityPainting)
                entityItem = new ItemStack(Items.painting);
            else if (entity instanceof EntityItemFrame) {
                entityItem = new ItemStack(Items.item_frame);
                if (ModConfig.showItemFrameEMC)
                    storedItem = ((EntityItemFrame) entity).getDisplayedItem();
            } else if (entity instanceof EntityMinecart) {
                if (entity instanceof EntityMinecartChest)
                    entityItem = new ItemStack(Items.chest_minecart);
                else if (entity instanceof EntityMinecartFurnace)
                    entityItem = new ItemStack(Items.furnace_minecart);
                else if (entity instanceof EntityMinecartTNT)
                    entityItem = new ItemStack(Items.tnt_minecart);
                else if (entity instanceof EntityMinecartHopper)
                    entityItem = new ItemStack(Items.hopper_minecart);
                else if (entity instanceof EntityMinecartEmpty)
                    entityItem = new ItemStack(Items.minecart);
            } else if (entity instanceof EntityBoat)
                entityItem = new ItemStack(Items.boat);

            if (entityItem != null) {
                int emc = ProjectEAPI.getEMCProxy().getValue(entityItem);
                if (ModConfig.showEMC) {
                    String tip = StatCollector.translateToLocal(ModLang.ENERGY_VALUE_PE);
                    String energy = energyValueDecimalFormat.format(emc);
                    tooltip.add(String.format(tip, energy));
                }
                if (storedItem != null) {
                    emc = ProjectEAPI.getEMCProxy().getValue(storedItem);
                    if (emc > 0) {
                        String tip = StatCollector.translateToLocal(ModLang.ITEMFRAME_ITEM_ENERGY_PE);
                        String energy = energyValueDecimalFormat.format(emc);
                        tooltip.add(String.format(tip, energy));
                    }
                }
            }
        }
        return tooltip;
    }

    @Override
    public List<String> getWailaTail(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new EntityEMCDataProvider(), Entity.class);
    }
}

package net.lomeli.wiiemc.providers;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.wiiemc.ModLang;
import net.lomeli.wiiemc.config.ModConfig;

import com.pahimar.ee3.api.exchange.EnergyValue;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.api.knowledge.TransmutationKnowledgeRegistryProxy;

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
        EntityPlayer player = accessor.getPlayer();
        if (entity != null) {
            EnergyValue entityValue = null;
            EnergyValue itemEnergy = null;
            ItemStack entityItem = null;
            if (entity instanceof EntityPainting)
                entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.painting));
            else if (entity instanceof EntityItemFrame) {
                entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.item_frame));
                if (ModConfig.showItemFrameEMC) {
                    ItemStack stack = ((EntityItemFrame) entity).getDisplayedItem();
                    if (stack != null && stack.getItem() != null && EnergyValueRegistryProxy.hasEnergyValue(stack))
                        itemEnergy = EnergyValueRegistryProxy.getEnergyValue(stack);
                }
            } else if (entity instanceof EntityMinecart) {
                if (entity instanceof EntityMinecartChest)
                    entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.chest_minecart));
                else if (entity instanceof EntityMinecartFurnace)
                    entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.furnace_minecart));
                else if (entity instanceof EntityMinecartTNT)
                    entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.tnt_minecart));
                else if (entity instanceof EntityMinecartHopper)
                    entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.hopper_minecart));
                else if (entity instanceof EntityMinecartEmpty)
                    entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.minecart));
            } else if (entity instanceof EntityBoat)
                entityValue = EnergyValueRegistryProxy.getEnergyValue(entityItem = new ItemStack(Items.boat));

            if (entityItem != null) {
                boolean isKnown = TransmutationKnowledgeRegistryProxy.doesPlayerKnow(player, entityItem);
                boolean canBeLearned = TransmutationKnowledgeRegistryProxy.canPlayerLearn(player, entityItem);
                if (entityValue != null && entityValue.getValue() > 0f) {
                    if (ModConfig.showEMC) {
                        String tip = StatCollector.translateToLocal(ModLang.ENERGY_VALUE);
                        String energy = energyValueDecimalFormat.format(entityValue.getValue());
                        tooltip.add(String.format(tip, energy));
                    }
                    if (itemEnergy != null && ModConfig.showItemFrameEMC) {
                        String itemTip = StatCollector.translateToLocal(ModLang.ITEMFRAME_ITEM_ENERGY);
                        String itemValue = energyValueDecimalFormat.format(itemEnergy.getValue());
                        tooltip.add(String.format(itemTip, itemValue));
                    }
                } else {
                    if (ModConfig.showNoEMC)
                        tooltip.add(StatCollector.translateToLocal(ModLang.NO_ENERGY));
                }

                if (canBeLearned) {
                    if (ModConfig.showCanLearn)
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
    public List<String> getWailaTail(Entity entity, List<String> tooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new EntityEMCDataProvider(), Entity.class);
        /*registrar.registerBodyProvider(new EMCDataProvider(), EntityPainting.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityItemFrame.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityMinecart.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityMinecartChest.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityMinecartFurnace.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityMinecartTNT.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityMinecartHopper.class);
        registrar.registerBodyProvider(new EMCDataProvider(), EntityBoat.class);*/
    }
}
package net.lomeli.wiiemc.core.handler;

import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.wiiemc.WIIEMC;
import net.lomeli.wiiemc.core.network.MessageKnowledge;

import com.pahimar.ee3.api.event.PlayerKnowledgeEvent;
import com.pahimar.ee3.api.knowledge.TransmutationKnowledgeRegistryProxy;

public class EventHandler {
    @SubscribeEvent
    public void playerJoinsWorld(EntityJoinWorldEvent event) {
        if (!event.world.isRemote && event.entity != null && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player != null) {
                EntityPlayerMP mp = (EntityPlayerMP) FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.dimension).func_152378_a(player.getUniqueID());
                if (mp != null) {
                    WIIEMC.packetHandler.sendTo(new MessageKnowledge(TransmutationKnowledgeRegistryProxy.getPlayerKnownTransmutations(player)), mp);
                }
            }
        }
    }

    @SubscribeEvent
    public void playerLearnItem(PlayerKnowledgeEvent.PlayerLearnKnowledgeEvent event) {
        if (event.playerUUID != null) {
            EntityPlayerMP player = getPlayerByUUID(event.playerUUID);
            if (player != null) {
                Set<ItemStack> knownItems = TransmutationKnowledgeRegistryProxy.getPlayerKnownTransmutations(player);
                if (event.itemStack != null)
                    knownItems.add(event.itemStack);
                WIIEMC.packetHandler.sendTo(new MessageKnowledge(knownItems), player);
            }
        }
    }

    @SubscribeEvent
    public void playerForgotItem(PlayerKnowledgeEvent.PlayerForgetKnowledgeEvent event) {
        if (event.playerUUID != null) {
            EntityPlayerMP player = getPlayerByUUID(event.playerUUID);
            if (player != null) {
                Set<ItemStack> knownItems = TransmutationKnowledgeRegistryProxy.getPlayerKnownTransmutations(player);
                if (event.itemStack != null)
                    knownItems.remove(event.itemStack);
                WIIEMC.packetHandler.sendTo(new MessageKnowledge(knownItems), player);
            }
        }
    }

    @SubscribeEvent
    public void playerForgotEverything(PlayerKnowledgeEvent.PlayerForgetAllKnowledgeEvent event) {
        if (event.playerUUID != null) {
            EntityPlayerMP player = getPlayerByUUID(event.playerUUID);
            if (player != null)
                WIIEMC.packetHandler.sendTo(new MessageKnowledge(), player);
        }
    }

    private EntityPlayerMP getPlayerByUUID(UUID uuid) {
        if (uuid != null) {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                Integer[] ids = DimensionManager.getIDs();
                for (int i = 0; i < ids.length; i++) {
                    EntityPlayerMP player = (EntityPlayerMP) server.worldServerForDimension(ids[i]).func_152378_a(uuid);
                    if (player != null)
                        return player;
                }
            }
        }
        return null;
    }
}

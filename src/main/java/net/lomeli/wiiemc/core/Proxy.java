package net.lomeli.wiiemc.core;

import java.util.Collection;

import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

import net.lomeli.wiiemc.WIIEMC;
import net.lomeli.wiiemc.config.ModConfig;
import net.lomeli.wiiemc.core.handler.EventHandler;
import net.lomeli.wiiemc.core.helper.KnowledgeHelper;
import net.lomeli.wiiemc.core.helper.Logger;
import net.lomeli.wiiemc.core.network.MessageKnowledge;
import net.lomeli.wiiemc.version.VersionChecker;

public class Proxy {

    public void preInit() {
        WIIEMC.config.loadConfig();
        WIIEMC.versionChecker = new VersionChecker(WIIEMC.UPDATE, WIIEMC.MOD_ID.toUpperCase(), WIIEMC.MAJOR, WIIEMC.MINOR, WIIEMC.REV);
        if (ModConfig.checkForUpdates)
            WIIEMC.versionChecker.checkForUpdates();

        WIIEMC.packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel(WIIEMC.MOD_ID.toLowerCase());
        WIIEMC.packetHandler.registerMessage(MessageKnowledge.class, MessageKnowledge.class, 0, Side.CLIENT);
    }

    public void init() {
        WIIEMC.config.initEvent();

        Logger.logInfo("Beware the flower pots...they will confuse you and ambush you...");
        if (Loader.isModLoaded("EE3")) {
            FMLInterModComms.sendMessage("Waila", "register", "net.lomeli.wiiemc.providers.ee3.BlockEMCDataProvider.callbackRegister");
            FMLInterModComms.sendMessage("Waila", "register", "net.lomeli.wiiemc.providers.ee3.EntityEMCDataProvider.callbackRegister");
            FMLInterModComms.sendMessage("Waila", "register", "net.lomeli.wiiemc.providers.ee3.TileEMCDataProvider.callbackRegister");
            MinecraftForge.EVENT_BUS.register(new EventHandler());
        }
        if (Loader.isModLoaded("ProjectE")) {
            FMLInterModComms.sendMessage("Waila", "register", "net.lomeli.wiiemc.providers.projecte.BlockEMCDataProvider.callbackRegister");
            FMLInterModComms.sendMessage("Waila", "register", "net.lomeli.wiiemc.providers.projecte.EntityEMCDataProvider.callbackRegister");
            FMLInterModComms.sendMessage("Waila", "register", "net.lomeli.wiiemc.providers.projecte.TileEMCDataProvider.callbackRegister");
        }
        if (Loader.isModLoaded("simplecondenser"))
            Logger.logInfo("Aww...I feel all special now...");

        if (Loader.isModLoaded("EE3") && Loader.isModLoaded("ProjectE"))
            Logger.logError("Greedy little fella, aren't ya?");
    }

    public boolean doesPlayerKnow(ItemStack stack) {
        return false;
    }

    public boolean canPlayerLearn(ItemStack stack) {
        return false;
    }

    public void setKnowledge(Collection<ItemStack> itemStacks) {
    }

    public KnowledgeHelper getKnowledgeHelper() {
        return null;
    }
}

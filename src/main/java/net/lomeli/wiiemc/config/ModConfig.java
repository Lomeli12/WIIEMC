package net.lomeli.wiiemc.config;

import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.wiiemc.ModLang;
import net.lomeli.wiiemc.WIIEMC;

public class ModConfig {
    public static boolean showEMC = true;
    public static boolean showNoEMC = true;
    public static boolean showIsLearned = true;
    public static boolean showCantLearn = true;

    private Configuration config;

    public ModConfig(Configuration config) {
        this.config = config;
    }

    public void loadConfig() {
        String cat = Configuration.CATEGORY_GENERAL;
        showEMC = config.getBoolean("showEMC", cat, true, StatCollector.translateToLocal(ModLang.SHOW_EMC));
        showNoEMC = config.getBoolean("showNoEMC", cat, true, StatCollector.translateToLocal(ModLang.SHOW_NO_EMC));
        showIsLearned = config.getBoolean("showCanLearn", cat, true, StatCollector.translateToLocal(ModLang.SHOW_CAN_LEARN));
        showCantLearn = config.getBoolean("showCantLearn", cat, true, StatCollector.translateToLocal(ModLang.SHOW_CAN_NOT_LEARN));

        if (config.hasChanged())
            config.save();
    }

    public void initEvent() {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            FMLCommonHandler.instance().bus().register(this);
    }

    public Configuration getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equalsIgnoreCase(WIIEMC.MOD_ID))
            loadConfig();
    }
}

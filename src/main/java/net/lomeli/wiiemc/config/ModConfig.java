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
    public static boolean showNoEMC = false;
    public static boolean showIsLearned = false;
    public static boolean showCanLearn = true;
    public static boolean showCantLearn = false;
    public static boolean showTabletEMC = true;
    public static boolean showCondenserEMC = true;
    public static boolean showCondenserRate = true;
    public static boolean showCondenserRedstoneState = true;
    public static boolean showItemFrameEMC = true;
    public static boolean checkForUpdates = true;

    private Configuration config;

    public ModConfig(Configuration config) {
        this.config = config;
    }

    public void loadConfig() {
        String cat = Configuration.CATEGORY_GENERAL;
        showEMC = config.getBoolean("showEMC", cat, true, StatCollector.translateToLocal(ModLang.SHOW_EMC));
        showNoEMC = config.getBoolean("showNoEMC", cat, false, StatCollector.translateToLocal(ModLang.SHOW_NO_EMC));
        showIsLearned = config.getBoolean("showLearned", cat, false, StatCollector.translateToLocal(ModLang.SHOW_LEARNED));
        showCanLearn = config.getBoolean("showCanLearn", cat, true, StatCollector.translateToLocal(ModLang.SHOW_CAN_LEARN));
        showCantLearn = config.getBoolean("showCantLearn", cat, false, StatCollector.translateToLocal(ModLang.SHOW_CAN_NOT_LEARN));
        showItemFrameEMC = config.getBoolean("showItemFrameEMC", cat, true, StatCollector.translateToLocal(ModLang.SHOW_ITEMFRAME_EMC));
        showTabletEMC = config.getBoolean("showTabletEMC", cat, true, StatCollector.translateToLocal(ModLang.SHOW_TABLET_EMC));
        showCondenserEMC = config.getBoolean("showCondenserEMC", cat, true, StatCollector.translateToLocal(ModLang.SHOW_CONDENSER_EMC));
        showCondenserRate = config.getBoolean("showCondenserRate", cat, true, StatCollector.translateToLocal(ModLang.SHOW_CONDENSER_RATE));
        showCondenserRedstoneState = config.getBoolean("showCondenserRedstoneState", cat, true, StatCollector.translateToLocal(ModLang.SHOW_CONDENSER_REDSTONE_STATE));
        checkForUpdates = config.getBoolean("checkForUpdates", cat, true, StatCollector.translateToLocal(ModLang.CHECK_FOR_UPDATES));

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

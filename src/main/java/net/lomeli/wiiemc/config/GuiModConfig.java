package net.lomeli.wiiemc.config;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.config.GuiConfig;

import net.lomeli.wiiemc.WIIEMC;

public class GuiModConfig extends GuiConfig {
    public GuiModConfig(GuiScreen parent) {
        super(parent, new ConfigElement(WIIEMC.config.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), WIIEMC.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(WIIEMC.config.getConfig().toString()));
    }
}

package net.lomeli.wiiemc;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import net.lomeli.wiiemc.config.ModConfig;
import net.lomeli.wiiemc.core.Proxy;
import net.lomeli.wiiemc.version.VersionChecker;

@Mod(modid = WIIEMC.MOD_ID, name = WIIEMC.MOD_NAME, version = WIIEMC.VERSION, dependencies = WIIEMC.DEPENDENCIES, guiFactory = WIIEMC.FACTORY, acceptedMinecraftVersions = WIIEMC.MINECRAFT_VERSION)
public class WIIEMC {
    public static final String MOD_ID = "wiiemc";
    public static final String MOD_NAME = "What Is Its EMC?";
    public static final int MAJOR = 1, MINOR = 1, REV = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REV;
    public static final String DEPENDENCIES = "required-after:Waila;required-after:EE3;after:simplecondenser;after:VersionChecker";
    public static final String FACTORY = "net.lomeli.wiiemc.config.ConfigFactory";
    public static final String UPDATE = "https://raw.githubusercontent.com/Lomeli12/WIIEMC/master/update.json";
    public static final String MINECRAFT_VERSION = "1.7.10";

    @SidedProxy(serverSide = "net.lomeli.wiiemc.core.Proxy", clientSide = "net.lomeli.wiiemc.core.ClientProxy")
    public static Proxy proxy;

    public static SimpleNetworkWrapper packetHandler;

    public static ModConfig config;
    public static VersionChecker versionChecker;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new ModConfig(new Configuration(event.getSuggestedConfigurationFile()));
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
}

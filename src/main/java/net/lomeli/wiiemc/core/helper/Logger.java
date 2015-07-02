package net.lomeli.wiiemc.core.helper;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

import net.lomeli.wiiemc.WIIEMC;

public class Logger {

    public static void log(Level logLevel, Object message) {
        FMLLog.log(WIIEMC.MOD_ID.toUpperCase(), logLevel, String.valueOf(message));
    }

    public static void logWarning(Object message) {
        log(Level.WARN, message);
    }

    public static void logInfo(Object message) {
        log(Level.INFO, message);
    }

    public static void logError(Object message) {
        log(Level.ERROR, message);
    }
}

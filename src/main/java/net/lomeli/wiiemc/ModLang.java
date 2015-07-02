package net.lomeli.wiiemc;

public class ModLang {
    // ToolTips
    private static final String TOOLTIP_BASE = "tooltip.wiiemc.";

    // Energy
    private static final String ENERGY_BASE = TOOLTIP_BASE + "energy.";
    public static final String ENERGY_VALUE = ENERGY_BASE + "value";
    public static final String NO_ENERGY = ENERGY_BASE + "none";
    public static final String STORED_ENERGY = ENERGY_BASE + "storedemc";
    public static final String AVAILABLE_ENERGY = ENERGY_BASE + "availableemc";
    public static final String ITEMFRAME_ITEM_ENERGY = ENERGY_BASE + "storeditememc";

    // Knowledge
    private static final String KNOWLEDGE_BASE = TOOLTIP_BASE + "knowledge.";
    public static final String IS_KNOWN = KNOWLEDGE_BASE + "isknown";
    public static final String UNKNOWN = KNOWLEDGE_BASE + "isunknown";
    public static final String NOT_LEARNABLE = KNOWLEDGE_BASE + "cantlearn";
    
    // Condenser
    private static final String CONDENSER_BASE = TOOLTIP_BASE + "condenser.";
    public static final String CONDENSER_RATE = CONDENSER_BASE + "tickrate";
    public static final String CONDENSER_REDSTONE_ON = CONDENSER_BASE + "redstone.on";
    public static final String CONDENSER_REDSTONE_OFF = CONDENSER_BASE + "redstone.off";
    public static final String CONDENSER_REDSTONE_IGNORE = CONDENSER_BASE + "redstone.ignore";

    // Configs
    private static final String CONFIG_BASE = "config.wiiemc.";
    public static final String SHOW_EMC = CONFIG_BASE + "showemc";
    public static final String SHOW_NO_EMC = CONFIG_BASE + "shownoemc";
    public static final String SHOW_LEARNED = CONFIG_BASE + "showlearned";
    public static final String SHOW_CAN_LEARN = CONFIG_BASE + "showcanlearn";
    public static final String SHOW_CAN_NOT_LEARN = CONFIG_BASE + "showcannotlearn";
    public static final String SHOW_TABLET_EMC = CONFIG_BASE + "showtabletemc";
    public static final String SHOW_CONDENSER_EMC = CONFIG_BASE + "showcondenseremc";
    public static final String SHOW_CONDENSER_RATE = CONFIG_BASE + "showcondenserrate";
    public static final String SHOW_CONDENSER_REDSTONE_STATE = CONFIG_BASE + "showcondenserredstone";
    public static final String SHOW_ITEMFRAME_EMC = CONFIG_BASE + "showitemframeemc";
}

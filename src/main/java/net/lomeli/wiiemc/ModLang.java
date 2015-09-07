package net.lomeli.wiiemc;

public class ModLang {
    // ToolTips
    private static final String TOOLTIP_BASE = "tooltip.wiiemc.";

    // --------------------- EE3 ---------------------
    // Energy
    private static final String ENERGY_BASE_EE3 = TOOLTIP_BASE + "ee3.energy.";
    public static final String ENERGY_VALUE_EE3 = ENERGY_BASE_EE3 + "value";
    public static final String NO_ENERGY_EE3 = ENERGY_BASE_EE3 + "none";
    public static final String STORED_ENERGY_EE3 = ENERGY_BASE_EE3 + "storedemc";
    public static final String AVAILABLE_ENERGY_EE3 = ENERGY_BASE_EE3 + "availableemc";
    public static final String ITEMFRAME_ITEM_ENERGY_EE3 = ENERGY_BASE_EE3 + "storeditememc";

    // Knowledge
    private static final String KNOWLEDGE_BASE = TOOLTIP_BASE + "ee3.knowledge.";
    public static final String IS_KNOWN = KNOWLEDGE_BASE + "isknown";
    public static final String UNKNOWN = KNOWLEDGE_BASE + "isunknown";
    public static final String NOT_LEARNABLE = KNOWLEDGE_BASE + "cantlearn";

    // --------------------- ProjectE ---------------------
    // Energy
    private static final String ENERGY_BASE_PE = TOOLTIP_BASE + "projecte.energy.";
    public static final String ENERGY_VALUE_PE = ENERGY_BASE_PE + "value";
    public static final String NO_ENERGY_PE = ENERGY_BASE_PE + "none";
    public static final String STORED_ENERGY_PE = ENERGY_BASE_PE + "storedemc";
    public static final String ITEMFRAME_ITEM_ENERGY_PE = ENERGY_BASE_PE + "storeditememc";

    // --------------------- Simple Condensers ---------------------
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
    public static final String SHOW_TILE_EMC = CONFIG_BASE + "showtileemc";
    public static final String CHECK_FOR_UPDATES = CONFIG_BASE + "checkforupdates";
    public static final String EE3_BLOCKS = CONFIG_BASE + "ee3.block";
    public static final String EE3_TILES = CONFIG_BASE + "ee3.tile";
    public static final String EE3_ENTITIES = CONFIG_BASE + "ee3.entity";
    public static final String PE_BLOCKS = CONFIG_BASE + "projecte.block";
    public static final String PE_TILES = CONFIG_BASE + "projecte.tile";
    public static final String PE_ENTITIES = CONFIG_BASE + "projecte.entity";
}

package net.lomeli.wiiemc;

public class ModLang {
    // ToolTips
    private static final String TOOLTIP_BASE = "tooltip.wiiemc.";

    // Energy
    private static final String ENERGY_BASE = TOOLTIP_BASE + "energy.";
    public static final String ENERGY_VALUE = ENERGY_BASE + "value";
    public static final String NO_ENERGY = ENERGY_BASE + "none";

    // Knowledge
    private static final String KNOWLEDGE_BASE = TOOLTIP_BASE + "knowledge.";
    public static final String IS_KNOWN = KNOWLEDGE_BASE + "isknown";
    public static final String UNKNOWN = KNOWLEDGE_BASE + "isunknown";
    public static final String NOT_LEARNABLE = KNOWLEDGE_BASE + "cantlearn";

    // Configs
    private static final String CONFIG_BASE = "config.wiiemc.";
    public static final String SHOW_EMC = CONFIG_BASE + "showemc";
    public static final String SHOW_NO_EMC = CONFIG_BASE + "shownoemc";
    public static final String SHOW_CAN_LEARN = CONFIG_BASE + "showcanlearn";
    public static final String SHOW_CAN_NOT_LEARN = CONFIG_BASE + "showcannotlearn";
}

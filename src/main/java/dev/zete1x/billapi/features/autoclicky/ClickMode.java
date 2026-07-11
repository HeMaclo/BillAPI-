package dev.zete1x.billapi.features.autoclicky;

/**
 * Режимы клика для AutoClicky
 * 1.8 - как в 1.8 версии (килкаем быстро)
 * 1.9+ - с задержками между кликами (как в новых версиях)
 */
public enum ClickMode {
    MODE_1_8("1.8 PvP", true),
    MODE_1_9_PLUS("1.9+ PvP", false);

    private final String displayName;
    private final boolean is18;

    ClickMode(String displayName, boolean is18) {
        this.displayName = displayName;
        this.is18 = is18;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean is18Mode() {
        return is18;
    }

    public static ClickMode fromBoolean(boolean is18) {
        return is18 ? MODE_1_8 : MODE_1_9_PLUS;
    }
}

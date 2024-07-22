package net.pulsir.api;

public class LunarAPI {

    private static LunarPluginAPI plugin;

    public static LunarPluginAPI getPlugin() {
        return plugin;
    }

    public static void setPlugin(LunarPluginAPI plugin) {
        if (LunarAPI.plugin != null) {
            throw new UnsupportedOperationException("Failed to initialize plugin. Plugin is already initialized.");
        }

        LunarAPI.plugin = plugin;
    }
}

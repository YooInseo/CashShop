package me.github.freejia;

import me.github.freejia.data.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static final Logger Log = Bukkit.getLogger();
    public static Main plugin;

    public static ConfigManager config;
    public static ConfigManager Cash ;
    public void onEnable() {
        super.onEnable();
        config = new ConfigManager("config");
        plugin = this;
    }
}

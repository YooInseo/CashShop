package me.github.freejia;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static final Logger Log = Bukkit.getLogger();

    public void onEnable() {
        super.onEnable();

        Log.info("플러그인이 켜졌다옹");

        getLogger().info("TEST");
    }
}

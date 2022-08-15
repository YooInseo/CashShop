package me.github.freejia;

import me.github.freejia.cmd.cash;
import me.github.freejia.cmd.cashshop;
import me.github.freejia.data.Cash;
import me.github.freejia.data.CashShop;
import me.github.freejia.data.ConfigManager;
import me.github.freejia.data.SQL.MySql;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static final Logger Log = Bukkit.getLogger();
    public static Main plugin;

    public static ConfigManager config;

    public static ConfigManager database;
    public static ConfigManager Cash ;


    public void onEnable() {
        super.onEnable();

        config = new ConfigManager("config");
        database = new ConfigManager("database");
        plugin = this;
        MySql mysql = new MySql();
        mysql.Connect();
        new cash(this);
        new cashshop(this);

        ConfigurationSerialization.registerClass(Cash.class);
        ConfigurationSerialization.registerClass(CashShop.class);
        init();
    }

    public void init(){
        database.getConfig().options().copyDefaults();

        database.getConfig().addDefault("Mysql.enabled",false);
        database.getConfig().addDefault("Mysql.type","HeidiSQL");
        database.getConfig().addDefault("Mysql.mysql.host","localhost");
        database.getConfig().addDefault("Mysql.mysql.port",3306);
        database.getConfig().addDefault("Mysql.mysql.database","minecraft");
        database.getConfig().addDefault("Mysql.mysql.username","admin");
        database.getConfig().addDefault("Mysql.mysql.password","");


        database.getConfig().options().copyDefaults(true);
        database.saveConfig();

        config.getConfig().options().copyDefaults();

        config.getConfig().addDefault("cash_message.check","");
        config.getConfig().addDefault("cash_message.send","");
        config.getConfig().addDefault("cash_message.remove","");
        config.getConfig().addDefault("cash_message.initalization","");
        config.getConfig().addDefault("cash_message.set","");

        config.getConfig().options().copyDefaults(true);
        config.saveConfig();
    }
}

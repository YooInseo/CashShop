package me.github.freejia;

import me.github.freejia.cmd.cash;
import me.github.freejia.cmd.cashshop;
import me.github.freejia.data.Cash;
import me.github.freejia.data.CashShop;
import me.github.freejia.data.ConfigManager;
import me.github.freejia.data.SQL.MySql;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
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

        config.getConfig().addDefault("shop_message.1_buy","");
        config.getConfig().addDefault("shop_message.1_sell","");
        config.getConfig().addDefault("shop_message.64_buy","");
        config.getConfig().addDefault("cash_message.64_sell","");

        config.getConfig().addDefault("shop_price.gui","구매가격/판매가격");
        config.getConfig().addDefault("shop_price.gui_size",27);
        config.getConfig().addDefault("shop_price.item","LIME_WOOL");
        config.getConfig().addDefault("shop_price.slot",13);
        config.getConfig().addDefault("shop_price.lore",Arrays.asList("","&f[&b!&f] 클릭시 구매가격을 설정합니다!",""));

        config.getConfig().options().copyDefaults(true);
        config.saveConfig();
    }
}

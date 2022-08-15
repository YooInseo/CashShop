package me.github.freejia;

import me.github.freejia.cmd.cash;
import me.github.freejia.cmd.cashshop;
import me.github.freejia.data.Cash;
import me.github.freejia.data.CashShop;
import me.github.freejia.data.ConfigManager;
import me.github.freejia.data.SQL.MySql;
import me.github.freejia.events.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
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

        Listener[] events = {new ClickEvent()};
        PluginManager pm = Bukkit.getPluginManager();
        Arrays.stream(events).forEach(classes -> {
            pm.registerEvents(classes, this);
        });
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
        config.getConfig().addDefault("shop_message.64_sell","");

        config.getConfig().addDefault("cash_message.check","");
        config.getConfig().addDefault("cash_message.send","");
        config.getConfig().addDefault("cash_message.remove","");
        config.getConfig().addDefault("cash_message.set","");

        config.getConfig().addDefault("shop_price.gui","구매가격/판매가격");
        config.getConfig().addDefault("shop_price.gui_size",27);
        config.getConfig().addDefault("shop_price.buy_settings.item","LIME_WOOL");
        config.getConfig().addDefault("shop_price.buy_settings.slot",13);
        config.getConfig().addDefault("shop_price.buy_settings.lore", Arrays.asList("","&f[&b!&f] 클릭시 구매가격을 설정합니다!",""));

        config.getConfig().addDefault("shop_price.sell_settings.item","LIME_WOOL");
        config.getConfig().addDefault("shop_price.sell_settings.slot",15);
        config.getConfig().addDefault("shop_price.sell_settings.lore", Arrays.asList("","&f[&b!&f] 클릭시 구매가격을 설정합니다!",""));



        config.getConfig().options().copyDefaults(true);
        config.saveConfig();
    }
}

package me.github.freejia;

import me.github.freejia.cmd.cash;
import me.github.freejia.cmd.cashshop;
import me.github.freejia.data.Object.Cash;
import me.github.freejia.data.Object.CashShop;
import me.github.freejia.data.Config.ConfigManager;
import me.github.freejia.data.Object.Items;
import me.github.freejia.data.SQL.MySql;
import me.github.freejia.events.ChatEvent;
import me.github.freejia.events.ClickEvent;
import me.github.freejia.events.CloseEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static final Logger Log = Bukkit.getLogger();
    public static Main plugin;

    public static ConfigManager config;
    public static ConfigManager database;
    public static ConfigManager Cash;


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
        ConfigurationSerialization.registerClass(Items.class);

        init();

        Listener[] events = {new ClickEvent(), new CloseEvent(), new ChatEvent()};
        PluginManager pm = Bukkit.getPluginManager();
        Arrays.stream(events).forEach(classes -> {
            pm.registerEvents(classes, this);
        });
        registerPlaceHolder();
    }

    public void registerPlaceHolder() {

    }

    public void init() {
        database.getConfig().options().copyDefaults();

        database.getConfig().addDefault("Mysql.enabled", false);
        database.getConfig().addDefault("Mysql.type", "HeidiSQL");
        database.getConfig().addDefault("Mysql.mysql.host", "localhost");
        database.getConfig().addDefault("Mysql.mysql.port", 3306);
        database.getConfig().addDefault("Mysql.mysql.database", "minecraft");
        database.getConfig().addDefault("Mysql.mysql.username", "admin");
        database.getConfig().addDefault("Mysql.mysql.password", "");

        database.getConfig().options().copyDefaults(true);
        database.saveConfig();

        config.getConfig().options().copyDefaults();

        config.getConfig().addDefault("shop_message.1_buy", "%item_order%을 1개를 %buy_price%캐시로 구매하였습니다!");
        config.getConfig().addDefault("shop_message.1_sell", "%item_order% 1개를 %sell_price%캐시로 구매하였습니다!");
        config.getConfig().addDefault("shop_message.64_buy", "%item_order%을 64개를 %buy_price%캐시로 판매하였습니다!");
        config.getConfig().addDefault("shop_message.64_sell", "%item_order% 64개를 %sell_price%캐시로 판매하였습니다!");

        config.getConfig().addDefault("cash_message.check", "보유중인 캐시: %cash%");
        config.getConfig().addDefault("cash_message.check_user", "%player%님의 보유중인 캐시: %cash%");
        config.getConfig().addDefault("cash_message.send", "%player%님 에게 %cash%만큼의 캐시를 지급하였습니다!%");
        config.getConfig().addDefault("cash_message.remove", "%player%님의 캐시를 %cash%만큼 제거하였습니다!%");
        config.getConfig().addDefault("cash_message.set", "%player%님의 캐시를 %cash%로 설정하였습니다!");


        config.getConfig().addDefault("error_message.cant_buy_cash", "당신은 캐시가 부족하여 구매가 불가능합니다!");
        config.getConfig().addDefault("error_message.cant_buy_item", "당신은 아이템이 부족하여 판매가 불가능합니다!");
        config.getConfig().addDefault("error_message.cant_inventory_slot", "인벤토리에 빈공간이 없어, 구매가 불가능합니다!");
        config.getConfig().addDefault("error_message.command_none_player", "플레이어 닉네임을 입력해주세요!");
        config.getConfig().addDefault("error_message.command_none_cash", "금액을 입력해주세요!");
        config.getConfig().addDefault("error_message.shop_none_name", "상점 이름을 입력해주세요!");
        config.getConfig().addDefault("error_message.shop_none_line", "줄을 입력해 주세요!");

        config.getConfig().addDefault("shop_price.gui", "구매가격/판매가격");
        config.getConfig().addDefault("shop_price.gui_size", 27);
        config.getConfig().addDefault("shop_price.buy_settings.item", "LIME_WOOL");
        config.getConfig().addDefault("shop_price.buy_settings.slot", 11);
        config.getConfig().addDefault("shop_price.buy_settings.lore", Arrays.asList("", "&f[&b!&f] 클릭시 구매가격을 설정합니다!", ""));

        config.getConfig().addDefault("shop_price.sell_settings.item", "RED_WOOL");
        config.getConfig().addDefault("shop_price.sell_settings.slot", 13);
        config.getConfig().addDefault("shop_price.sell_settings.lore", Arrays.asList("", "&f[&b!&f] 클릭시 구매가격을 설정합니다!", ""));

        config.getConfig().options().copyDefaults(true);
        config.saveConfig();
    }
}

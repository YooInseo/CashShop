package me.github.freejia;

import me.github.freejia.cmd.Cashcmd;
import me.github.freejia.cmd.CashShopCmd;
import me.github.freejia.data.Data;
import me.github.freejia.data.object.Cash;
import me.github.freejia.data.object.CashShop;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.object.Items;
import me.github.freejia.data.object.log.AdminLog;
import me.github.freejia.data.object.log.UserLog;
import me.github.freejia.data.placeHolder.CashExpansion;

import me.github.freejia.events.ChatEvent;
import me.github.freejia.events.ClickEvent;
import me.github.freejia.events.CloseEvent;
import me.github.freejia.events.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
    public static ConfigManager Cash;

    public static ConfigManager UserLog;

    public static ConfigManager AdminLog;

    public void onEnable() {
        super.onEnable();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CashExpansion(this).register();
            Bukkit.getConsoleSender().sendMessage("§a등록 완료  §b%cashshop_cash% §a키워드로 사용 가능합니다!");
        } else {

            Bukkit.getConsoleSender().sendMessage("§cPLACEHOLDER API 기능이 해제 됩니다 (API 없음)");
        }

        config = new ConfigManager("config");
        plugin = this;


        new Cashcmd(this);
        new CashShopCmd(this);

        ConfigurationSerialization.registerClass(Cash.class);
        ConfigurationSerialization.registerClass(CashShop.class);
        ConfigurationSerialization.registerClass(Items.class);

        /**
         * 로그 클래스
         */
        ConfigurationSerialization.registerClass(me.github.freejia.data.object.log.UserLog.class);
        ConfigurationSerialization.registerClass(AdminLog.class);

        init();

        Listener[] events = {new ClickEvent(), new CloseEvent(), new ChatEvent(), new JoinEvent()};
        PluginManager pm = Bukkit.getPluginManager();
        Arrays.stream(events).forEach(classes -> {
            pm.registerEvents(classes, this);
        });


    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (Data.cashshop.containsKey(players.getUniqueId())) {
                players.closeInventory();
            }
        });
    }



    public void init() {
        config.getConfig().options().copyDefaults();

        config.getConfig().addDefault("shop_message.1_buy", "%item_order%을 1개를 %buy_price%캐시로 구매하였습니다!");
        config.getConfig().addDefault("shop_message.1_sell", "%item_order% 1개를 %sell_price%캐시로 판매하였습니다!");

        config.getConfig().addDefault("shop_message.64_buy", "%item_order%을 64개를 %buy_price%캐시로 판매하였습니다!");
        config.getConfig().addDefault("shop_message.64_sell", "%item_order% 64개를 %sell_price%캐시로 판매하였습니다!");

        config.getConfig().addDefault("shop_message.chat_setting_buy_price", "&a구매가격을 입력해주세요!");
        config.getConfig().addDefault("shop_message.chat_setting_sell_price", "&a판매가격을 입력해주세요!");

        config.getConfig().addDefault("shop_message.reload_message", "&a콘피그를 정상적으로 리로드하였습니다!");

        config.getConfig().addDefault("cash_message.check", "보유중인 캐시: %cash%");
        config.getConfig().addDefault("cash_message.check_user", "%player%님의 보유중인 캐시: %cash%");
        config.getConfig().addDefault("cash_message.send", "%player%님 에게 %cash%만큼의 캐시를 지급하였습니다!%");
        config.getConfig().addDefault("cash_message.initalization", "%player%님의 캐시를 초기화하였습니다!%");
        config.getConfig().addDefault("cash_message.remove", "%player%님의 캐시를 %cash%만큼 제거하였습니다!%");
        config.getConfig().addDefault("cash_message.set", "%player%님의 캐시를 %cash%로 설정하였습니다!");

        config.getConfig().addDefault("error_message.overflow", "캐시가 2147483647이상이 될 수 없습니다!");
        config.getConfig().addDefault("error_message.cant_buy_cash", "당신은 캐시가 부족하여 구매가 불가능합니다!");
        config.getConfig().addDefault("error_message.cant_sell_item", "당신은 아이템이 부족하여 판매가 불가능합니다!");
        config.getConfig().addDefault("error_message.cant_inventory_slot", "인벤토리에 빈공간이 없어, 구매가 불가능합니다!");
        config.getConfig().addDefault("error_message.command_none_player", "플레이어 닉네임을 입력해주세요!");
        config.getConfig().addDefault("error_message.command_none_cash", "금액을 입력해주세요!");
        config.getConfig().addDefault("error_message.shop_none_name", "상점 이름을 입력해주세요!");
        config.getConfig().addDefault("error_message.shop_none_line", "줄을 입력해 주세요!");
        config.getConfig().addDefault("error_message.cant_buy_impossible_item", "구매가 불가능한 상품입니다!");
        config.getConfig().addDefault("error_message.cant_sell_impossible_item", "판매가 불가능한 상품입니다!");

        config.getConfig().addDefault("cash_shop_message.main", Arrays.asList(
                "/캐시상점 생성 [이름] : 캐시상점을 생성합니다.",
                "/캐시상점 제거 [이름] : 캐시상점을 제거합니다.",
                "/캐시상점 줄 [이름] [1~6] : 캐시상점 줄을 설정합니다.",
                "/캐시상점 GUI이름 [이름] [GUI이름] : GUI이름을 설정합니다.",
                "/캐시상점 편집 [이름] : 캐시상점을 편집합니다."));


        config.getConfig().addDefault("cash_shop_message.create_shop", "%shopname%의 캐시상점을 생성하였습니다!");
        config.getConfig().addDefault("cash_shop_message.delete_shop", "%shopname%의 캐시상점을 제거하였습니다!");
        config.getConfig().addDefault("cash_shop_message.setting_gui_size", "%shopname%의 GUI 사이즈를 %gui_size%로 설정하였습니다!");
        config.getConfig().addDefault("cash_shop_message.setting_gui_name", "%shopname% GUI이름을 %changename%으로 변경하였습니다!");
        config.getConfig().addDefault("cash_shop_message.buy_price", "아이템 구매 가격을 %buy_price%캐시로 설정하였습니다!");
        config.getConfig().addDefault("cash_shop_message.sell_price", "아이템 판매 가격을 %sell_price%캐시로 설정하였습니다!");


        config.getConfig().addDefault("cash_shop_message.defualt_lore", Arrays.asList(
                "",
                "&f[&b!&f] 구매 가격: %buy_price% 캐시",
                "&f[&b!&f] 판매 가격: %sell_price% 캐시",
                "",
                "&f[&b!&f] 쉬프트 + 좌클릭: 64개 구매",
                "&f[&b!&f] 쉬프트 + 우클릭: 64개 판매",
                ""));


        config.getConfig().addDefault("cash_shop_message.cant_buy", Arrays.asList(
                "",
                "&f[&b!&f] 구매 불가 상품입니다.",
                "&f[&b!&f] 판매 가격: %sell_price% 캐시",
                "",
                "&f[&b!&f] 쉬프트 + 좌클릭: 64개 구매",
                "&f[&b!&f] 쉬프트 + 우클릭: 64개 판매",
                ""));

        config.getConfig().addDefault("cash_shop_message.cant_sell", Arrays.asList(
                "",
                "&f[&b!&f] 구매 가격: %buy_price% 캐시",
                "&f[&b!&f] 판매 불가 상품입니다.",
                "",
                "&f[&b!&f] 쉬프트 + 좌클릭: 64개 구매",
                "&f[&b!&f] 쉬프트 + 우클릭: 64개 판매",
                ""));

        config.getConfig().addDefault("cash_shop_message.both", Arrays.asList(
                "",
                "&f[&b!&f] 구매 불가 상품입니다.",
                "&f[&b!&f] 판매 불가 상품입니다.",
                "",
                "&f[&b!&f] 쉬프트 + 좌클릭: 64개 구매",
                "&f[&b!&f] 쉬프트 + 우클릭: 64개 판매",
                ""));

        config.getConfig().addDefault("cash_shop_message.lore", "");

        config.getConfig().addDefault("cash_shop_message.buy_sound", Sound.ENTITY_VILLAGER_NO.name());
        config.getConfig().addDefault("cash_shop_message.sell_sound", Sound.ENTITY_VILLAGER_NO.name());
        config.getConfig().addDefault("cash_shop_message.imposibble_sound", Sound.ENTITY_VILLAGER_NO.name());
        config.getConfig().addDefault("shop_price.gui", "구매가격/판매가격");
        config.getConfig().addDefault("shop_price.gui_size", 27);
        config.getConfig().addDefault("shop_price.buy_settings.item", "LIME_WOOL");
        config.getConfig().addDefault("shop_price.buy_settings.slot", 12);
        config.getConfig().addDefault("shop_price.buy_settings.lore", Arrays.asList("", "&f[&b!&f] 클릭시 구매가격을 설정합니다!", ""));

        config.getConfig().addDefault("shop_price.sell_settings.item", "RED_WOOL");
        config.getConfig().addDefault("shop_price.sell_settings.slot", 14);
        config.getConfig().addDefault("shop_price.sell_settings.lore", Arrays.asList("", "&f[&b!&f] 클릭시 판매가격을 설정합니다!", ""));

        config.getConfig().options().copyDefaults(true);
        config.saveConfig();
    }
}

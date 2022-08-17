package me.github.freejia.util;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String replace(Player player, long cash, String path) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("data/" + player.getUniqueId());

        a = a.replaceAll("%cash%", cash + "");
        a = a.replaceAll("%player%", player.getDisplayName());
        return a;
    }

    public static String replace(String name, String path) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        a = a.replaceAll("%shopname%", name);

        return a;
    }

    public static String replace(String name, String path, int line) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        a = a.replaceAll("%shopname%", name);
        a = a.replaceAll("%gui_size%", line + "");
        return a;
    }


    public static String buyreplace(String name, String path, int buy_price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);


        a = a.replaceAll("%buy_price%", buy_price + "");
        return a;
    }

    public static String sellreplace(String name, String path, int sell_price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        a = a.replaceAll("%sell_price%", sell_price + "");
        return a;
    }
    public static String replace(String name, String path, String target) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        a = a.replaceAll("%shopname%", name);
        a = a.replaceAll("%changename%", target + "");
        return a;
    }


    public static String replace(String name, String path, ItemStack item, int price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);
        if(item.hasItemMeta()){
            a = a.replaceAll("%item_order%", item.getItemMeta().getDisplayName());
        } else{
            a = a.replaceAll("%item_order%", item.getType().name());
        }

        a = a.replaceAll("%buy_price%", price + "");
        return a;
    }

    public static String sellreplace(String name, String path, ItemStack item, int price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        if(item.hasItemMeta()){
            a = a.replaceAll("%item_order%", item.getItemMeta().getDisplayName());
        } else{
            a = a.replaceAll("%item_order%", item.getType().name());
        }
        a = a.replaceAll("%sell_price%", price + "");
        return a;
    }


    public static List<String> replace(List<String> arrays, int buy, int sell) {

        List<String> newArray = new ArrayList<>();
        for (String array : arrays) {

            array = array.replaceAll("%buy_price%", buy + "");
            array = array.replaceAll("%sell_price%", sell + "");
            array = ChatColor.translateAlternateColorCodes('&', array);
            newArray.add(array);
        }

        return newArray;
    }

    public static List<String> replace(List<String> arrays ) {

        List<String> newArray = new ArrayList<>();
        for (String array : arrays) {
            array = ChatColor.translateAlternateColorCodes('&', array);
            newArray.add(array);
        }

        return newArray;
    }
    public static boolean isInventoryFull(Player p)
    {
        int slot = p.getInventory().firstEmpty();

        return slot == -1;
    }

    public static void removeOne(Inventory inventory, ItemStack item, int amount) {
        int size = inventory.getSize();
        for (int i = 0; i < size; i ++) {
            ItemStack other = inventory.getItem(i);
            if (item.isSimilar(other)) {
                if (amount > 1) {
                    other.setAmount(other.getAmount() - amount);
                } else {
                    other = null;
                }
                inventory.setItem(i, other);
                break;
            }
        }
    }
}

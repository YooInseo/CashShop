package me.github.freejia.util;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {

    public static ItemStack AddNBTItem(ItemStack itemStack) {

        NBTItem nbtItem = new NBTItem(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        if(!itemStack.getType().equals(Material.AIR)){
            if (nbtItem.hasCustomNbtData()) {
                itemStack.setItemMeta(meta);
                return nbtItem.getItem();
            } else {
                return itemStack;
            }
        }
        return null;
    }

    public static String replace(Player player, String path) {
        String a = Main.config.getString(path);

        a = a.replaceAll("%player%", player.getDisplayName());
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String replace(Player player, long cash, String path) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("data/" + player.getUniqueId());

        a = a.replaceAll("%cash%", cash + "");
        a = a.replaceAll("%player%", player.getDisplayName());
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String replace(String name, String path) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        a = a.replaceAll("%shopname%", name);
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String replace(String name, String path, int line) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        a = a.replaceAll("%shopname%", name);
        a = a.replaceAll("%gui_size%", line + "");
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }


    public static String buyreplace(String name, String path, int buy_price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);


        a = a.replaceAll("%buy_price%", buy_price + "");
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String buyreplace(String name, ItemStack item, String path, int buy_price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);
        if (item.hasItemMeta()) {
            a = a.replaceAll("%item_order%", item.getItemMeta().getDisplayName() + "");
        } else {
            a = a.replaceAll("%item_order%", item.getType().name() + "");

        }
        a = a.replaceAll("%buy_price%", buy_price + "");
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String sellreplace(String name, ItemStack item, String path, int sell_price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);
        if (item.hasItemMeta()) {
            a = a.replaceAll("%item_order%", item.getItemMeta().getDisplayName() + "");
        } else {
            a = a.replaceAll("%item_order%", item.getType().name() + "");

        }
        a = a.replaceAll("%sell_price%", sell_price + "");
        a = ChatColor.translateAlternateColorCodes('&', a);
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
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String replace(String path) {
        String a = Main.config.getString(path);


        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }


    public static String replace(String name, String path, ItemStack item, int price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);
        if (item.hasItemMeta()) {
            a = a.replaceAll("%item_order%", item.getItemMeta().getDisplayName());
        } else {
            a = a.replaceAll("%item_order%", item.getType().name());
        }

        a = a.replaceAll("%buy_price%", price + "");
        a = ChatColor.translateAlternateColorCodes('&', a);
        return a;
    }

    public static String sellreplace(String name, String path, ItemStack item, int price) {
        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("shop/" + name);

        if (item.hasItemMeta()) {
            a = a.replaceAll("%item_order%", item.getItemMeta().getDisplayName());
        } else {
            a = a.replaceAll("%item_order%", item.getType().name());
        }
        a = a.replaceAll("%sell_price%", price + "");
        a = ChatColor.translateAlternateColorCodes('&', a);
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

    public static List<String> replace(List<String> arrays) {

        List<String> newArray = new ArrayList<>();
        for (String array : arrays) {
            array = ChatColor.translateAlternateColorCodes('&', array);
            newArray.add(array);
        }

        return newArray;
    }

    public static boolean isInventoryFull(Player p) {
        int slot = p.getInventory().firstEmpty();

        return slot == -1;
    }

    public static boolean removeOne(Inventory inventory, ItemStack item, int amount) {
        int size = inventory.getSize();
        for (int i = 0; i < size; i++) {
            ItemStack other = inventory.getItem(i);
            if (item.isSimilar(other)) {

                if (amount == 64) {
                    if (other.getAmount() == 64) {
                        other.setAmount(other.getAmount() - amount);
                        return true;
                    } else {
                        return false;
                    }

                } else if (amount == 1) {
                    other.setAmount(other.getAmount() - amount);
                    return true;
                }
                inventory.setItem(i, other);
                break;
            }
        }
        return false;
    }

    public static String getDate() {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
        Date date = new Date();
        String nowTime = format2.format(date);
        return nowTime;
    }

    public static String decal(long amount) {
        DecimalFormat decFormat = new DecimalFormat("###,###");

        String str = decFormat.format(amount);

        return str;
    }
}

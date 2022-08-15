package me.github.freejia.util;

import me.github.freejia.Main;
import me.github.freejia.data.Cash;
import me.github.freejia.data.ConfigManager;
import org.bukkit.entity.Player;

public class Util {

    public static String replace(Player player,String path){

        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("data/" + player.getUniqueId());
        Cash cash = Main.Cash.getConfig().getObject("Cash", Cash.class);

        a = a.replaceAll("%cash%",cash.getCash() + "");
        a = a.replaceAll("%player%",player.getDisplayName());
        return a;
    }

}

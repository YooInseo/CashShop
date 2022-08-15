package me.github.freejia.util;

import me.github.freejia.Main;
import me.github.freejia.data.Cash;
import me.github.freejia.data.ConfigManager;
import org.bukkit.entity.Player;

public class Util {

    public static String replace(Player player,long cash, String path){

        String a = Main.config.getString(path);

        Main.Cash = new ConfigManager("data/" + player.getUniqueId());


        a = a.replaceAll("%cash%",cash + "");
        a = a.replaceAll("%player%",player.getDisplayName());
        return a;
    }

}

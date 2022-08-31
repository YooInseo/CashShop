package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.object.Cash;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Main.Cash = new ConfigManager("data/" + player.getUniqueId());

        if(!Main.Cash.isExist()){
            Cash cash = new Cash(player,0);
            Main.Cash.getConfig().set("Cash",cash);
            Main.Cash.saveConfig();
        }
    }
}

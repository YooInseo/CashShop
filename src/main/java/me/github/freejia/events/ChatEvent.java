package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.Config.ConfigManager;
import me.github.freejia.data.Object.CashShop;
import me.github.freejia.data.Data;
import me.github.freejia.data.Object.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        if(Data.cashshop.containsKey(player.getUniqueId())){
            try{
                Integer amount = Integer.parseInt(event.getMessage());
                if(amount > 0){
                    CashShop cashShop = Data.cashshop.get(player.getUniqueId());

                    if(!cashShop.isDefualt()){
                        cashShop.setPlayer(player);
                        cashShop.setPrice(amount);

                        cashShop.getSelect().setBuyprice(amount);
                        cashShop.UpdateItem();
                        cashShop.setType(Type.Default);

                        Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                cashShop.Editor();
                            }
                        });

                        event.setCancelled(true);
                    }
                } else{
                    event.setCancelled(true);
                }



            } catch (NumberFormatException e){
                return;
            }
        }
    }
}

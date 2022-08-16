package me.github.freejia.events;

import me.github.freejia.data.CashShop;
import me.github.freejia.data.Data;
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
                long amount = Long.parseLong(event.getMessage());

                CashShop cashShop = Data.cashshop.get(player.getUniqueId());
                cashShop.setPrice(amount);

            } catch (NumberFormatException e){
                return;
            }
        }
    }
}

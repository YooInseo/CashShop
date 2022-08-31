package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.object.CashShop;
import me.github.freejia.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (Data.cashshop.containsKey(player.getUniqueId())) {
            CashShop cashshop;
            cashshop = Data.cashshop.get(player.getUniqueId());
            if (cashshop != null) {
                if (cashshop.isPrice()) {

                    if (event.getView().getTitle().equals(Main.config.getConfig().getString("shop_price.gui")) && cashshop.isDefualt()) {
                        Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                cashshop.Editor();
                            }
                        });
                    }

                } else if (cashshop.getEditorInv() != null) {


                    if(cashshop.getEditorInv().equals(event.getInventory())){
                        cashshop.saveItem();

                        Data.cashshop.remove(player.getUniqueId());
                    }
                } else if(event.getView().getTitle().equalsIgnoreCase(Data.cashshop.get(player.getUniqueId()).getName())){
                    Data.cashshop.remove(player.getUniqueId());
                }
            }
        }
    }
}

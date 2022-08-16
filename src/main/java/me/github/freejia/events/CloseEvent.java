package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.Object.CashShop;
import me.github.freejia.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (Data.cashshop.containsKey(player.getUniqueId())) {
            CashShop cashshop;
            cashshop = Data.cashshop.get(player.getUniqueId());
            if (cashshop != null) {
                if (cashshop.isPrice()) {
                    if (cashshop.getPriceInv().equals(event.getInventory()) && event.getView().getTitle().equals(Main.config.getConfig().getString("shop_price.gui")) && cashshop.isDefualt()) {
                        Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                cashshop.Editor();

                            }
                        });
                    }
                } else if (cashshop.getEditorInv().equals(event.getInventory())) {
                    cashshop.saveItem();
                }
            }
        }
    }
}

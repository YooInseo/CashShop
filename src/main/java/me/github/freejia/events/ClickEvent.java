package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (Data.cashshop.containsKey(player.getUniqueId())) {
            if (inv.equals(Data.cashshop.get(player.getUniqueId()).getEditorInv())) {
                if (event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR) {
                    player.sendMessage("" + event.getClick().isShiftClick());

                    if (event.getClick().isShiftClick()) {
                        event.setCancelled(true);
                        Data.cashshop.get(player.getUniqueId()).PriceGUI();
                    }
                }
            } else if (event.getView().getTitle().equals(Main.config.getConfig().getString("shop_price.gui"))) {
                switch (event.getSlot()) {
                    case 12:

                        break;
                    case 14:

                        break;
                }
                event.setCancelled(true);
            }
        }
    }
}

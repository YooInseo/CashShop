package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.Data;
import me.github.freejia.data.Object.CashShop;
import me.github.freejia.data.Object.Type;
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
        CashShop cashShop;
        if (Data.cashshop.containsKey(player.getUniqueId())) {
            if (inv.equals(Data.cashshop.get(player.getUniqueId()).getEditorInv())) {
                if (event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR) {
                    cashShop = Data.cashshop.get(player.getUniqueId());

                    if (event.getClick().isShiftClick()) {
                        event.setCancelled(true);
                        cashShop.setSelect( 0);
                        cashShop.PriceGUI();
                    }
                }
            } else if (event.getView().getTitle().equals(Main.config.getConfig().getString("shop_price.gui"))) {
                 cashShop = Data.cashshop.get(player.getUniqueId());
                switch (event.getSlot()) {
                    case 12:
                        cashShop.setType(Type.BUY);
                        player.closeInventory();
                        player.sendMessage("§a구매 가격을 입력해 주세요!");
                        break;
                    case 14:
                        cashShop.setType(Type.SELL);
                        player.sendMessage("§a판매 가격을 입력해 주세요!");
                        player.closeInventory();
                        break;
                }
                event.setCancelled(true);
            } else if (event.getView().getTitle().equals(Data.cashshop.get(player.getUniqueId()).getTitle())) {
                if (event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR) {
                    event.setCancelled(true);
                } else{
                    return;
                }

            }
        }
    }
}

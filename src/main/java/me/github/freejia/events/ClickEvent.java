package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.Data;
import me.github.freejia.data.object.Cash;
import me.github.freejia.data.object.CashShop;
import me.github.freejia.data.object.Items;
import me.github.freejia.data.object.Type;
import me.github.freejia.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
                cashShop = Data.cashshop.get(player.getUniqueId());

                if (event.getClick().equals(ClickType.RIGHT) || event.getClick().equals(ClickType.LEFT)) {

                    cashShop = Data.cashshop.get(player.getUniqueId());


                }
                if (event.getClick().isShiftClick()) {
                    event.setCancelled(true);

                    for (Items items : cashShop.getItems()) {
                        if (items.getSlot() == event.getSlot()) {
                            int i = cashShop.getItems().indexOf(items);
                            Data.select.put(player.getUniqueId(), i);
                        }
                    }
                    cashShop.PriceGUI();
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
                cashShop = Data.cashshop.get(player.getUniqueId());
                Main.Cash = new ConfigManager("data/" + player.getUniqueId());
                Cash cash = Main.Cash.getConfig().getObject("Cash", Cash.class);

                for (Items items : cashShop.getItems()) {
                    if (items.getSlot() == event.getSlot()) {
                        int i = cashShop.getItems().indexOf(items);
                        Data.select.put(player.getUniqueId(), i);
                    }
                }

                Items item = cashShop.getItems().get(Data.select.get(player.getUniqueId()));

                switch (event.getClick()) {
                    case LEFT:
                        if (item.getSlot() == event.getSlot()) {
                            if (Util.isInventoryFull(player)) {
                                player.sendMessage(Main.config.getString("error_message.cant_inventory_slot"));
                            } else {
                                if (item.getBuyprice() == -1) {
                                    player.sendMessage(Main.config.getString("error_message.cant_buy_impossible_item"));
                                } else {
                                    if (event.isShiftClick()) {
                                        if (item.getBuyprice() > 0) {
                                            if (cash.Decrease(item.getBuyprice() * 64)) {
                                                Main.Cash.getConfig().set("Cash", cash);
                                                Main.Cash.saveConfig();
                                                ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial()));
                                                itemStack.setAmount(64);
                                                player.getInventory().addItem(itemStack);
                                            } else {
                                                player.sendMessage(Main.config.getString("error_message.cant_buy_cash"));
                                            }
                                        }


                                    } else {
                                        if (cash.Decrease(item.getBuyprice())) {
                                            Main.Cash.getConfig().set("Cash", cash);
                                            Main.Cash.saveConfig();
                                            ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial()));
                                            itemStack.setItemMeta(item.getMeta());
                                            player.getInventory().addItem(itemStack);
                                            player.sendMessage(Util.replace("", "shop_message.1_buy", itemStack, item.getBuyprice()));
                                        } else {
                                            player.sendMessage(Main.config.getString("error_message.cant_buy_cash"));
                                        }

                                    }
                                }
                            }
                        }

                        break;

                    case RIGHT:
                        if (event.isShiftClick()) {

                        } else {

                        }
                        break;
                }
                event.setCancelled(true);
            }
        }
    }
}

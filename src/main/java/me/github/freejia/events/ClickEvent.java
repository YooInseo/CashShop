package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.Data;
import me.github.freejia.data.object.Cash;
import me.github.freejia.data.object.CashShop;
import me.github.freejia.data.object.Items;
import me.github.freejia.data.object.Type;
import me.github.freejia.data.object.log.UserLog;
import me.github.freejia.util.Util;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        CashShop cashShop;


        if (Data.cashshop.containsKey(player.getUniqueId())) {
            if (inv != null) {

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
                                cashShop.PriceGUI();
                            }
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


                    if (item.getSlot() == event.getSlot()) {
                        if (event.getClick().isLeftClick() && event.getClick().isShiftClick()) {
                            if (!Util.isInventoryFull(player)) {
                                if (item.getBuyprice() != -1) {
                                    if (cash.Decrease(item.getBuyprice() * 64)) {
                                        Main.Cash.getConfig().set("Cash", cash);
                                        Main.Cash.saveConfig();
                                        ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial()));
                                        itemStack.setAmount(64);
                                        player.getInventory().addItem(itemStack);

                                        saveLog(player, itemStack, 64, Type.BUY, item.getBuyprice() * 64, itemStack);

                                        player.sendMessage(Util.buyreplace(cashShop.getName(), itemStack, "shop_message.64_buy", item.getBuyprice() * 64));

                                    } else {
                                        Sound(player,"cash_shop_message.cash_shop_message.buy_sound");
                                        player.sendMessage(Main.config.getString("error_message.cant_buy_cash"));
                                    }
                                } else {
                                    Sound(player,"cash_shop_message.imposibble_sound");
                                    player.sendMessage(Main.config.getString("error_message.cant_buy_impossible_item"));
                                }

                            } else {
                                player.sendMessage(Main.config.getString("error_message.cant_inventory_slot"));
                            }
                        } else if (event.getClick().isLeftClick()) {
                            if (item.getBuyprice() != -1) {
                                if (cash.Decrease(item.getBuyprice())) {
                                    Main.Cash.getConfig().set("Cash", cash);
                                    Main.Cash.saveConfig();
                                    ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial()));
                                    itemStack.setAmount(1);
                                    player.getInventory().addItem(itemStack);
                                    player.sendMessage(Util.buyreplace(cashShop.getName(), itemStack, "shop_message.1_buy", item.getBuyprice()));
                                    saveLog(player, itemStack, 1, Type.BUY, item.getBuyprice(), itemStack);

                                } else {
                                    Sound(player,"cash_shop_message.cash_shop_message.buy_sound");
                                    player.sendMessage(Main.config.getString("error_message.cant_buy_cash"));
                                }
                            } else {
                                Sound(player,"cash_shop_message.imposibble_sound");

                                player.sendMessage(Main.config.getString("error_message.cant_buy_impossible_item"));
                            }


                        } else if (event.getClick().isRightClick() && event.getClick().isShiftClick()) {
                            if (item.getSellprice() != -1) {
                                ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial()));
                                if (Util.removeOne(player.getInventory(), itemStack, 64)) {
                                    cash.increase(item.getSellprice() * 64);
                                    Main.Cash.getConfig().set("Cash", cash);
                                    Main.Cash.saveConfig();
                                    saveLog(player, itemStack, 64, Type.SELL, item.getBuyprice() * 64, itemStack);
                                    player.sendMessage(Util.sellreplace(cashShop.getName(), itemStack, "shop_message.64_sell", item.getSellprice() * 64));
                                } else {
                                    Sound(player,"cash_shop_message.sell_sound");
                                    player.sendMessage(Main.config.getString("error_message.cant_sell_item"));
                                }
                            } else {

                                Sound(player,"cash_shop_message.imposibble_sound");
                                player.sendMessage(Main.config.getString("error_message.cant_sell_impossible_item"));
                            }

                        } else if (event.getClick().isRightClick()) {
                            if (item.getSellprice() != -1) {
                                ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial()));
                                if (Util.removeOne(player.getInventory(), itemStack, 1)) {
                                    cash.increase(item.getSellprice());
                                    Main.Cash.getConfig().set("Cash", cash);
                                    Main.Cash.saveConfig();
                                    saveLog(player, itemStack, 1, Type.SELL, item.getBuyprice(), itemStack);
                                    player.sendMessage(Util.sellreplace(cashShop.getName(), itemStack, "shop_message.1_sell", item.getSellprice()));
                                } else{
                                    Sound(player,"cash_shop_message.sell_sound");
                                    player.sendMessage(Main.config.getString("error_message.cant_sell_item"));
                                }
                            } else {
                                Sound(player,"cash_shop_message.imposibble_sound");
                                player.sendMessage(Main.config.getString("error_message.cant_sell_impossible_item"));
                            }

                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    public void saveLog(Player player, ItemStack items, int amount, Type type, int price, ItemStack item) {
        ConfigManager config = Main.UserLog = new ConfigManager("log/user/" + player.getUniqueId());

        UserLog userlog = new UserLog(player, amount, type, price, item);


        if (!config.isExist()) {
            config.getConfig().set("Log", new ArrayList<UserLog>());

            List<UserLog> log = (List<UserLog>) config.getConfig().getList("Log");

            log.add(userlog);

            config.saveConfig();
        } else {
            List<UserLog> log = (List<UserLog>) config.getConfig().getList("Log");
            log.add(userlog);

            config.saveConfig();
        }
    }

    public void Sound(Player player,String path){
        Sound sound = Sound.valueOf(Main.config.getString(path));
        player.playSound(player, sound, 1, 1);
    }

}

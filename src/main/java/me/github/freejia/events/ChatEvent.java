package me.github.freejia.events;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.object.CashShop;
import me.github.freejia.data.Data;
import me.github.freejia.data.object.Items;
import me.github.freejia.data.object.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (Data.cashshop.containsKey(player.getUniqueId())) {
            try {
                Integer amount = Integer.parseInt(event.getMessage());
                if (amount > 0 || amount  == -1) {
                    CashShop cashShop = Data.cashshop.get(player.getUniqueId());

                    if (!cashShop.isDefualt()) {

                        cashShop.setPlayer(player);
                        Items item = cashShop.getItems().get(Data.select.get(player.getUniqueId()));

                        if (cashShop.getType().equals(Type.BUY)) {
                            item.setBuyprice(amount);
                        } else if (cashShop.getType().equals(Type.SELL)) {
                            item.setSellprice(amount);
                        }

                        cashShop.getCashShop().getItems().set(Data.select.get(player.getUniqueId()), item);

                        ConfigManager shop = new ConfigManager("shop/" + Data.cashshop.get(player.getUniqueId()).getName());

                        shop.getConfig().set("shop", cashShop);
                        shop.saveConfig();

                        Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                            @Override
                            public void run() {
                                cashShop.Editor();
                            }
                        });

                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }


            } catch (NumberFormatException e) {
                return;
            }
        }
    }

}

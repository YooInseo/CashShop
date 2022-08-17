package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.cmd.tab.CashShopTabComplete;
import me.github.freejia.data.Object.CashShop;
import me.github.freejia.data.Config.ConfigManager;
import me.github.freejia.data.Data;
import me.github.freejia.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CashShopCmd implements CommandExecutor {

    private Main plugin;

    public CashShopCmd(Main plugin) {
        Bukkit.getPluginCommand("캐시상점").setExecutor(this);
        Bukkit.getPluginCommand("캐시상점").setTabCompleter(new CashShopTabComplete());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String name;
        CashShop cashShop;
        if (args.length == 0) {

        } else {
            if (player.isOp()) {
                ConfigManager shop;
                switch (args[0]) {
                    case "생성":
                        if (args.length > 1) {
                            name = args[1];
                            shop = new ConfigManager("shop/" + name);

                            cashShop = new CashShop(name, player);
                            cashShop.setLine(6);
                            shop.getConfig().set("shop", cashShop);
                            shop.saveConfig();
                            player.sendMessage (Util.replace(name,"cash_shop_message.create_shop"));

                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }

                        break;
                    case "줄":
                        if (args.length > 1) {
                            name = args[1];
                            if (args.length > 2) {
                                int line = Integer.parseInt(args[2]);
                                shop = new ConfigManager("shop/" + name);

                                cashShop = shop.getConfig().getObject("shop", CashShop.class);

                                cashShop.setLine(line);
                                shop.getConfig().set("shop", cashShop);
                                shop.saveConfig();
                                player.sendMessage(Util.replace(name,"cash_shop_message.setting_gui_size",line));
                            } else {
                                player.sendMessage(Main.config.getString("error_message.shop_none_line"));
                            }
                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }

                        break;

                    case "편집":
                        if (args.length > 1) {
                            if (!Data.cashshop.containsKey(player.getUniqueId())) {
                                name = args[1];

                                shop = new ConfigManager("shop/" + name);
                                cashShop = shop.getConfig().getObject("shop", CashShop.class);
                                cashShop.setPlayer(player);
                                cashShop.Editor();

                                CashShop cashshop = cashShop;
                                Data.cashshop.put(player.getUniqueId(), cashshop);
                            }
                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }

                        break;

                    case "GUI이름":
                        if (args.length > 1) {
                            if (args.length > 2) {
                                name = args[1];
                                String targetname = args[2];

                                shop = new ConfigManager("shop/" + name);
                                cashShop = shop.getConfig().getObject("shop", CashShop.class);
                                cashShop.setTitle(targetname);
                                shop.getConfig().set("shop", cashShop);
                                shop.saveConfig();
                                shop.rename("shop/" + targetname);
                                player.sendMessage(Util.replace(name, "cash_shop_message.setting_gui_name",targetname));
                            }
                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }

                        break;

                    case "리로드":
                        if (args.length > 1) {
                            name = args[1];
                            shop = new ConfigManager("shop/" + name);

                            shop.reloadConfig();
                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }

                        break;
                    case "열기":
                        if (args.length > 1) {
                            if (!Data.cashshop.containsKey(player.getUniqueId())) {
                                name = args[1];
                                shop = new ConfigManager("shop/" + name);
                                cashShop = shop.getConfig().getObject("shop", CashShop.class);
                                cashShop.setPlayer(player);
                                cashShop.Open();
                                Data.cashshop.put(player.getUniqueId(), cashShop);
                            }

                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }
                        break;

                    case "제거":
                        if(args.length > 1) {
                            name = args[1];
                            shop = new ConfigManager("shop/" + name);
                            shop.delete();

                            player.sendMessage(Util.replace(name,"cash_shop_message.delete_shop"));
                        }
                        break;
                }
            } else {
                switch (args[0]) {

                }
            }

        }


        return false;
    }
}

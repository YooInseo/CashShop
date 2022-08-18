package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.cmd.tab.CashShopTabComplete;
import me.github.freejia.data.object.CashShop;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.Data;
import me.github.freejia.util.Util;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

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
            List<String> messages = Main.config.getConfig().getStringList("cash_shop_message.main");
            for (String message : messages) {
                player.sendMessage(message);
            }
        } else {
            ConfigManager shop;
            switch (args[0]) {
                case "생성":

                    if (player.isOp()) {
                        if (args.length > 1) {
                            name = args[1];
                            shop = new ConfigManager("shop/" + name);

                            cashShop = new CashShop(name, player);
                            cashShop.setLine(6);
                            shop.getConfig().set("shop", cashShop);
                            shop.saveConfig();
                            player.sendMessage(Util.replace(name, "cash_shop_message.create_shop"));

                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }
                    }


                    break;
                case "줄":
                    if (player.isOp()) {
                        if (args.length > 1) {
                            name = args[1];
                            if (args.length > 2) {
                                int line = Integer.parseInt(args[2]);
                                if (line <= 6 || line != 0) {
                                    shop = new ConfigManager("shop/" + name);

                                    cashShop = shop.getConfig().getObject("shop", CashShop.class);

                                    cashShop.setLine(line);
                                    shop.getConfig().set("shop", cashShop);
                                    shop.saveConfig();
                                    player.sendMessage(Util.replace(name, "cash_shop_message.setting_gui_size", line));
                                }

                            } else {
                                player.sendMessage(Main.config.getString("error_message.shop_none_line"));
                            }
                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }

                    }

                    break;

                case "편집":
                    if (player.isOp()) {
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
                    }


                    break;

                case "GUI이름":

                    if (player.isOp()) {
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
                                player.sendMessage(Util.replace(name, "cash_shop_message.setting_gui_name", targetname));
                            }
                        } else {
                            player.sendMessage(Main.config.getString("error_message.shop_none_name"));
                        }
                    }


                    break;
                case "리로드":

                    File dir = new File(Main.plugin.getDataFolder() + "/shop/");
                    File files[] = dir.listFiles();

                    Main.config = new ConfigManager("config");
                    Main.config.reloadConfig();
                    if (files != null) {
                        for (File file : files) {
                            String filename = FilenameUtils.getName(file.getPath());
                            filename = filename.replaceAll(".yml", "");
                            ConfigManager config = new ConfigManager("/shop/" + filename);
                            config.reloadConfig();
                        }
                        player.sendMessage(Util.replace("shop_message.reload_message"));
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
                    if (player.isOp()) {
                        if (args.length > 1) {
                            name = args[1];
                            shop = new ConfigManager("shop/" + name);
                            shop.delete();

                            player.sendMessage(Util.replace(name, "cash_shop_message.delete_shop"));
                        }
                    }
                    break;
            }


        }


        return false;
    }
}

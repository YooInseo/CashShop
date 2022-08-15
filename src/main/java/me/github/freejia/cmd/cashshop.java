package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.cmd.tab.CashShopTabComplete;
import me.github.freejia.cmd.tab.CashTabComplete;
import me.github.freejia.data.CashShop;
import me.github.freejia.data.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cashshop implements CommandExecutor {

    private Main plugin;

    public cashshop(Main plugin) {
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
                        name = args[1];
                        shop = new ConfigManager("shop/" + name);

                        cashShop = new CashShop(name, player);

                        shop.getConfig().set("shop", cashShop);
                        shop.saveConfig();
                        break;
                    case "줄":

                        name = args[1];
                        int line = Integer.parseInt(args[2]);
                        shop = new ConfigManager("shop/" + name);

                        cashShop = shop.getConfig().getObject("shop", CashShop.class);

                        cashShop.setLine(line);
                        shop.getConfig().set("shop", cashShop);
                        shop.saveConfig();

                        break;

                    case "편집":
                        name = args[1];
                        shop = new ConfigManager("shop/" + name);
                        cashShop = shop.getConfig().getObject("shop", CashShop.class);
                        cashShop.setPlayer(player);
                        cashShop.Editor();
                        break;

                    case "GUI이름":
                        name = args[1];
                        String targetname = args[2];

                        shop = new ConfigManager("shop/" + name);

                        cashShop = shop.getConfig().getObject("shop", CashShop.class);
                        cashShop.setTitle(targetname);
                        shop.getConfig().set("shop", cashShop);
                        shop = new ConfigManager("shop/" + targetname);
                        shop.saveConfig();
                        break;
                        
                    case "리로드":
                        name = args[1];
                        shop = new ConfigManager("shop/" + name);

                        shop.reloadConfig();
                        break;
                }
            }

        }


        return false;
    }
}

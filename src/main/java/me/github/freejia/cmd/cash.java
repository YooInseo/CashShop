package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.cmd.tab.CashShopTabComplete;
import me.github.freejia.cmd.tab.CashTabComplete;
import me.github.freejia.data.Cash;
import me.github.freejia.data.ConfigManager;
import me.github.freejia.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cash implements CommandExecutor {
    private Main plugin;

    public cash(Main plugin) {
        Bukkit.getPluginCommand("캐시").setExecutor(this);
        Bukkit.getPluginCommand("캐시").setTabCompleter(new CashTabComplete());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        Player target;
        int amount;
        Cash cash = new Cash(player);

        if (args.length == 0) {
            Main.Cash = new ConfigManager("data/" + player.getUniqueId());
            cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
            if (cash == null) {
                cash = new Cash(player);
                Main.Cash.getConfig().set("Cash", cash);
                Main.Cash.saveConfig();
                player.sendMessage(Util.replace(player, cash.getCash(),"cash_message.check"));
            } else {
                player.sendMessage(Util.replace(player, cash.getCash(),"cash_message.check"));
            }

        } else if (player.isOp()) {
            switch (args[0]) {
                case "지급":
                    target = Bukkit.getPlayer(args[1]);

                    try {
                        amount = Integer.parseInt(args[2]);
                        if (target != null) {
                            Main.Cash = new ConfigManager("data/" + target.getUniqueId());

                            cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                            cash.increase(amount);

                            Main.Cash.getConfig().set("Cash", cash);
                            Main.Cash.saveConfig();

                            player.sendMessage(Util.replace(player, amount,"cash_message.send"));

                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;

                case "확인":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    player.sendMessage(Util.replace(target, cash.getCash(),"cash_message.check"));
                    break;

                case "제거":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    amount = Integer.parseInt(args[2]);
                    cash.Decrease(amount);

                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    player.sendMessage(Util.replace(player, amount,"cash_message.remove"));
                    break;
                case "설정":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    try{
                        amount = Integer.parseInt(args[2]);
                        cash.setCash(amount);


                        Main.Cash.getConfig().set("Cash", cash);
                        Main.Cash.saveConfig();

                        player.sendMessage(Util.replace(player, amount,"cash_message.set"));
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                    }

                    break;

            }
        }
        return false;
    }
}

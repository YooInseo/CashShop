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
                player.sendMessage(Util.replace(player, cash.getCash(), "cash_message.check"));
            } else {
                player.sendMessage(Util.replace(player, cash.getCash(), "cash_message.check"));
            }

        } else if (player.isOp()) {
            switch (args[0]) {
                case "지급":
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);

                        if (target != null) {
                            if(args.length > 2){
                                try {
                                    amount = Integer.parseInt(args[2]);
                                    if (target != null) {
                                        Main.Cash = new ConfigManager("data/" + target.getUniqueId());

                                        cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                                        cash.increase(amount);

                                        Main.Cash.getConfig().set("Cash", cash);
                                        Main.Cash.saveConfig();

                                        player.sendMessage(Util.replace(player, amount, "cash_message.send"));

                                    } else {
                                        return false;
                                    }
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            } else{
                                player.sendMessage(Main.config.getString("error_message.command_none_cash"));
                            }
                        }
                    } else{
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
                    }


                    break;

                case "확인":
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);

                        Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                        cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                        player.sendMessage(Util.replace(target, cash.getCash(), "cash_message.check_user"));
                    }
                    break;

                case "제거":
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);
                        try {
                            if (target != null) {
                                if (args.length > 2) {
                                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                                    amount = Integer.parseInt(args[2]);
                                    cash.Decrease(amount);

                                    Main.Cash.getConfig().set("Cash", cash);
                                    Main.Cash.saveConfig();
                                    player.sendMessage(Util.replace(player, amount, "error_message.remove"));
                                } else{
                                    player.sendMessage(Main.config.getString("error_message.command_none_cash"));
                                }

                            } else {
                                return false;
                            }

                        } catch (NumberFormatException e) {
                            return false;
                        }
                    } else{
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
                    }


                    break;
                case "설정":
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);

                        try {
                            if (target != null) {
                                if (args.length > 2) {
                                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                                    amount = Integer.parseInt(args[2]);
                                    cash.setCash(amount);


                                    Main.Cash.getConfig().set("Cash", cash);
                                    Main.Cash.saveConfig();

                                    player.sendMessage(Util.replace(player, amount, "cash_message.set"));
                                } else{
                                    player.sendMessage(Main.config.getString("error_message.command_none_cash"));
                                }

                            } else {
                                return false;
                            }

                        } catch (NumberFormatException e) {
                            return false;
                        }
                    } else{
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
                    }


                    break;

            }
        }
        return false;
    }
}

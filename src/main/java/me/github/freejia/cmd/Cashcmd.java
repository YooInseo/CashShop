package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.cmd.tab.CashTabComplete;
import me.github.freejia.data.Data;
import me.github.freejia.data.object.Cash;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.object.SendType;
import me.github.freejia.data.object.Type;
import me.github.freejia.data.object.log.AdminLog;
import me.github.freejia.data.object.log.UserLog;
import me.github.freejia.util.Util;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Cashcmd implements CommandExecutor {
    private Main plugin;

    public Cashcmd(Main plugin) {
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

                        if (args.length > 2) {
                            try {
                                Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                                cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                                amount = Integer.parseInt(args[2]);
                                if (amount + cash.getCash() <= Integer.MAX_VALUE - 1) {
                                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());


                                    cash.increase(amount);

                                    Main.Cash.getConfig().set("Cash", cash);
                                    Main.Cash.saveConfig();

                                    player.sendMessage(Util.replace(player, amount, "cash_message.send"));

                                    saveLog(player, target, SendType.add, amount);
                                } else {
                                    player.sendMessage(Main.config.getString("error_message.overflow"));
                                }

                            } catch (NumberFormatException e) {
                                return false;
                            }
                        } else {
                            player.sendMessage(Main.config.getString("error_message.command_none_cash"));
                        }

                    } else {
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
                    }


                    break;

                case "확인":
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);

                        Main.Cash = new ConfigManager("data/" + target.getUniqueId());

                        cash = Main.Cash.getConfig().getObject("Cash", Cash.class);

                        player.sendMessage(Util.replace(target, cash.getCash(), "cash_message.check_user"));
                    } else {
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
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
                                    player.sendMessage(Util.replace(player, amount, "cash_message.remove"));

                                    saveLog(player, target, SendType.remove, amount);
                                } else {
                                    player.sendMessage(Main.config.getString("error_message.command_none_cash"));
                                }

                            } else {
                                return false;
                            }

                        } catch (NumberFormatException e) {
                            return false;
                        }
                    } else {
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

                                    if (amount <= Integer.MAX_VALUE - 1) {
                                        cash.setCash(amount);
                                        Main.Cash.getConfig().set("Cash", cash);
                                        Main.Cash.saveConfig();
                                        saveLog(player, target, SendType.set, amount);
                                        player.sendMessage(Util.replace(player, amount, "cash_message.set"));
                                    } else {
                                        player.sendMessage(Main.config.getString("error_message.overflow"));
                                    }

                                } else {
                                    player.sendMessage(Main.config.getString("error_message.command_none_cash"));
                                }

                            } else {
                                return false;
                            }

                        } catch (NumberFormatException e) {
                            return false;
                        }
                    } else {
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
                    }

                    break;
                case "초기화":
                    if (args.length > 1) {
                        target = Bukkit.getPlayer(args[1]);
                        Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                        cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                        cash.setCash(0);
                        Main.Cash.getConfig().set("Cash", cash);
                        Main.Cash.saveConfig();
                        player.sendMessage(Util.replace(target, "cash_message.initalization"));

                        saveLog(player, target, SendType.initialization, 0);
                    } else {
                        player.sendMessage(Main.config.getString("error_message.command_none_player"));
                    }
                    break;

            }
        }
        return false;
    }

    public void saveLog(Player player, Player target, SendType sendType, int price) {
        ConfigManager config = Main.AdminLog = new ConfigManager("log/admin/" + player.getUniqueId());

        AdminLog adminLog = new AdminLog(player, target, sendType, price);


        if (!config.isExist()) {
            config.getConfig().set("Log", new ArrayList<AdminLog>());

            List<AdminLog> log = (List<AdminLog>) config.getConfig().getList("Log");

            log.add(adminLog);

            config.saveConfig();
        } else {
            List<AdminLog> log = (List<AdminLog>) config.getConfig().getList("Log");
            log.add(adminLog);

            config.saveConfig();
        }
    }
}

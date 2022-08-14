package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.data.Cash;
import me.github.freejia.data.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cmd implements CommandExecutor {
    private Main plugin;

    public cmd(Main plugin) {
        Bukkit.getPluginCommand("캐쉬").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        Player target;
        Cash cash = new Cash(player);

        if (args.length == 0) {
            Main.Cash = new ConfigManager("data/" + player.getUniqueId());

            cash = Main.Cash.getConfig().getObject("Cash", Cash.class);

            player.sendMessage(cash.getCash() + " ");

        } else if (player.isOp()) {
            switch (args[0]) {
                case "지급":
                    target = Bukkit.getPlayer(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());

                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    cash.increase(amount);

                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    break;
                case "확인":
                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    break;

                case "뺏기":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    cash.Decrease(Integer.parseInt(args[2]));

                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    break;
            }
        }
        return false;
    }
}

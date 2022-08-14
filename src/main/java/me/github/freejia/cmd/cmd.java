package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.data.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        Main.Cash = new ConfigManager("data/" + player.getUniqueId());

        if (args.length == 0) {

            if (!Main.Cash.file.exists()) {
                Main.Cash.getConfig().set("Cash", 0);
                Main.Cash.saveConfig();
            } else{
                player.sendMessage(Main.Cash.getConfig().getInt("Cash") +" ");
            }

        }
        if (player.isOp()) {
            switch (args[0]) {
                case "뺏기":

                    break;
                case "확인":

                    break;

                case "지급":

                    break;
            }
        }
        return false;
    }
}

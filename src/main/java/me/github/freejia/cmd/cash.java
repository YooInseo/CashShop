package me.github.freejia.cmd;

import me.github.freejia.Main;
import me.github.freejia.data.Cash;
import me.github.freejia.data.ConfigManager;
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
            if(cash == null){
                cash = new Cash(player);
                Main.Cash.getConfig().set("Cash",cash);
                Main.Cash.saveConfig();
            } else{
                player.sendMessage("당신의 캐쉬는 " + cash.getCash() + " 원 입니다.");
            }




        } else if (player.isOp()) {
            switch (args[0]) {
                case "지급":
                    target = Bukkit.getPlayer(args[1]);
                    amount = Integer.parseInt(args[2]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());

                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    cash.increase(amount);

                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    break;

                case "확인":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    player.sendMessage("" + cash.getCash() + "캐쉬 입니다.");
                    break;

                case "제거":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    cash.Decrease(Integer.parseInt(args[2]));

                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    break;
                case "설정":
                    target = Bukkit.getPlayer(args[1]);
                    Main.Cash = new ConfigManager("data/" + target.getUniqueId());
                    cash = Main.Cash.getConfig().getObject("Cash", Cash.class);
                    cash.setCash(Integer.parseInt(args[2]));

                    Main.Cash.getConfig().set("Cash", cash);
                    Main.Cash.saveConfig();
                    break;

            }
        }
        return false;
    }
}

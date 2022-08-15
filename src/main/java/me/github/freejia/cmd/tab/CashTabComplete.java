package me.github.freejia.cmd.tab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CashTabComplete implements TabCompleter {


    List<String> results = new ArrayList<>();
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player)sender;

        if(args.length == 1){
            if(player.isOp()){
                if(results.isEmpty()){
                    results.add("지급");
                    results.add("제거");
                    results.add("설정");
                    results.add("확인");
                }
            }
            return results;
        } else if (args.length == 2 && args.length != 1){
            results.remove("지급");
            results.remove("제거");
            results.remove("설정");
            results.remove("확인");

            for(Player online : Bukkit.getOnlinePlayers()){
                results.add(online.getDisplayName());
            }

            return results;
        } else if(args.length == 3 && args.length != 2 && args.length != 1){
            if(!results.isEmpty()){
                results.removeAll(results);
            }
            results.add("int");
        }

        return results;
    }
}

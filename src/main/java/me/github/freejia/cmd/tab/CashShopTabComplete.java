package me.github.freejia.cmd.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CashShopTabComplete implements TabCompleter {
    ArrayList<String> results =  new ArrayList<>();
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            results.add("생성");
            results.add("제거");
            results.add("GUI이름");
            results.add("줄");
            results.add("편집");
            return results;
        }
        return null;
    }
}

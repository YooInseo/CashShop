package me.github.freejia.cmd.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {


    List<String> results = new ArrayList<>();
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 1){

            results.add("생성");
            results.add("제거");
            results.add("줄");
            results.add("페이지");
            results.add("목록");
            results.add("리로드");
            results.add("아이템");
            return results;
        }

        return results;
    }
}

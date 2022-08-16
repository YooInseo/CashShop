package me.github.freejia.cmd.tab;

import me.github.freejia.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CashShopTabComplete implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> results =  new ArrayList<>();
        File dir = new File(Main.plugin.getDataFolder() + "/shop/");
        File files[] = dir.listFiles();

        if(args.length == 1){
            results.add("생성");
            results.add("제거");
            results.add("GUI이름");
            results.add("줄");
            results.add("편집");
            return results;
        } else if(args.length == 2){
            switch (args[0]){
                case "줄":
                    String[] filenames = dir.list();

                     for(String names : filenames){
                         names.replaceAll(".yml"," ");
                        results.add(names);

                    }

                    break;
            }
            return results;
        } else if (args.length == 3) {
            switch (args[0]){
                case "줄":
                    results.add("1");
                    results.add("2");
                    results.add("3");
                    results.add("4");

                    break;
            }

        }
        return null;
    }
}

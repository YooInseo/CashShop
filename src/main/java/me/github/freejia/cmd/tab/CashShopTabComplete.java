package me.github.freejia.cmd.tab;

import me.github.freejia.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CashShopTabComplete implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> results = new ArrayList<>();
        File dir = new File(Main.plugin.getDataFolder() + "/shop/");
        File files[] = dir.listFiles();

        if (args.length == 1) {
            if (sender.isOp()) {
                results.add("생성");
                results.add("제거");
                results.add("GUI이름");
                results.add("줄");
                results.add("편집");
            }

            results.add("열기");
            return results;
        } else if (args.length == 2) {
            if(files != null){
                add(files, results);
            }

            return results;
        } else if (args.length == 3) {

            if (args[0].equalsIgnoreCase("줄")) {
                results.add("1");
                results.add("2");
                results.add("3");
                results.add("4");
                results.add("5");
                results.add("6");

                return results;
            }

        }
        return null;
    }

    public void add(File[] files, ArrayList<String> results) {
        for (File file : files) {
            if(file != null){
                String name = FilenameUtils.getName(file.getPath());
                name = name.replaceAll(".yml", "");
                results.add(name);
            }

        }
    }
}

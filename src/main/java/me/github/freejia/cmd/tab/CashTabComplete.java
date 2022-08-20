package me.github.freejia.cmd.tab;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CashTabComplete implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        final List<String> completions = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], Arrays.asList("지급", "제거", "설정", "확인", "초기화"), completions);
                } else if (args.length == 2) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        completions.add(online.getDisplayName());
                    }

                } else if (args.length == 3) {
                    switch (args[0]) {
                        case "지급":
                            completions.add("<Amount>");
                            break;
                        case "제거":
                            completions.add("<Amount>");
                            break;
                        case "설정":
                            completions.add("<Amount>");
                            break;
                    }
                }

                Collections.sort(completions);
            }
        } else {
            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], Arrays.asList("지급", "제거", "설정", "확인", "초기화"), completions);
            } else if (args.length == 2) {
                for (OfflinePlayer online : Bukkit.getOfflinePlayers()) {
                    completions.add(online.getName());
                }

            } else if (args.length == 3) {
                switch (args[0]) {
                    case "지급":
                        completions.add("<Amount>");
                        break;
                    case "제거":
                        completions.add("<Amount>");
                        break;
                    case "설정":
                        completions.add("<Amount>");
                        break;
                }
            }
        }

        return completions;
    }
}

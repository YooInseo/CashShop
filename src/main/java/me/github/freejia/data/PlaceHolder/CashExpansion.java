package me.github.freejia.data.PlaceHolder;

import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.github.freejia.Main;
import me.github.freejia.data.Config.ConfigManager;
import me.github.freejia.data.Object.Cash;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CashExpansion extends PlaceholderExpansion implements Cacheable, Configurable {

    private Main plugin; // The instance is created in the constructor and won't be modified, so it can be final

    public CashExpansion(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Cash";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SkyExcel";
    }
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }


    @Override
    public String getRequiredPlugin() {
        return "CashShop";
    }

    @Override
    public boolean canRegister() {
        return (plugin = (Main) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("cash")){
            ConfigManager config = new ConfigManager("data/" + player.getUniqueId());
            Cash cash = config.getConfig().getObject("Cash", Cash.class);

            return cash.getCash() + "";
        }



        return null; // Placeholder is unknown by the expansion
    }

    @Override
    public void clear() {

    }

    @Override
    public Map<String, Object> getDefaults() {
        final Map<String, Object> defaults = new HashMap<>();
        defaults.put("","");
        return defaults;
    }
}

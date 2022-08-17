package me.github.freejia.data.object.log;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.object.Items;
import me.github.freejia.util.Util;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserLog implements ConfigurationSerializable {

    private Player player;

    private Items item;
    private int Amount;

    public UserLog(Player player, Items item, int amount) {
        this.player = player;
        this.item = item;
        this.Amount = amount;
    }


    public void saveLog(){
        Main.UserLog = new ConfigManager("log/user/" + player.getUniqueId());
        Main.UserLog.getConfig().set("Log",new ArrayList<>());

    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", Util.getDate());
        if(item.getMeta() != null){
            map.put("item_name",item.getMeta().getDisplayName());
        } else{
            map.put("item_type",item.getMaterial());
        }
        map.put("amount", Amount);
        return null;
    }
}

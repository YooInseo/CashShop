package me.github.freejia.data.Object;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

public class Cash implements ConfigurationSerializable {
    private long Cash;
    private Player player;

    public Cash(Player player) {

        this.player = player;
    }
    public Cash(Player player,long Cash) {

        this.player = player;
        this.Cash = Cash;
    }
    public long getCash() {
        return Cash;
    }


    public void increase(long Amount) {
        Cash += Amount;
    }

    public boolean Decrease(long Amount) {
        if(Cash > 0){
            Cash -= Amount;
            return true;
        }
        return false;

    }

    public void setCash(long cash) {
        Cash = cash;
    }
    public Cash(Map<String, Object> map){
        this((Player)map.get("Player"),(int)map.get("Cash"));
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Cash", Cash);
        map.put("Player", player);
        return map;
    }
}

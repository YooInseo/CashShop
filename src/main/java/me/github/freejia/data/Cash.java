package me.github.freejia.data;

import me.github.freejia.Main;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Cash implements ConfigurationSerializable {
    private int Cash;
    private Player player;

    public Cash(Player player) {

        this.player = player;
    }
    public Cash(Player player,int Cash) {

        this.player = player;
        this.Cash = Cash;
    }
    public int getCash() {
        return Cash;
    }


    public void increase(int Amount) {
        Cash = Cash + Amount;
    }

    public void Decrease(int Amount) {
        Cash -= Amount;
    }

    public void setCash(int cash) {
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

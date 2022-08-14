package me.github.freejia.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CashShop implements ConfigurationSerializable {

    private String name;
    private Inventory inv;
    private String title;
    private int line; // Max 45
    private int Page;
    private Player player;

    public CashShop(String name,  Player player) {
        this.name = name;
        this.title = name;
        this.player = player;
    }



    public void Create(){
        Inventory inv = Bukkit.createInventory(null,line + 9, name);
        this.title = name;

        player.openInventory(inv);
    }

    public void setLine(int line) {
        this.line = line;
    }
    public void Editor(){
        Inventory inv = Bukkit.createInventory(null,line + 9, "Editor + " + name);
        this.title = "Editor + " + name;

        player.openInventory(inv);
    }
    public String getTitle() {
        return title;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("inv",inv);
        map.put("line",line);
        map.put("Page",Page);
        map.put("Title",title);
        return map;
    }
}

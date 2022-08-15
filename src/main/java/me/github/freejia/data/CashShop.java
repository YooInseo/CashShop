package me.github.freejia.data;

import me.github.freejia.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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


    public CashShop(String name,int line) {
        this.name = name;
        this.title = name;
        this.line = line;
    }


    public void Create(){
        Inventory inv = Bukkit.createInventory(null,line + 9, name);
        this.title = name;

        player.openInventory(inv);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLine(int line) {
        this.line = line;
    }
    public void Editor(){
        Inventory inv = Bukkit.createInventory(null,line + 9, "Editor : " + name);
        this.title = "Editor : " + name;


        player.openInventory(inv);
    }

    public void PriceGUI(){
        Inventory inv =  Bukkit.createInventory(null, Main.config.getConfig().getInt("shop_price.gui_size"), Main.config.getConfig().getString("shop_price.gui"));

        ItemStack sell = new ItemStack(Material.valueOf(Main.config.getConfig().getString("shop_price.buy_settings.item")));

//        inv.setItem();

        player.openInventory(inv);
    }
    public String getTitle() {
        return title;
    }

    public CashShop(Map<String, Object> map){
        this((String) map.get("GUI"),(Integer) map.get("GUI_SIZE"));
    }


    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("GUI",title);
        map.put("GUI_SIZE",line);

//        map.put("inv",inv);

        return map;
    }
}

package me.github.freejia.data.object.log;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.data.object.Items;
import me.github.freejia.data.object.Type;
import me.github.freejia.util.Util;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLog implements ConfigurationSerializable {

    private Player player;

    private ItemStack item;
    private int Amount;

    private Type type;

    private String typename;

    private String name;


    private int price;

    public UserLog(Player player, int amount, Type type, int price, ItemStack item) {
        this.player = player;

        this.Amount = amount;
        this.type = type;
        this.typename = type.name();
        this.price = price;
        this.item = item;
        if (item.hasItemMeta()) {
            this.name = item.getItemMeta().getDisplayName();
        } else {
            this.name = item.getType().name();
        }
    }

    public UserLog(int amount, int price, String purchase, String item) {


        this.Amount = amount;
        this.price = price;
        this.typename = purchase;
        this.name = item;
    }


    public UserLog(Map<String, Object> map) {
        this((Integer) map.get("amount"), (Integer) map.get("price"), (String) map.get("purchase"), (String) map.get("item_name"));
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("date", Util.getDate());
        map.put("item_name", name);

        map.put("price", price);
        map.put("amount", Amount);
        map.put("purchase", typename);


        return map;
    }
}

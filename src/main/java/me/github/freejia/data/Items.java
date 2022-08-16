package me.github.freejia.data;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items implements ConfigurationSerializable {

    private String name;

    private Material material;

    private List<String> lore;

    private int slot;

    private ItemMeta meta;

    public Items(ItemStack item,int slot ){
        ItemMeta itemmeta = item.getItemMeta();
        name = itemmeta.getDisplayName();
        material = item.getType();
        lore = itemmeta.getLore();
        meta = itemmeta;
        this.slot = slot;
    }

    public Items(String name, Material material, List<String> lore,ItemMeta meta,int slot){
        this.name = name;
        this.material = material;
        this.lore = lore;
        this.meta = meta;
        this.slot = slot;
    }

    public void setSlot(int slot) {
        slot = slot;
    }

    public Items(Map<String, Object> map) {
        this((String)map.get("name"),(Material) map.get("type"),(List<String>)map.get("lore"), (ItemMeta) map.get("itemmeta"), (Integer)map.get("slot"));
    }

    public int getSlot() {
        return slot;
    }

    public ItemMeta getMeta() {
        return meta;
    }

    public List<String> getLore() {
        return lore;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("slot",slot);
        map.put("type",material.toString());
        map.put("lore",lore);
        map.put("itemmeta",meta);
        return map;
    }
}

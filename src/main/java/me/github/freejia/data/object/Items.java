package me.github.freejia.data.object;

import me.github.freejia.Main;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items implements ConfigurationSerializable {

    private String name;

    private String material;

    private List<String> lore;

    private int slot;

    private ItemMeta meta;

    private int Amount;

    private int sellprice;

    private int buyprice;

    private ItemStack item;
    public void setBuyprice(int buyprice) {
        this.buyprice = buyprice;

    }

    public int getBuyprice() {
        return buyprice;
    }

    public int getSellprice() {
        return sellprice;
    }

    public void setSellprice(int sellprice) {
        this.sellprice = sellprice;
    }



    public ItemStack getItem() {
        return item;
    }

    public Items(ItemStack item, int slot) {
        this.item = item;
        if(item.hasItemMeta()){
            ItemMeta itemmeta = item.getItemMeta();
            name = itemmeta.getDisplayName();
            lore = itemmeta.getLore();
            meta = itemmeta;
        }


        material = item.getType().name();

        this.Amount = item.getAmount();
        this.slot = slot;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Items(String name, String material, List<String> lore, ItemMeta meta, int slot, int Amount, int sellprice, int buyprice) {
        this.name = name;
        this.material = material;
        this.lore = lore;
        this.meta = meta;
        this.slot = slot;
        this.Amount = Amount;
        this.sellprice = sellprice;
        this.buyprice = buyprice;
    }

    public void setMeta(ItemMeta meta) {
        this.meta = meta;
    }


    public void setSlot(int slot) {
        slot = slot;
    }

    public Items(Map<String, Object> map) {
        this((String) map.get("name"), (String) map.get("type"), (List<String>) map.get("lore"), (ItemMeta) map.get("itemmeta"), (Integer) map.get("slot"), (Integer) map.get("Amount"),
                (Integer) map.get("sellprice"), (Integer) map.get("buyprice"));
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

    public String getMaterial() {
        return material;
    }


    public String getName() {
        return name;
    }

    public int getAmount() {
        return Amount;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("slot", slot);
        map.put("lore", lore);
        map.put("Amount", Amount);
        map.put("type", material.toString());

        map.put("itemmeta", meta);
        map.put("sellprice", sellprice);
        map.put("buyprice", buyprice);

        return map;
    }
}

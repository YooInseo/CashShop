package me.github.freejia.data.Object;

import me.github.freejia.Main;
import me.github.freejia.data.Config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CashShop implements ConfigurationSerializable {

    private String name;
    private Inventory inv;
    private String title;
    private int line; // Max 45
    private int Page;
    private Player player;
    private long price;

    private Inventory EditorInv;
    private String EditorTitle;
    private Inventory PriceInv;

    private boolean isPrice = false;

    private List<Items> items = new ArrayList<>();



    public CashShop(String name, Player player) {
        this.name = name;
        this.title = name;
        this.player = player;
    }


    public CashShop(String name, int line, List<Items> items) {
        this.name = name;
        this.title = name;
        this.line = line;
        this.items = items;

    }


    public void Create() {
        Inventory inv = Bukkit.createInventory(null, line + 9, name);
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

    public void Editor() {
        isPrice = false;
        if (items != null) {
            if (EditorInv == null) {
                Inventory inv = Bukkit.createInventory(null, line * 9, "Editor : " + name);
                this.EditorTitle = "Editor : " + name;
                this.EditorInv = inv;
                player.openInventory(inv);
            } else {
                player.openInventory(EditorInv);
            }
        } else {
            ConfigManager shop = new ConfigManager("shop/" + name);

            Inventory inv = Bukkit.createInventory(null, line * 9, "Editor : " + name);


            this.EditorTitle = "Editor : " + name;
            this.EditorInv = inv;
            for(Items items:  items){
                ItemStack itemStack = new ItemStack(Material.valueOf(items.getMaterial()));
                itemStack.setAmount(items.getAmount());
                inv.setItem(items.getSlot(),itemStack);
            }
            player.sendMessage("test");

            player.openInventory(inv);
        }
    }

    public void Open() {

        Inventory inv = Bukkit.createInventory(null, line * 9, name);
        for(Items items:  items){
            ItemStack itemStack = new ItemStack(Material.valueOf(items.getMaterial()));
            itemStack.setAmount(items.getAmount());
            inv.setItem(items.getSlot(),itemStack);
        }


        player.openInventory(inv);
    }

    public int getLine() {
        return line;
    }

    public void saveItem() {
        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);


        for (int i = 0; i < line * 9; i++) {
            ItemStack item = EditorInv.getItem(i);
            if (item != null) {


                Items items = new Items(item, i);
                items.setSlot(i);
                cashshop.getItems().add(items);

                player.sendMessage(EditorInv.getItem(i).getType().toString() + " : " + i);
            }
        }


        shop.getConfig().set("shop", cashshop);

        shop.saveConfig();
    }

    public List<Items> getItems() {
        return items;
    }

    public void setisPrice(boolean price) {
        isPrice = price;
    }

    public String getEditorTitle() {
        return EditorTitle;
    }

    public Inventory getEditorInv() {
        return EditorInv;
    }

    public void PriceGUI() {
        isPrice = true;
        Inventory inv = Bukkit.createInventory(null, Main.config.getConfig().getInt("shop_price.gui_size"), Main.config.getConfig().getString("shop_price.gui"));

        ItemStack sell = new ItemStack(Material.valueOf(Main.config.getConfig().getString("shop_price.sell_settings.item")));

        ItemMeta sellmeta = sell.getItemMeta();
        sellmeta.setDisplayName("§c판매가격");
        sell.setItemMeta(sellmeta);


        ItemStack buy = new ItemStack(Material.valueOf(Main.config.getConfig().getString("shop_price.buy_settings.item")));
        ItemMeta buymeta = sell.getItemMeta();
        buymeta.setDisplayName("§a구매가격");
        buy.setItemMeta(buymeta);

        inv.setItem(Main.config.getConfig().getInt("shop_price.sell_settings.slot"), sell);

        inv.setItem(Main.config.getConfig().getInt("shop_price.buy_settings.slot"), buy);
        player.openInventory(inv);
        this.PriceInv = inv;
    }

    public Inventory getPriceInv() {
        return PriceInv;
    }

    public boolean isPrice() {
        return isPrice;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public CashShop(Map<String, Object> map) {


        this((String) map.get("GUI"), (Integer) map.get("GUI_SIZE"), (List<Items>) map.get("items"));
    }


    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("GUI", title);
        map.put("GUI_SIZE", line);
        map.put("items", items);

//        map.put("inv",inv);

        return map;
    }
}

package me.github.freejia.data.Object;

import me.github.freejia.Main;
import me.github.freejia.data.Config.ConfigManager;
import me.github.freejia.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;

public class CashShop implements ConfigurationSerializable {

    private String name;
    private Inventory inv;
    private String title;
    private int line; // Max 45
    private Player player;
    private int price;

    private Inventory EditorInv;
    private String EditorTitle;
    private Inventory PriceInv;

    private boolean isPrice = false;

    private List<Items> items = new ArrayList<>();

    private Type type = Type.Default;


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


    public Type getType() {
        return type;
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
        ConfigManager shop = new ConfigManager("shop/" + name);
        shop.saveConfig();
    }

    public void Editor() {
        isPrice = false;
        if (items != null) {
            if (EditorInv == null) {
                Inventory inv = Bukkit.createInventory(null, line * 9, "Editor : " + name);
                this.EditorTitle = "Editor : " + name;
                this.EditorInv = inv;

                for (Items items : items) {
                    if (items != null) {
                        ItemStack itemStack = new ItemStack(Material.valueOf(items.getMaterial()));
                        itemStack.setAmount(items.getAmount());
                        inv.setItem(items.getSlot(), itemStack);
                    }

                }
                player.openInventory(inv);
            } else {
                player.openInventory(EditorInv);
            }
        } else {

            Inventory inv = Bukkit.createInventory(null, line * 9, "Editor : " + name);


            this.EditorTitle = "Editor : " + name;
            this.EditorInv = inv;

            player.sendMessage("test");

            player.openInventory(inv);
        }
    }


    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
        Main.plugin.getLogger().info("" + type + isDefualt());
    }

    public boolean isDefualt() {
        return this.type.equals(Type.Default);
    }

    public void Open() {

        Inventory inv = Bukkit.createInventory(null, line * 9, name);
        for (Items items : items) {
            ItemStack itemStack = new ItemStack(Material.valueOf(items.getMaterial()));
            itemStack.setAmount(items.getAmount());
            inv.setItem(items.getSlot(), itemStack);
        }

        player.openInventory(inv);
    }

    public int getLine() {
        return line;
    }

    public void saveItem() {
        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);

        List<Items> items = new ArrayList<>();
        for (int i = 0; i < line * 9; i++) {
            ItemStack itemStack = EditorInv.getItem(i);

            if (itemStack != null) {
                Items item = new Items(itemStack, i);
                items.clear();
                if (!items.contains(item)) {
                    items.add(item);
                }

                for (Items newitems : items) {
                    System.out.println( cashshop.items.add(newitems)  + " :  "+ newitems.getMaterial() );

                }

                if (cashshop.getItems().size() != 0) {

                    player.sendMessage("눌 아님");

//                    for (int size = 0; size < cashshop.getItems().size(); size++) {
//                        Items newitem = cashshop.getItems().get(size);
//
//                        cashshop.items.set(size, newitem);
//                        shop.getConfig().set("shop", newitem);
//                        shop.saveConfig();
//
//                        player.sendMessage("test" + newitem.getBuyprice());
//                    }
                } else {


                    shop.getConfig().set("shop", cashshop);
                    shop.saveConfig();

                }

            } else { // 슬롯 삭제
                for (int size = 0; size < cashshop.getItems().size(); size++) {
                    Items item = cashshop.getItems().get(size);
                    if (i == item.getSlot()) {
                        cashshop.items.remove(item);
                        shop.getConfig().set("shop", cashshop);
                        shop.saveConfig();
                    }
                }

            }

//            if (itemStack != null) {
//
//
//                for (Items items : cashshop.getItems()) {
//                    if(items == null){
//                        player.sendMessage("아이템 값이 없습니다");
//                    }
//                    if(items.getSlot() != i){
//                        Items item = new Items(itemStack, i);
//                        cashshop.items.add(item);
//                        player.sendMessage("Test");
//                        shop.getConfig().set("shop", cashshop);
//                        shop.saveConfig();
//                    }
//                }
//
//                Items item = new Items(itemStack,i);
//                cashshop.items.clear();
//                cashshop.items.add(item);
//                shop.getConfig().set("shop", cashshop);
//                shop.saveConfig();
//                player.sendMessage("test");
//
//            } else {
//                player.sendMessage("null");
//                for (Items items : cashshop.getItems()) {
//                    if (items.getSlot() != i) {
//                        Items item = new Items(itemStack, i);
//
//                        item.setBuyprice(items.getBuyprice());
//                        item.setSellprice(items.getSellprice());
//
//                        cashshop.items.clear();
//                        cashshop.items.add(item);
//                        shop.saveConfig();
//                    }
//                }
//
//            }
        }
//        for (Items items : cashshop.getItems()) {
//            if (items != null) {
//                int indeex = cashshop.getItems().indexOf(items);
//
//                for (int i = 0; i < line * 9; i++) {
//
//                    if (items.getSlot() == i) {
//
//                        Items item = new Items(EditorInv.getItem(i), i);
//
//                        item.setSellprice(items.getSellprice());
//
//                        cashshop.items.clear();
//                        cashshop.items.add(item);
//                        shop.getConfig().set("shop", cashshop);
//                        shop.saveConfig();
//                        player.sendMessage("test");
//                    }
//                }
//            } else {
//                player.sendMessage("test");
//            }
//        }
////
//        for (int i = 0; i < line * 9; i++) {
//            ItemStack itemstack = EditorInv.getItem(i);
//
//            if(itemstack != null){
//
//                Items item = new Items(itemstack, i);
//
//                player.sendMessage(getItems().get(0) + "");
//
//                if(!this.getItems().contains(item)){
//                    cashshop.items.clear();
//                    cashshop.items.add(item);
//                }
//                shop.getConfig().set("shop", cashshop);
//                shop.saveConfig();
//            }
//
//        }
    }

    public CashShop getCashShop() {
        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);

        return cashshop;
    }


    public void UpdateItem() {
        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);


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

    public void setPrice(int price, int i) {

        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);


        cashshop.price = price;
        Items item = getItems().get(i);
        item.setSellprice(price);
        cashshop.items.set(i, item);

        shop.saveConfig();
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

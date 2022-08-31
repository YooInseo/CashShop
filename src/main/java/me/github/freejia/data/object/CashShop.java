package me.github.freejia.data.object;

import me.github.freejia.Main;
import me.github.freejia.data.config.ConfigManager;
import me.github.freejia.util.Util;
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
                        ItemMeta itemmeta = itemStack.getItemMeta();
                        itemmeta.setDisplayName(items.getName());
                        itemStack.setAmount(items.getAmount());
                        itemStack.setItemMeta(itemmeta);
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

    }

    public boolean isDefualt() {
        return this.type.equals(Type.Default);
    }

    public void Open() {

        Inventory inv = Bukkit.createInventory(null, line * 9, name);

        for (Items items : items) {

            ItemStack itemStack = new ItemStack(Material.valueOf(items.getMaterial()));
            List<String> lores = Main.config.getConfig().getStringList("cash_shop_message.defualt_lore");


            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(items.getName());
            meta.setLore(Util.replace(lores, items.getBuyprice(), items.getSellprice()));
            itemStack.setItemMeta(meta);
            itemStack.setAmount(items.getAmount());
            inv.setItem(items.getSlot(), itemStack);

            if (items.getBuyprice() == -1 && items.getSellprice() == -1) {

                ItemStack cantitemStack = new ItemStack(Material.valueOf(items.getMaterial()));
                List<String> cantlores = Main.config.getConfig().getStringList("cash_shop_message.both");

                ItemMeta cantmeta = cantitemStack.getItemMeta();
                cantmeta.setLore(Util.replace(cantlores, items.getBuyprice(), items.getSellprice()));
                cantmeta.setDisplayName(items.getName());
                itemStack.setItemMeta(cantmeta);

                itemStack.setAmount(items.getAmount());


                inv.setItem(items.getSlot(), itemStack);
            } else if (items.getSellprice() == -1) {

                ItemStack cantitemStack = new ItemStack(Material.valueOf(items.getMaterial()));
                List<String> cantlores = Main.config.getConfig().getStringList("cash_shop_message.cant_sell");

                ItemMeta cantmeta = cantitemStack.getItemMeta();
                cantmeta.setLore(Util.replace(cantlores, items.getBuyprice(), items.getSellprice()));
                cantmeta.setDisplayName(items.getName());
                itemStack.setItemMeta(cantmeta);

                itemStack.setAmount(items.getAmount());
                inv.setItem(items.getSlot(), itemStack);

            } else if (items.getBuyprice() == -1) {

                ItemStack cantitemStack = new ItemStack(Material.valueOf(items.getMaterial()));
                List<String> cantlores = Main.config.getConfig().getStringList("cash_shop_message.cant_buy");

                ItemMeta cantmeta = cantitemStack.getItemMeta();
                cantmeta.setLore(Util.replace(cantlores, items.getBuyprice(), items.getSellprice()));
                itemStack.setItemMeta(cantmeta);

                itemStack.setAmount(items.getAmount());


                inv.setItem(items.getSlot(), itemStack);
            }

        }

        player.openInventory(inv);
    }

    public void saveItem() {
        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);

        List<Items> items = new ArrayList<>();
        int index = cashshop.items.size();
        for (int i = 0; i < line * 9; i++) {

            ItemStack itemStack = EditorInv.getItem(i);
            if (itemStack != null) {
                List<Items> array = cashshop.items; // 아이템을 지우기 전, 정보를 전달

                Items item;

                item = new Items(itemStack, i);

                item.setSlot(i);
                for (Items newarray : array) {
                    if (newarray.getSlot() == item.getSlot()) {
                        item.setBuyprice(newarray.getBuyprice());
                        item.setSellprice(newarray.getSellprice());
                    }
                }
                items.add(item);

                if (cashshop.items.size() != 0) {
                    if (items.size() == cashshop.items.size()) { // 아이템이 증가 혹은 감소가 되지 않았을 경우.


                        cashshop.items.clear();
                        for (Items newitems : items) {

                            cashshop.items.add(newitems);
                            player.sendMessage(newitems.getBuyprice() + "");
                        }
                    } else {
                        if (items.size() >= cashshop.items.size()) { // GUI에 있는 아이템 사이즈가 List에 있는 사이즈보다 클때
                            index++;
                            cashshop.items.add(item);
                        }
                    }
                } else { // List에 있는 사이즈가 0 일때
                    index = 0;
                    if (items.size() != index) {
                        index++;
                        Items newitem = items.get(index - 1);
                        cashshop.items.add(newitem);
                    }
                }
                shop.getConfig().set("shop", cashshop);
                shop.saveConfig();
            } else { // Inventory 아이템이 null일 경우, 원래 list에 있던 아이템인지 체크 후 있으면 원래 list에서 삭제.
                for (int size = 0; size < cashshop.getItems().size(); size++) {
                    Items item = cashshop.getItems().get(size);
                    if (i == item.getSlot()) {
                        cashshop.items.remove(item);
                        shop.getConfig().set("shop", cashshop);
                        shop.saveConfig();

                    }
                }
            }
        }
    }

    public CashShop getCashShop() {
        ConfigManager shop = new ConfigManager("shop/" + name);

        CashShop cashshop = shop.getConfig().getObject("shop", CashShop.class);

        return cashshop;
    }

    public List<Items> getItems() {
        return items;
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

        List<String> sell_lore = Main.config.getConfig().getStringList("shop_price.sell_settings.lore");

        sellmeta.setLore(Util.replace(sell_lore));

        sell.setItemMeta(sellmeta);


        ItemStack buy = new ItemStack(Material.valueOf(Main.config.getConfig().getString("shop_price.buy_settings.item")));
        ItemMeta buymeta = sell.getItemMeta();

        List<String> buy_lore = Main.config.getConfig().getStringList("shop_price.buy_settings.lore");
        buymeta.setDisplayName("§a구매가격");

        buymeta.setLore(Util.replace(buy_lore));
        buy.setItemMeta(buymeta);

        inv.setItem(Main.config.getConfig().getInt("shop_price.sell_settings.slot"), sell);

        inv.setItem(Main.config.getConfig().getInt("shop_price.buy_settings.slot"), buy);
        player.openInventory(inv);
        this.PriceInv = inv;
    }

    public boolean isPrice() {
        return isPrice;
    }

    public String getTitle() {
        return title;
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

        return map;
    }
}

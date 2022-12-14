package me.github.freejia.data.config;

import me.github.freejia.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    public FileConfiguration config;
    public File file;
    private String name;

    public ConfigManager(String name) {
        this.name = name;

    }

    public void reloadConfig() {
        if (this.config == null)
            this.file = new File(Main.plugin.getDataFolder(), name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);

        InputStream inputStream = Main.plugin.getResource(name + ".yml");
        if (inputStream != null) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            this.config.setDefaults(config);
        }
    }
    public boolean isExist(){
        this.file = new File(Main.plugin.getDataFolder(), name + ".yml");
        return this.file.exists();
    }

    public Object load(String path) {
        this.config = YamlConfiguration.loadConfiguration(this.file);
        return config.getDefaults().get(path);
    }

    public void delete() {
        this.file = new File(Main.plugin.getDataFolder(), name + ".yml");
        this.file.delete();

    }

    public void rename(String name) {
        this.file = new File(Main.plugin.getDataFolder(), this.name + ".yml");
        File file = new File(Main.plugin.getDataFolder(), name + ".yml");


        this.file.renameTo(file);
    }

    public FileConfiguration getConfig() {
        if (this.config == null) reloadConfig();
        return config;
    }

    public boolean saveConfig() {
        if (this.config == null || this.file == null) return false;
        try {
            getConfig().save(this.file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveDefualtConfig() {
        if (this.file == null)
            this.file = new File(Main.plugin.getDataFolder(), name + ".yml");
        if (!this.file.exists()) {
            file = new File(Main.plugin.getDataFolder(), name + ".yml");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setString(String path, String msg) {
        if (getConfig().get(path) == null) {
            getConfig().set(path, msg);
            this.saveConfig();
        }
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public boolean Delete() {
        this.file = new File(Main.plugin.getDataFolder(), name + ".yml");

        return this.file.delete();
    }

    public Object get(String path) {
        return this.config.get(path);
    }


    @NotNull
    public <T extends Object> List<T> getList(@NotNull String path, Class<T> clazz) {
        List list = getConfig().getList(path);

        return (List<T>) clazz.cast(list);
    }

    public void removeObject(String path, Object obj) {
        List<Object> list = (List<Object>) this.getConfig().getList(path);
        list.remove(obj);
        this.saveConfig();
    }

    public void Value(String path, Object value) {
        this.getConfig().set(path, value);
        this.saveConfig();
    }


    public void newArrayList(String path) {
        if (getConfig().getList(path) == null) {
            this.getConfig().set(path, new ArrayList<>());
            this.saveConfig();
        } else {
            System.out.print("?????? ???????????? ?????? ?????? ???????????????.");
        }
    }

    /***
     *@implSpec {@code This method Get A}
     * <pre>{@code
     *
     *   ConfigManager config;
     *   public void CreatePlayer(Player player){
     *   config = new ConfigManager(player.getUniqueId().toString());
     *   config.newArrayList("player", player);
     *   }
     *   public void addPlayer(Player player){
     *   config.addObject("player", player);
     *   }
     *
     * }</pre>
     * @return If the object is contained in List from path
     */
    public boolean addObject(String path, Object obj) {
        List<Object> list = (List<Object>) this.getConfig().getList(path);
        if (!list.contains(obj)) {
            list.add(obj);
        }

        return this.saveConfig();
    }


    public boolean setObject(String path, Object obj) {
        List<Object> list = (List<Object>) this.getConfig().getList(path);
        if (!list.contains(obj)) {
            list.set(list.indexOf(obj), obj);
        }

        return this.saveConfig();
    }

    public boolean setObject(String path, Object obj, int index) {
        List<Object> list = (List<Object>) this.getConfig().getList(path);
        list.set(index, obj);
        return this.saveConfig();
    }
}

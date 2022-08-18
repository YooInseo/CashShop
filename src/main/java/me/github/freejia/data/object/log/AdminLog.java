package me.github.freejia.data.object.log;

import me.github.freejia.data.object.SendType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AdminLog implements ConfigurationSerializable {


    private Player sender;

    private Player target;
    private SendType sendtype;
    private String sendtypename;
    private String sendername;

    private String targetname;
    private int price;

    public AdminLog(Player sender, Player target, SendType sendtype, int price) {
        this.sender = sender;
        this.sendername = sender.getDisplayName();
        this.target = target;
        this.targetname = target.getDisplayName();
        this.sendtype = sendtype;
        this.sendtypename = sendtype.name();
        this.price = price;
    }


    public AdminLog(String sender, String target, String type, int price) {
        this.sendername = sender;
        this.targetname = target;
        this.sendtypename = type;
        this.price = price;
    }

    public AdminLog(Map<String, Object> map) {
        this((String) map.get("sender"), (String) map.get("target"), (String) map.get("sendType"),(Integer) map.get("price"));
    }


    @NotNull
    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sender", sendername);
        map.put("sendType", sendtypename);
        map.put("target", targetname);

        map.put("price", price);



        return map;
    }
}

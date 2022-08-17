package me.github.freejia.data.sql;

import me.github.freejia.Main;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySql {


    private String db = "";
    private String password  = "";
    private String user = "root";
    private String url = "jdbc:mysql://localhost:3306/" + db;

    static Connection connection;



    public void Connect(){
        if(Main.database.getConfig().getBoolean("Mysql.enabled")){
            try {

                password =  Main.database.getString("Mysql.mysql.password");
                user = Main.database.getString("Mysql.mysql.username");
                db = Main.database.getString("Mysql.mysql.database");
                connection = DriverManager.getConnection(url, user, password);


                Bukkit.getConsoleSender().sendMessage("§aDatabase Connect" + db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

//    public static PreparedStatement preparedStatement(SQL sql) throws SQLException {
//        PreparedStatement stmt = connection.prepareStatement(sql.getSql());
//
//         return stmt;
//    }
    public static Statement createStatement() throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt;
    }
}

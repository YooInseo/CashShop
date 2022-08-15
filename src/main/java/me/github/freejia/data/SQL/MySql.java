package me.github.freejia.data.SQL;

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
        try { // try catch to get any SQL errors (for example connections errors)
            password =  Main.database.getString("Mysql.mysql.password");
            user = Main.database.getString("Mysql.mysql.username");
            db = Main.database.getString("Mysql.mysql.database");
            connection = DriverManager.getConnection(url, user, password);
            // with the method getConnection() from DriverManager, we're trying to set
            // the connection's url, username, password to the variables we made earlier and
            // trying to get a connection at the same time. JDBC allows us to do this.
            Bukkit.getConsoleSender().sendMessage("§aDatabase Connect" + db);
        } catch (SQLException e) { // catching errors
            e.printStackTrace(); // prints out SQLException errors to the console (if any)
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

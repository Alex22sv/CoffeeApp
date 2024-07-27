package com.alex22sv.coffeeapp.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url = Config.DRIVER.value + ":" + Config.DATABASE_MANAGER.value + "://" + Config.SERVER.value + ":" + Config.PORT.value + "/" + Config.DATABASE.value;
    public static Connection getConnection() {
        try {
            /*System.out.println(url);
            System.out.println(Config.USERNAME.value);
            System.out.println(Config.PASSWORD.value);*/
            return DriverManager.getConnection(url, Config.USERNAME.value, Config.PASSWORD.value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

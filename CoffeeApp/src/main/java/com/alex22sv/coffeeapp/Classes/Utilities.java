package com.alex22sv.coffeeapp.Classes;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilities {
    public static boolean existsConsumedCoffee(int consumedCoffeeId) throws SQLException{
        boolean flag = false;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT * FROM ConsumedCoffee WHERE consumedCoffeeId = ?)");
        preparedStatement.setInt(1, consumedCoffeeId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        flag = resultSet.getBoolean(1);
        connection.close();
        return flag;
    }
}

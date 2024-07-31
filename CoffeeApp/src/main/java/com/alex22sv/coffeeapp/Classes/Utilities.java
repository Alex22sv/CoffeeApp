package com.alex22sv.coffeeapp.Classes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utilities {
    public static boolean existsConsumedCoffee(int consumedCoffeeId) throws SQLException{
        boolean flag = false;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT * FROM ConsumedCoffee WHERE consumedCoffeeId = ?)");
        preparedStatement.setInt(1, consumedCoffeeId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            flag = resultSet.getBoolean(1);
        } else {
            flag = false;
        }
        connection.close();
        return flag;
    }
    public static boolean existsCoffeeBrand(int coffeeBrandId) throws SQLException{
        boolean flag = false;
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS(SELECT * FROM CoffeeBrand WHERE coffeeBrandId = ?)");
        preparedStatement.setInt(1, coffeeBrandId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            flag = resultSet.getBoolean(1);
        } else {
            flag = false;
        }
        connection.close();
        return flag;
    }
    public static void logAction(AuditLogAction action) {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);

            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO AuditLog (`action`, `user`, `date`) VALUES (?, ?, ?)");
            preparedStatement.setString(1, action.toString());
            preparedStatement.setString(2, Config.USERNAME.value);
            preparedStatement.setTimestamp(3, timestamp);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

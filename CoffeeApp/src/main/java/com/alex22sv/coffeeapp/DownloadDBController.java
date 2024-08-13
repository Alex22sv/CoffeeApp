package com.alex22sv.coffeeapp;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alex22sv.coffeeapp.Classes.DatabaseConnection;
import com.alex22sv.coffeeapp.Classes.Utilities;
import com.alex22sv.coffeeapp.Enums.AuditLogAction;
import com.alex22sv.coffeeapp.Enums.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class DownloadDBController extends Controller {
    // Download database
    @FXML
    private Button downloadConsumedCoffeesButton;
    @FXML
    private Button downloadCoffeeBrandsButton;
    @FXML
    private Button downloadPreparationMethodsButton;
    // File
    private File file;
    // Database info
    @FXML
    private Label databaseUsername;
    @FXML
    private Label databaseServer;
    @FXML
    private Label databaseName;
    // App Version
    @FXML
    private Label appVersion;
    // Initialize
    @FXML
    private void initialize(){
        // Database info
        databaseUsername.setText("User: " + Config.USERNAME.value);
        databaseServer.setText("Server: " + Config.SERVER.value);
        databaseName.setText("Database: " + Config.DATABASE.value);
        // App version
        appVersion.setText(Config.APP_VERSION.value);
        /*// Audit log
        Utilities.logAction(AuditLogAction.OPENED_DOWNLOAD_DATABASE);*/
    }

    // Download consumed coffees
    public void downloadConsumedCoffees(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Where do you want to save this file?");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*.*")
            );
            file = fileChooser.showSaveDialog(new Stage());
            FileWriter fileWriter = new FileWriter(file, false);
            Connection connection = DatabaseConnection.getConnection();
            java.sql.Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ConsumedCoffee");
            while(resultSet.next()){
                fileWriter.write(resultSet.getString("consumedCoffeeId")+";"+resultSet.getString("coffeeName")+";"+
                resultSet.getString("coffeeBrandId")+";"+resultSet.getString("preparationMethodId")+";"+
                resultSet.getString("consumedCoffeeDate")+"\n");
                
            }
            fileWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved file.");
            alert.setTitle("Database download");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (NullPointerException e){
            // Something
        } catch (IOException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    // Download coffee brands
    public void downloadCoffeeBrands(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Where do you want to save this file?");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*.*")
            );
            file = fileChooser.showSaveDialog(new Stage());
            FileWriter fileWriter = new FileWriter(file, false);
            Connection connection = DatabaseConnection.getConnection();
            java.sql.Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CoffeeBrand");
            while(resultSet.next()){
                fileWriter.write(resultSet.getString("coffeeBrandId")+";"+resultSet.getString("coffeeBrandName")+"\n");
                
            }
            fileWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved file.");
            alert.setTitle("Database download");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (NullPointerException e){
            // Something
        } catch (IOException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    // Download preparation methods
    public void downloadPreparationMethods(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Where do you want to save this file?");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
                new FileChooser.ExtensionFilter("All files", "*.*")
            );
            file = fileChooser.showSaveDialog(new Stage());
            FileWriter fileWriter = new FileWriter(file, false);
            Connection connection = DatabaseConnection.getConnection();
            java.sql.Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PreparationMethod");
            while(resultSet.next()){
                fileWriter.write(resultSet.getString("preparationMethodId")+";"+resultSet.getString("preparationMethodName")+"\n");
                
            }
            fileWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved file.");
            alert.setTitle("Database download");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (NullPointerException e){
            // Something
        } catch (IOException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

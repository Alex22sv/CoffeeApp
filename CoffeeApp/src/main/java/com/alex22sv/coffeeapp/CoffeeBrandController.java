package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Classes.*;
import com.alex22sv.coffeeapp.Enums.AuditLogAction;
import com.alex22sv.coffeeapp.Enums.Config;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class CoffeeBrandController extends Controller {
    // Add coffee brand
     @FXML
     private TextField addCoffeeBrandName;
     @FXML
     private Button addCoffeeBrandButton;
    // Update coffee brand
    @FXML
    private TextField updateCoffeeBrandId;
    @FXML
    private Button updateCoffeeBrandIdButton;
    @FXML
    private TextField updateCoffeeBrandName;
    @FXML
    private Button updateCoffeeBrandButton;
    // Delete coffee brand
    @FXML
    private TextField deleteCoffeeBrandId;
    @FXML
    private Button deleteCoffeeBrandButton;
    // Table
    @FXML
    private TableView<CoffeeBrand> coffeeBrandTableView;
    @FXML
    private TableColumn<CoffeeBrand, Integer> coffeeBrandIdColumn;
    @FXML
    private TableColumn<CoffeeBrand, String> coffeeBrandNameColumn;
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
        // Table cells
        coffeeBrandIdColumn.setCellValueFactory(new PropertyValueFactory<>("coffeeBrandId"));
        coffeeBrandNameColumn.setCellValueFactory(new PropertyValueFactory<>("coffeeBrandName"));
        // Database info
        databaseUsername.setText("User: " + Config.USERNAME.value);
        databaseServer.setText("Server: " + Config.SERVER.value);
        databaseName.setText("Database: " + Config.DATABASE.value);
        // App version
        appVersion.setText(Config.APP_VERSION.value);
        /*// Audit log
        Utilities.logAction(AuditLogAction.OPENED_COFFEE_BRAND);*/
        // Update table view
        updateTable();
    }
    // Add coffee brand
    @FXML
    public void addCoffeeBrand(){
        if(!addCoffeeBrandName.getText().isEmpty()){
            try{
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CoffeeBrand (`coffeeBrandName`) VALUES (?)");
                preparedStatement.setString(1, addCoffeeBrandName.getText());
                preparedStatement.execute();
                // Clear items
                addCoffeeBrandName.clear();
                connection.close();
                updateTable();
                Utilities.logAction(AuditLogAction.ADDED_COFFEE_BRAND);
                successfullOperation();
            } catch(SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_ADD_COFFEE_BRAND);
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }
        } else {
            emptyOperation();
        }
    }
    // Update coffee brand
    @FXML
    public void updateCoffeeBrandGetOldValues(){
        if(!updateCoffeeBrandId.getText().isEmpty()){
            try {
                if(Utilities.existsCoffeeBrand(Integer.valueOf(updateCoffeeBrandId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM CoffeeBrand WHERE coffeeBrandId = " + updateCoffeeBrandId.getText());
                    if(resultSet.next()){
                        updateCoffeeBrandName.setText(resultSet.getString("coffeeBrandName"));
                    }
                    connection.close();
                } else {
                    idNotFound();
                }
            } catch (SQLException e){
                e.printStackTrace();
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }
        } else {
            emptyOperation();
        }
    }
    @FXML
    public void updateCoffeeBrand(){
        if((!updateCoffeeBrandId.getText().isEmpty()) && (!updateCoffeeBrandName.getText().isEmpty())){
            try {
                if(Utilities.existsCoffeeBrand(Integer.valueOf(updateCoffeeBrandId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CoffeeBrand SET coffeeBrandName = ? WHERE coffeeBrandId = ?");
                    preparedStatement.setString(1, updateCoffeeBrandName.getText());
                    preparedStatement.setInt(2, Integer.valueOf(updateCoffeeBrandId.getText()));
                    preparedStatement.executeUpdate();
                    // Clear items
                    updateCoffeeBrandId.clear();
                    updateCoffeeBrandName.clear();
                    connection.close();
                    updateTable();
                    Utilities.logAction(AuditLogAction.UPDATED_COFFEE_BRAND);
                    successfullOperation();
                }
            } catch (SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_UPDATE_COFFEE_BRAND);
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }

        } else {
            emptyOperation();
        }
    }
    // Delete coffee brand
    @FXML
    public void deleteCoffeeBrand(){
        if(!deleteCoffeeBrandId.getText().isEmpty()){
            try {
                if(Utilities.existsCoffeeBrand(Integer.valueOf(deleteCoffeeBrandId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CoffeeBrand WHERE coffeeBrandId = ?");
                    preparedStatement.setInt(1, Integer.valueOf(deleteCoffeeBrandId.getText()));
                    preparedStatement.execute();
                    // Clear items
                    deleteCoffeeBrandId.clear();
                    connection.close();
                    updateTable();
                    Utilities.logAction(AuditLogAction.DELETED_COFFEE_BRAND);
                    successfullOperation();
                } else {
                    idNotFound();
                }
            } catch (SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_DELETE_COFFEE_BRAND);
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }
        } else {
            emptyOperation();
        }
    }
    // Update table
    @FXML
    public void updateTable(){
        try {
            coffeeBrandTableView.getItems().clear();
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CoffeeBrand");
            while(resultSet.next()){
                coffeeBrandTableView.getItems().add(new CoffeeBrand(resultSet.getInt("coffeeBrandId"), resultSet.getString("coffeeBrandName")));
            }
        } catch (SQLException e){
            e.printStackTrace();
            failedOperation();
        }
    }
}

package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Enums.Config;
import com.alex22sv.coffeeapp.Classes.*;
import com.alex22sv.coffeeapp.Enums.AuditLogAction;
import com.alex22sv.coffeeapp.Enums.Config;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class PreparationMethodController extends Controller {
    // Add preparation method
    @FXML
    private TextField addPreparationMethodName;
    @FXML
    private Button addPreparationMethodButton;
    // Update preparation method
    @FXML
    private TextField updatePreparationMethodId;
    @FXML
    private Button updatePreparationMethodIdButton;
    @FXML
    private TextField updatePreparationMethodName;
    @FXML
    private Button updatePreparationMethodButton;
    // Delete preparation method
    @FXML
    private TextField deletePreparationMethodId;
    @FXML
    private Button deletePreparationMethodButton;
    // Table
    @FXML
    private TableView<PreparationMethod> preparationMethodTableView;
    @FXML
    private TableColumn<PreparationMethod, Integer> preparationMethodIdColumn;
    @FXML
    private TableColumn<PreparationMethod, String> preparationMethodNameColumn;
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
        preparationMethodIdColumn.setCellValueFactory(new PropertyValueFactory<>("preparationMethodId"));
        preparationMethodNameColumn.setCellValueFactory(new PropertyValueFactory<>("preparationMethodName"));
        // Database info
        databaseUsername.setText("User: " + Config.USERNAME.value);
        databaseServer.setText("Server: " + Config.SERVER.value);
        databaseName.setText("Database: " + Config.DATABASE.value);
        // App version
        appVersion.setText(Config.APP_VERSION.value);
        /*// Audit log
        Utilities.logAction(AuditLogAction.OPENED_PREPARATION_METHOD);*/
        // Update table view
        updateTable();
    }
    // Add preparation method
    @FXML
    public void addPreparationMethod(){
        if(!addPreparationMethodName.getText().isEmpty()){
            try{
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PreparationMethod (`preparationMethodName`) VALUES (?)");
                preparedStatement.setString(1, addPreparationMethodName.getText());
                preparedStatement.execute();
                // Clear items
                addPreparationMethodName.clear();
                connection.close();
                updateTable();
                Utilities.logAction(AuditLogAction.ADDED_PREPARATION_METHOD);
                successfullOperation();
            } catch(SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_ADD_PREPARATION_METHOD);
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }
        } else {
            emptyOperation();
        }
    }
    // Update preparation method
    @FXML
    public void updatePreparationMethodGetOldValues(){
        if(!updatePreparationMethodId.getText().isEmpty()){
            try {
                if(Utilities.existsCoffeeBrand(Integer.valueOf(updatePreparationMethodId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM PreparationMethod WHERE preparationMethodId = " + updatePreparationMethodId.getText());
                    if(resultSet.next()){
                        updatePreparationMethodName.setText(resultSet.getString("preparationMethodName"));
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
    public void updatePreparationMethod(){
        if((!updatePreparationMethodId.getText().isEmpty()) && (!updatePreparationMethodName.getText().isEmpty())){
            try {
                if(Utilities.existsCoffeeBrand(Integer.valueOf(updatePreparationMethodId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PreparationMethod SET preparationMethodName = ? WHERE preparationMethodId = ?");
                    preparedStatement.setString(1, updatePreparationMethodName.getText());
                    preparedStatement.setInt(2, Integer.valueOf(updatePreparationMethodId.getText()));
                    preparedStatement.executeUpdate();
                    // Clear items
                    updatePreparationMethodId.clear();
                    updatePreparationMethodName.clear();
                    connection.close();
                    updateTable();
                    Utilities.logAction(AuditLogAction.UPDATED_PREPARATION_METHOD);
                    successfullOperation();
                }
            } catch (SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_UPDATE_PREPARATION_METHOD);
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }

        } else {
            emptyOperation();
        }
    }
    @FXML
    public void deletePreparationMethod(){
        if(!deletePreparationMethodId.getText().isEmpty()){
            try {
                if(Utilities.existsCoffeeBrand(Integer.valueOf(deletePreparationMethodId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PreparationMethod WHERE preparationMethodId = ?");
                    preparedStatement.setInt(1, Integer.valueOf(deletePreparationMethodId.getText()));
                    preparedStatement.execute();
                    // Clear items
                    deletePreparationMethodId.clear();
                    connection.close();
                    updateTable();
                    Utilities.logAction(AuditLogAction.DELETED_PREPARATION_METHOD);
                    successfullOperation();
                } else {
                    idNotFound();
                }
            } catch (SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_DELETE_PREPARATION_METHOD);
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
            preparationMethodTableView.getItems().clear();
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PreparationMethod");
            while(resultSet.next()){
                preparationMethodTableView.getItems().add(new PreparationMethod(resultSet.getInt("preparationMethodId"), resultSet.getString("preparationMethodName")));
            }
        } catch (SQLException e){
            e.printStackTrace();
            failedOperation();
        }
    }
}


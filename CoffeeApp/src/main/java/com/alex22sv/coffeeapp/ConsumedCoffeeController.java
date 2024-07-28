package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ConsumedCoffeeController extends Controller{
    // Add consumed coffee
    @FXML
    private TextField addConsumedCoffeeName;
    @FXML
    private ChoiceBox<String> addConsumedCoffeeBrand;
    @FXML
    private DatePicker addConsumedCoffeeDate;
    @FXML
    private Button addConsumedCoffeeButton;
    // Update consumed coffee
    @FXML
    private TextField updateConsumedCoffeeId;
    @FXML
    private Button updateConsumedCoffeeIdButton;
    @FXML
    private TextField updateConsumedCoffeeName;
    @FXML
    private ChoiceBox<String> updateConsumedCoffeeBrand;
    @FXML
    private DatePicker updateConsumedCoffeeDate;
    @FXML Button updateConsumedCoffeeButton;
    // Delete consumed coffee
    @FXML
    private TextField deleteConsumedCoffeeId;
    @FXML
    private Button deleteConsumedCoffeeIdButton;
    // Table
    @FXML
    private TableView<ConsumedCoffee> consumedCoffeeTableView;
    @FXML
    private TableColumn<ConsumedCoffee, Integer> consumedCoffeeIdColumn;
    @FXML
    private TableColumn<ConsumedCoffee, String> consumedCoffeeNameColumn;
    @FXML
    private TableColumn<ConsumedCoffee, String> consumedCoffeeBrandColumn;
    @FXML
    private TableColumn<ConsumedCoffee, String> consumedCoffeeDateColumn;
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
        consumedCoffeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("consumedCoffeeId"));
        consumedCoffeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("consumedCoffeeName"));
        consumedCoffeeBrandColumn.setCellValueFactory(new PropertyValueFactory<>("consumedCoffeeBrand"));
        consumedCoffeeDateColumn.setCellValueFactory(new PropertyValueFactory<>("consumedCoffeeDate"));
        // Database info
        databaseUsername.setText("User: " + Config.USERNAME.value);
        databaseServer.setText("Server: " + Config.SERVER.value);
        databaseName.setText("Database: " + Config.DATABASE.value);
        // App version
        appVersion.setText(Config.APP_VERSION.value);
        // Coffee brands
        updateCoffeeBrandOptions();
        updateConsumedCoffeeBrand.setValue("None");
        // Update table
        updateTable();
    }
    // Update coffee brand options
    @FXML
    public void updateCoffeeBrandOptions(){
        try{
            addConsumedCoffeeBrand.getItems().clear();
            updateConsumedCoffeeBrand.getItems().clear();
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CoffeeBrand");
            while(resultSet.next()){
                if(!resultSet.getString("coffeeBrandName").toLowerCase().equals("none")){
                    addConsumedCoffeeBrand.getItems().add(resultSet.getString("coffeeBrandName"));
                    updateConsumedCoffeeBrand.getItems().add(resultSet.getString("coffeeBrandName"));
                }

            }
            addConsumedCoffeeBrand.getItems().add("None");
            updateConsumedCoffeeBrand.getItems().add("None");
            addConsumedCoffeeBrand.setValue("None");
            updateConsumedCoffeeBrand.setValue("None");
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
            failedOperation();
        }
    }
    // Add consumed coffee
    @FXML
    private void addConsumedCoffee(){
        if( (!addConsumedCoffeeName.getText().isEmpty()) && (addConsumedCoffeeBrand.getValue()!=null) && (addConsumedCoffeeDate.getValue()!=null) ){
            try{
                Connection connection = DatabaseConnection.getConnection();
                Integer coffeeBrandId = null;
                if((!addConsumedCoffeeBrand.getValue().toLowerCase().equals("none"))){
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT coffeeBrandId FROM CoffeeBrand WHERE coffeeBrandName = '" + addConsumedCoffeeBrand.getValue() + "'");
                    resultSet.next();
                     coffeeBrandId = resultSet.getInt("coffeeBrandId");
                }
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ConsumedCoffee (`coffeeName`, `coffeeBrandId`, `consumedCoffeeDate`) VALUES (?, ?, ?)");
                preparedStatement.setString(1, addConsumedCoffeeName.getText());
                if(addConsumedCoffeeBrand.getValue().toLowerCase().equals("none")){
                    preparedStatement.setNull(2, Types.INTEGER);
                } else {
                    preparedStatement.setInt(2, coffeeBrandId);
                }
                preparedStatement.setDate(3,  java.sql.Date.valueOf(addConsumedCoffeeDate.getValue()));
                preparedStatement.executeUpdate();
                // Clear items
                addConsumedCoffeeName.clear();
                addConsumedCoffeeBrand.setValue("None");
                addConsumedCoffeeDate.setValue(null);
                connection.close();
                updateTable();
                Utilities.logAction(AuditLogAction.ADDED_CONSUMED_COFFEE);
                successfullOperation();
            } catch(SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_ADD_CONSUMED_COFFEE);
                failedOperation();
            } catch(NumberFormatException e){
                typeError();
            }

        } else {
            emptyOperation();
        }
    }
    // Update consumed coffee
    @FXML
    private void updateConsumedCoffeeGetOldValues(){
        if(!updateConsumedCoffeeId.getText().isEmpty()){
            try {
                if(Utilities.existsConsumedCoffee(Integer.valueOf(updateConsumedCoffeeId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM ConsumedCoffee WHERE consumedCoffeeId = " + updateConsumedCoffeeId.getText());
                    if(resultSet.next()){
                        String coffeeBrandName = "None";
                        if(resultSet.getString("coffeeBrandId")!=null){
                            Statement statement1 = connection.createStatement();
                            ResultSet resultSet1 = statement1.executeQuery("SELECT coffeeBrandName FROM CoffeeBrand WHERE coffeeBrandId = " + resultSet.getInt("coffeeBrandId"));
                            if(resultSet1.next()){
                                coffeeBrandName = resultSet1.getString("coffeeBrandName");
                            }
                        }
                        updateConsumedCoffeeName.setText(resultSet.getString("coffeeName"));
                        updateCoffeeBrandOptions();
                        updateConsumedCoffeeBrand.setValue(coffeeBrandName);
                        updateConsumedCoffeeDate.setValue(resultSet.getDate("consumedCoffeeDate").toLocalDate());
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
    private void updateConsumedCoffee(){
        if( (!updateConsumedCoffeeId.getText().isEmpty()) && (!updateConsumedCoffeeName.getText().isEmpty()) && (updateConsumedCoffeeBrand.getValue()!=null) && (updateConsumedCoffeeDate.getValue()!=null)){
           try {
               if(Utilities.existsConsumedCoffee(Integer.valueOf(updateConsumedCoffeeId.getText()))){
                   Connection connection = DatabaseConnection.getConnection();
                   Integer coffeeBrandId = null;
                   if((!updateConsumedCoffeeBrand.getValue().toLowerCase().equals("none"))){
                       Statement statement = connection.createStatement();
                       ResultSet resultSet = statement.executeQuery("SELECT coffeeBrandId FROM CoffeeBrand WHERE coffeeBrandName = '" + updateConsumedCoffeeBrand.getValue() + "'");
                       resultSet.next();
                       coffeeBrandId = resultSet.getInt("coffeeBrandId");
                   }
                   PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ConsumedCoffee SET coffeeName = ?, coffeeBrandId = ?, consumedCoffeeDate = ? WHERE consumedCoffeeId = ?");
                   preparedStatement.setString(1, updateConsumedCoffeeName.getText());
                   if(updateConsumedCoffeeBrand.getValue().toLowerCase().equals("none")){
                       preparedStatement.setNull(2, Types.INTEGER);
                   } else {
                       preparedStatement.setInt(2, coffeeBrandId);
                   }
                   preparedStatement.setDate(3, java.sql.Date.valueOf(updateConsumedCoffeeDate.getValue()));
                   preparedStatement.setInt(4, Integer.valueOf(updateConsumedCoffeeId.getText()));
                   preparedStatement.executeUpdate();
                   // Clear items
                   updateConsumedCoffeeId.clear();
                   updateConsumedCoffeeName.clear();
                   updateConsumedCoffeeBrand.setValue("None");
                   updateConsumedCoffeeDate.setValue(null);
                   connection.close();
                   updateTable();
                   Utilities.logAction(AuditLogAction.UPDATED_CONSUMED_COFFEE);
                   successfullOperation();
               } else {
                   idNotFound();
               }
           } catch (SQLException e){
               e.printStackTrace();
               Utilities.logAction(AuditLogAction.FAILED_TO_UPDATE_CONSUMED_COFFEE);
               failedOperation();
           } catch(NumberFormatException e){
               typeError();
           }

        } else {
            emptyOperation();
        }
    }
    // Delete consumed coffee
    @FXML
    private void deleteConsumedCoffee(){
        if(!deleteConsumedCoffeeId.getText().isEmpty()){
            try{
                if(Utilities.existsConsumedCoffee(Integer.valueOf(deleteConsumedCoffeeId.getText()))){
                    Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ConsumedCoffee WHERE consumedCoffeeId = ?");
                    preparedStatement.setInt(1, Integer.valueOf(deleteConsumedCoffeeId.getText()));
                    preparedStatement.executeUpdate();
                    // Clear items
                    deleteConsumedCoffeeId.clear();
                    connection.close();
                    updateTable();
                    Utilities.logAction(AuditLogAction.DELETED_CONSUMED_COFFEE);
                    successfullOperation();
                } else {
                    idNotFound();
                }
            } catch (SQLException e){
                e.printStackTrace();
                Utilities.logAction(AuditLogAction.FAILED_TO_DELETE_CONSUMED_COFFEE);
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
    private void updateTable(){
        try{
            consumedCoffeeTableView.getItems().clear();
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ConsumedCoffee");
            while(resultSet.next()){
                if(resultSet.getString("coffeeBrandId")!=null){
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery("SELECT coffeeBrandName FROM CoffeeBrand WHERE coffeeBrandId = " + resultSet.getInt("coffeeBrandId"));
                    if(resultSet1.next()){
                        consumedCoffeeTableView.getItems().add(new ConsumedCoffee(resultSet.getInt("consumedCoffeeId"), resultSet.getString("coffeeName"), resultSet1.getString("coffeeBrandName"), resultSet.getString("consumedCoffeeDate")));
                    }
                } else {
                    consumedCoffeeTableView.getItems().add(new ConsumedCoffee(resultSet.getInt("consumedCoffeeId"), resultSet.getString("coffeeName"), "None", resultSet.getString("consumedCoffeeDate")));
                }
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
            failedOperation();
        }
    }
}
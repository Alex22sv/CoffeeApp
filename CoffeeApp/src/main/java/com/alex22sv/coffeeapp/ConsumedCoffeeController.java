package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Classes.Config;
import com.alex22sv.coffeeapp.Classes.ConsumedCoffee;
import com.alex22sv.coffeeapp.Classes.DatabaseConnection;
import com.alex22sv.coffeeapp.Classes.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.jshell.execution.Util;

import java.sql.*;
import java.time.ZoneId;
import java.util.Date;

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
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CoffeeBrand");
            while(resultSet.next()){
                if(!resultSet.getString("coffeeBrandName").toLowerCase().equals("none")){
                    addConsumedCoffeeBrand.getItems().add(resultSet.getString("coffeeBrandName"));
                }

            }
            addConsumedCoffeeBrand.getItems().add("None");
            addConsumedCoffeeBrand.setValue("None");
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
                if((!addConsumedCoffeeBrand.getValue().equals("None"))){
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT coffeeBrandId FROM CoffeeBrand WHERE coffeeBrandName = '" + addConsumedCoffeeBrand.getValue() + "'");
                    resultSet.next();
                     coffeeBrandId = resultSet.getInt("coffeeBrandId");
                }
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ConsumedCoffee (`coffeeName`, `coffeeBrandId`, `consumedCoffeeDate`) VALUES (?, ?, ?)");
                preparedStatement.setString(1, addConsumedCoffeeName.getText());
                if(addConsumedCoffeeBrand.getValue().equals("None")){
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
                updateTable();
                successfullOperation();
                connection.close();

            } catch(SQLException e){
                e.printStackTrace();
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
    private void updateConsumedCoffee(){

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
                    updateTable();
                    deleteConsumedCoffeeId.clear();
                    successfullOperation();
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
        }catch (SQLException e){
            e.printStackTrace();
            failedOperation();
        }
    }
}
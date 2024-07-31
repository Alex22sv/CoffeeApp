package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuditLogController extends Controller {
    // Audit log table
    @FXML
    private TableView<AuditLog> auditLogTableView;
    @FXML
    private TableColumn<AuditLog, Integer> auditLogIdColumn;
    @FXML
    private TableColumn<AuditLog, String> auditLogActionColumn;
    @FXML
    private TableColumn<AuditLog, String> auditLogUserColumn;
    @FXML
    private TableColumn<AuditLog, String> auditLogDateColumn;
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
        auditLogIdColumn.setCellValueFactory(new PropertyValueFactory<>("auditLogId"));
        auditLogActionColumn.setCellValueFactory(new PropertyValueFactory<>("auditLogAction"));
        auditLogUserColumn.setCellValueFactory(new PropertyValueFactory<>("auditLogUser"));
        auditLogDateColumn.setCellValueFactory(new PropertyValueFactory<>("auditLogDate"));
        // Database info
        databaseUsername.setText("User: " + Config.USERNAME.value);
        databaseServer.setText("Server: " + Config.SERVER.value);
        databaseName.setText("Database: " + Config.DATABASE.value);
        // App version
        appVersion.setText(Config.APP_VERSION.value);
        // Audit log
        Utilities.logAction(AuditLogAction.OPENED_AUDIT_LOG);
        // Update table view
        updateTable();
    }
    @FXML
    private void updateTable(){
        try {
            auditLogTableView.getItems().clear();
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM AuditLog");
            while(resultSet.next()){
                auditLogTableView.getItems().add(new AuditLog(resultSet.getInt("auditLogId"), resultSet.getString("action"), resultSet.getString("user"), resultSet.getString("date")));
            }
        } catch (SQLException e){
            e.printStackTrace();
            Utilities.logAction(AuditLogAction.FAILED_TO_READ_AUDIT_LOG_TABLE);
            failedOperation();
        }
    }
}

package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Classes.Utilities;
import com.alex22sv.coffeeapp.Enums.AuditLogAction;
import com.alex22sv.coffeeapp.Enums.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

public class DownloadDBController extends Controller {

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
}

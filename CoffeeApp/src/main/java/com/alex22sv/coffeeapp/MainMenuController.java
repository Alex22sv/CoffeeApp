package com.alex22sv.coffeeapp;

import com.alex22sv.coffeeapp.Classes.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainMenuController extends Controller {
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
    }
}

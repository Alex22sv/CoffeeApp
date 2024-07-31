package com.alex22sv.coffeeapp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {
    @FXML
    public void openMainMenu(ActionEvent event) {
        changeScene(event, "mainMenu.fxml", "Coffee App");
    }
    @FXML
    public void openConsumedCoffees(ActionEvent event) {
        changeScene(event, "consumedCoffee.fxml", "Coffee App - Consumed Coffees");
    }

    @FXML
    private void openCoffeeBrands(ActionEvent event) {
        changeScene(event, "coffeeBrand.fxml", "Coffee App - Coffee Brands");
    }
    @FXML
    private void openMonthlyReport(ActionEvent event){

    }
    @FXML
    private void openYearlyReport(ActionEvent event){

    }
    @FXML
    private void openDownloadDB(ActionEvent event){

    }
    @FXML
    private void openAuditLog(ActionEvent event){
        changeScene(event, "auditLog.fxml", "Coffee App - Audit Log");
    }
    @FXML
    private void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    protected void changeScene(ActionEvent event, String fxmlFile, String title) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1200, 800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void successfullOperation(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("The operation was successfully executed.");
        alert.showAndWait();
    }
    @FXML
    public void failedOperation(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("The operation resulted in an unexpected error. Please contact your administrator.");
        alert.showAndWait();
    }
    @FXML
    public void emptyOperation(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Please fulfill all fields.");
        alert.showAndWait();
    }

    @FXML
    public void typeError(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error!");
        alert.setHeaderText("Please put the right type on fields.");
        alert.showAndWait();
    }

    @FXML
    public void idNotFound(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("ID not found.");
        alert.showAndWait();
    }
}
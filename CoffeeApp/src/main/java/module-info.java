module com.alex22sv.coffeeapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.jshell;

    opens com.alex22sv.coffeeapp to javafx.fxml;
    exports com.alex22sv.coffeeapp;
    exports com.alex22sv.coffeeapp.Classes;
    opens com.alex22sv.coffeeapp.Classes to javafx.fxml;
}
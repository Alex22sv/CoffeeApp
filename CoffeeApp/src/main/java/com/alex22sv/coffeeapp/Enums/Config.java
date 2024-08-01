package com.alex22sv.coffeeapp.Enums;

public enum Config {
    DRIVER("jdbc"),
    DATABASE_MANAGER("mysql"),
    PORT("3306"),
    USERNAME("ae22mp"),
    PASSWORD("Alex22sv"),
    SERVER("127.0.0.1"),
    DATABASE("CoffeeApp"),
    APP_VERSION("v1.0.0");

    public final String value;

    Config(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

package com.alex22sv.coffeeapp.Classes;

public class CoffeeBrand {
    private Integer coffeeBrandId;
    private String coffeeBrandName;
    public CoffeeBrand(Integer coffeeBrandId, String coffeeBrandName) {
        this.coffeeBrandId = coffeeBrandId;
        this.coffeeBrandName = coffeeBrandName;
    }

    public Integer getCoffeeBrandId() {
        return coffeeBrandId;
    }

    public void setCoffeeBrandId(Integer coffeeBrandId) {
        this.coffeeBrandId = coffeeBrandId;
    }

    public String getCoffeeBrandName() {
        return coffeeBrandName;
    }

    public void setCoffeeBrandName(String coffeeBrandName) {
        this.coffeeBrandName = coffeeBrandName;
    }
}

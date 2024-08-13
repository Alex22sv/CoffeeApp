package com.alex22sv.coffeeapp.Classes;

public class ConsumedCoffee {
    private Integer consumedCoffeeId;
    private String consumedCoffeeName;
    private String consumedCoffeeBrand;
    private String consumedCoffeePreparationMethod;
    private String consumedCoffeeDate;

    public ConsumedCoffee(Integer consumedCoffeeId, String consumedCoffeeName, String consumedCoffeeBrand, String consumedCoffeePreparationMethod, String consumedCoffeeDate) {
        this.consumedCoffeeId = consumedCoffeeId;
        this.consumedCoffeeName = consumedCoffeeName;
        this.consumedCoffeeBrand = consumedCoffeeBrand;
        this.consumedCoffeePreparationMethod = consumedCoffeePreparationMethod;
        this.consumedCoffeeDate = consumedCoffeeDate;
    }

    public Integer getConsumedCoffeeId() {
        return consumedCoffeeId;
    }

    public void setConsumedCoffeeId(Integer consumedCoffeeId) {
        this.consumedCoffeeId = consumedCoffeeId;
    }

    public String getConsumedCoffeeName() {
        return consumedCoffeeName;
    }

    public void setConsumedCoffeeName(String consumedCoffeeName) {
        this.consumedCoffeeName = consumedCoffeeName;
    }

    public String getConsumedCoffeeBrand() {
        return consumedCoffeeBrand;
    }

    public void setConsumedCoffeeBrand(String consumedCoffeeBrand) {
        this.consumedCoffeeBrand = consumedCoffeeBrand;
    }
    
    public String getConsumedCoffeePreparationMethod(){
        return consumedCoffeePreparationMethod;
    }
    
    public void setConsumedCoffeePreparationMethod(String consumedCoffeePreparationMethod){
        this.consumedCoffeePreparationMethod = consumedCoffeePreparationMethod;
    }

    public String getConsumedCoffeeDate() {
        return consumedCoffeeDate;
    }

    public void setConsumedCoffeeDate(String consumedCoffeeDate) {
        this.consumedCoffeeDate = consumedCoffeeDate;
    }
}
